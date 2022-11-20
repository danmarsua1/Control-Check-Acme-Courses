/*
 * TeacherFollowUpCreateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for teacher particular
 * purposes. The copyright owner does not offer teacher warranties or representations, nor do
 * they accept teacher liabilities with respect to them.
 */

package acme.features.teacher.followUp;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.FollowUp;
import acme.entities.HelpRequest;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.teacher.helpRequest.TeacherHelpRequestRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.helpers.SpamHelper;
import acme.roles.Teacher;

@Service
public class TeacherFollowUpCreateService implements AbstractCreateService<Teacher, FollowUp> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherFollowUpRepository repository;
	
	@Autowired
	protected TeacherHelpRequestRepository helpRequestRepository;

	@Autowired
	protected AdministratorConfigurationRepository configuration;

	@Autowired
	private SpamHelper spamHelper;

	@Override
	public boolean authorise(final Request<FollowUp> request) {
		assert request != null;

		return true;
	}

	@Override
	public FollowUp instantiate(final Request<FollowUp> request) {
		assert request != null;

		FollowUp result;
		Integer masterId;
		HelpRequest helpRequest;
		Date creationMoment;
		
		masterId = request.getModel().getInteger("helperRequestId");
		helpRequest = this.helpRequestRepository.findOneHelpRequestById(masterId);

		creationMoment = new Date(System.currentTimeMillis() - 1);

		result = new FollowUp();
		result.setCreationMoment(creationMoment);

		// Manage unique code
		String ticker = "";

		do
			ticker = this.createTicker(helpRequest);
		while (!this.isTickerUnique(ticker, helpRequest));
		result.setSequenceNumber(ticker);
		result.setHelpRequest(helpRequest);

		return result;
	}

	@Override
	public void bind(final Request<FollowUp> request, final FollowUp entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "sequenceNumber", "creationMoment", "message", "link");
	}

	@Override
	public void validate(final Request<FollowUp> request, final FollowUp entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean confirmation;
		final Configuration conf = this.configuration.findConfiguration();

		confirmation = request.getModel().getBoolean("confirmation");
		errors.state(request, confirmation, "confirmation", "teacher.follow-up.confirmation.error");

		if (!errors.hasErrors("message")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getMessage());
			errors.state(request, !isSpam, "message", "form.error.spam");
		}

	}

	@Override
	public void unbind(final Request<FollowUp> request, final FollowUp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "sequenceNumber", "creationMoment", "message", "link");
		model.setAttribute("confirmation", false);
		model.setAttribute("helperRequestId", entity.getHelpRequest().getId());
	}

	@Override
	public void create(final Request<FollowUp> request, final FollowUp entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

	// Other methods

	public String createTicker(HelpRequest helpRequest) {

		// The ticker must be as follow: HRE-XXX:XXXX
		String ticker = "";
		final String TICKER_PREFIX = helpRequest.getTicker();

		// Set ticker format
		ticker = TICKER_PREFIX + ":" + this.nextSequenceNumber(helpRequest);

		return ticker;

	}

	public boolean isTickerUnique(final String ticker, HelpRequest helpRequest) {

		Boolean result = true;

		final ArrayList<FollowUp> followUps = new ArrayList<>(
				this.repository.findFollowUpsByHelpRequest(helpRequest.getId()));

		final ArrayList<String> tickers = new ArrayList<>();

		for (final FollowUp f : followUps) {
			tickers.add(f.getSequenceNumber());
		}

		if (tickers.contains(ticker)) {
			result = false;
			this.createTicker(helpRequest);
		}

		return result;

	}

	public String nextSequenceNumber(HelpRequest helpRequest) {

		String res = "";
		int total;

		final ArrayList<FollowUp> followUps = new ArrayList<>(
				this.repository.findFollowUpsByHelpRequest(helpRequest.getId()));
		int size = followUps.size();

		total = size + 1;

		if (String.valueOf(total).length() == 1) {
			res = "000" + total;
		} else if (String.valueOf(total).length() == 2) {
			res = "00" + total;
		} else if (String.valueOf(total).length() == 3) {
			res = "0" + total;
		} else if (String.valueOf(total).length() > 3) {
			res = String.valueOf(total);
		}

		return res;

	}

}
