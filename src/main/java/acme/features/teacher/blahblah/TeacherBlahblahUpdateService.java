/*
 * TeacherBlahblahUpdateService.java
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

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Blahblah;
import acme.entities.Configuration;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.helpers.SpamHelper;
import acme.roles.Teacher;

@Service
public class TeacherBlahblahUpdateService implements AbstractUpdateService<Teacher, Blahblah>{

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected TeacherBlahblahRepository repository;
	
	@Autowired
	protected AdministratorConfigurationRepository configuration;
	
	@Autowired
	protected SpamHelper spamHelper;
	
	@Override
	public boolean authorise(final Request<Blahblah> request) {
		assert request != null;
		
		boolean result;
		int masterId;
		Blahblah blahblah;

		masterId = request.getModel().getInteger("id");
		blahblah = this.repository.findOneBlahblahById(masterId);
		
		result = blahblah != null;
		
		return result;
	}
	
	@Override
	public Blahblah findOne(final Request<Blahblah> request) {
		assert request != null;

		Blahblah result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneBlahblahById(id);

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
		
		boolean publishedTutorial = entity.getTheoryTutorial().isPublish();
		model.setAttribute("publishedTutorial", publishedTutorial);
	}	

	@Override
	public void update(final Request<Blahblah> request, final Blahblah entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
