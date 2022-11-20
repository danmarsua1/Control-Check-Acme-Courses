/*
 * LearnerHelpRequestDeleteService.java
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

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.FollowUp;
import acme.entities.HelpRequest;
import acme.features.learner.followUp.LearnerFollowUpRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Learner;

@Service
public class LearnerHelpRequestDeleteService implements AbstractDeleteService<Learner, HelpRequest>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LearnerHelpRequestRepository repository;
	
	@Autowired
	protected LearnerFollowUpRepository followUpRepository;

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
	public void bind(final Request<HelpRequest> request, final HelpRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "ticker", "statement", "creationMoment", "budget", "initDate", "finishDate", "status", "link");
		
	}

	@Override
	public void unbind(final Request<HelpRequest> request, final HelpRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "statement", "creationMoment", "budget", "initDate", "finishDate", "status", "link", "publish");
		
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
	public void validate(final Request<HelpRequest> request, final HelpRequest entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<HelpRequest> request, final HelpRequest entity) {
		
		assert request != null;
		assert entity != null;
		
		Collection<FollowUp> followUps = this.repository.findManyFollowUpsByHelpRequest(entity.getId());
		this.followUpRepository.deleteAll(followUps);
		
		this.repository.delete(entity);
		
	}	

}
