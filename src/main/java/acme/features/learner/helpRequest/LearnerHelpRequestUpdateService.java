/*
 * LearnerHelpRequestUpdateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.learner.helpRequest;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.HelpRequest;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.helpers.SpamHelper;
import acme.roles.Learner;

@Service
public class LearnerHelpRequestUpdateService implements AbstractUpdateService<Learner, HelpRequest>{

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected LearnerHelpRequestRepository repository;
	
	@Autowired
	private SpamHelper spamHelper;
	
	@Autowired
	protected AdministratorConfigurationRepository configuration;
	
	@Autowired
	protected SpamHelper helper;
	
	@Override
	public boolean authorise(final Request<HelpRequest> request) {
		assert request != null;
		
		boolean result;
		int masterId;
		HelpRequest helpRequest;

		masterId = request.getModel().getInteger("id");
		helpRequest = this.repository.findOneHelpRequestById(masterId);
		
		result = helpRequest != null;
		
		return result;
	}
	
	@Override
	public HelpRequest findOne(final Request<HelpRequest> request) {
		assert request != null;

		HelpRequest result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneHelpRequestById(id);

		return result;
	}

	@Override
	public void bind(final Request<HelpRequest> request, final HelpRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "ticker", "statement", "creationMoment", "budget", "initDate", "finishDate", "status", "link");
	}
	
	@Override
	public void validate(final Request<HelpRequest> request, final HelpRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Configuration conf = this.configuration.findConfiguration();
		
		if(!errors.hasErrors("statement")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getStatement());
			errors.state(request, !isSpam, "statement", "form.error.spam");
		}
		

		if (!errors.hasErrors("initDate")) {
			Date minimumInitialDate;

			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getCreationMoment());
			calendar.add(Calendar.WEEK_OF_MONTH, 1);
			minimumInitialDate = calendar.getTime();
			errors.state(request, entity.getInitDate().after(minimumInitialDate), "initDate", "learner.help-request.form.error.too-close-initial");
		}
		
		if (!errors.hasErrors("finishDate") && !errors.hasErrors("initDate")) {
			Date minimumFinalDate;

			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getInitDate());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			minimumFinalDate = calendar.getTime();
			errors.state(request, entity.getFinishDate().after(minimumFinalDate), "finishDate", "learner.help-request.form.error.too-close-final");
		}
		
		if(!errors.hasErrors("budget")) {
			errors.state(request, entity.getBudget().getAmount() > 0, "budget", "learner.help-request.form.error.negative-amount");
			final String currency = entity.getBudget().getCurrency();
			final String[] currencies = this.configuration.findConfiguration().getAcceptedCurrencies().split(",");
			final Set<String> set = new HashSet<>();

			Collections.addAll(set, currencies);
			final boolean res = set.contains(currency);
			
			errors.state(request, res, "budget", "learner.help-request.form.error.unknown-currency");
		}
		
	}

	@Override
	public void unbind(final Request<HelpRequest> request, final HelpRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "statement", "creationMoment", "budget", "initDate", "finishDate", "status", "link", "publish");		
	}	

	@Override
	public void update(final Request<HelpRequest> request, final HelpRequest entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
