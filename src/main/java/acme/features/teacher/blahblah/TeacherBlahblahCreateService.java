/*
 * TeacherBlahblahCreateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.blahblah;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Blahblah;
import acme.entities.Configuration;
import acme.entities.TheoryTutorial;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.teacher.theoryTutorial.TeacherTheoryTutorialRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.helpers.SpamHelper;
import acme.roles.Teacher;

@Service
public class TeacherBlahblahCreateService implements AbstractCreateService<Teacher, Blahblah>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherBlahblahRepository repository;
	
	@Autowired
	private SpamHelper spamHelper;
	
	@Autowired
	protected AdministratorConfigurationRepository configuration;
	
	@Autowired
	protected TeacherTheoryTutorialRepository theoryTutorialRepository;

	@Override
	public boolean authorise(final Request<Blahblah> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Blahblah instantiate(final Request<Blahblah> request) {
		assert request != null;

		Blahblah result;
		TheoryTutorial theoryTutorial;
		Integer masterId;
		Date creationMoment;
		
		masterId = request.getModel().getInteger("masterId");
		theoryTutorial = this.theoryTutorialRepository.findOneTheoryTutorialById(masterId);
		
		creationMoment = new Date(System.currentTimeMillis() - 1);

		result = new Blahblah();
		result.setCreationMoment(creationMoment);
		
		// Manage unique code
		String ticker = "";
		int cont = 1;

		do {
			ticker = this.createTicker(cont);
			cont++;
		}while (!this.isTickerUnique(ticker, cont));
		result.setTicker(ticker);
		result.setTheoryTutorial(theoryTutorial);
		
		return result;
	}

	@Override
	public void bind(final Request<Blahblah> request, final Blahblah entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "ticker", "caption", "summary", "creationMoment", "cost", "initDate", "finishDate", "hlink");
	}

	@Override
	public void validate(final Request<Blahblah> request, final Blahblah entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Configuration conf = this.configuration.findConfiguration();
		
		if(!errors.hasErrors("caption")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getCaption());
			errors.state(request, !isSpam, "caption", "form.error.spam");
		}
		
		if(!errors.hasErrors("summary")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getSummary());
			errors.state(request, !isSpam, "summary", "form.error.spam");
		}

		if (!errors.hasErrors("initDate")) {
			Date minimumInitialDate;

			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.WEEK_OF_MONTH, 1);
			minimumInitialDate = calendar.getTime();
			errors.state(request, entity.getInitDate().after(minimumInitialDate), "initDate", "teacher.blahblah.form.error.too-close-initial");
		}
		
		if (!errors.hasErrors("finishDate") && !errors.hasErrors("initDate")) {
			Date minimumFinalDate;

			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getInitDate());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			minimumFinalDate = calendar.getTime();
			errors.state(request, entity.getFinishDate().after(minimumFinalDate), "finishDate", "teacher.blahblah.form.error.too-close-final");
		}
		
		if(!errors.hasErrors("cost")) {
			errors.state(request, entity.getCost().getAmount() > 0, "cost", "teacher.blahblah.form.error.negative-amount");
			final String currency = entity.getCost().getCurrency();
			final String[] currencies = this.configuration.findConfiguration().getAcceptedCurrencies().split(",");
			final Set<String> set = new HashSet<>();

			Collections.addAll(set, currencies);
			final boolean res = set.contains(currency);
			
			errors.state(request, res, "cost", "teacher.blahblah.form.error.unknown-currency");
		}
		
	}
	
	@Override
	public void unbind(final Request<Blahblah> request, final Blahblah entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "caption", "summary", "creationMoment", "cost", "initDate", "finishDate", "hlink");
		model.setAttribute("masterId", entity.getTheoryTutorial().getId());
		
		boolean publishedTutorial = Boolean.parseBoolean((String) request.getModel().getAttribute("publishedTutorial"));
		model.setAttribute("publishedTutorial", publishedTutorial);
	}

	@Override
	public void create(final Request<Blahblah> request, final Blahblah entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
	}
	
	// Other methods
	
	public String createTicker(int cont) {

		// The ticker must be as follow: XXX-YYMMDD
		String code = new String();

		// Get creation date
		final Calendar calendar = Calendar.getInstance();
		final String year = String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
		String month = "";
		String day = "";

		if (calendar.get(Calendar.MONTH) + 1 < 10)
			month = "0" + (calendar.get(Calendar.MONTH) + 1);
		else
			month = String.valueOf(calendar.get(Calendar.MONTH) + 1);

		if (calendar.get(Calendar.DAY_OF_MONTH) < 10)
			day = "0" + calendar.get(Calendar.DAY_OF_MONTH);
		else
			day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

		code = lettersSecuency() + "-" + year + month + day;

		return code;

	}
	
	public boolean isTickerUnique(final String ticker, int cont) {

		Boolean result = true;

		final ArrayList<Blahblah> blahblahs = new ArrayList<Blahblah>(this.repository.findAllBlahblahs());

		final ArrayList<String> tickers = new ArrayList<>();

		for (final Blahblah t : blahblahs) {
			tickers.add(t.getTicker());
		}

		if (tickers.contains(ticker)) {
			result = false;
			this.createTicker(cont);
		}

		return result;

	}
	
	public String lettersSecuency() {

		final char[] elementos = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
				'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		final char[] conjunto = new char[3];

		final String secuency;

		for (int i = 0; i < 3; i++) {
			final int el = (int) (Math.random() * 25);
			conjunto[i] = elementos[el];
		}

		secuency = new String(conjunto).toUpperCase();
		return secuency;

	}
	
}
