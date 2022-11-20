/*
 * LearnerFollowUpListService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.learner.followUp;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.FollowUp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Learner;

@Service
public class LearnerFollowUpListService implements AbstractListService<Learner, FollowUp> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LearnerFollowUpRepository repository;

	@Override
	public boolean authorise(final Request<FollowUp> request) {
		assert request != null;
		
		boolean result;
		
		result = request.getPrincipal().hasRole(Learner.class); 

		return result;
	}

	@Override
	public Collection<FollowUp> findMany(Request<FollowUp> request) {
		assert request != null;
		
		Collection<FollowUp> result;
		
		result = this.repository.findManyFollowUpsByLearner(request.getPrincipal().getActiveRoleId());
		
		return result;
	}
	
	@Override
	public void unbind(final Request<FollowUp> request, final FollowUp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "sequenceNumber", "message");
	}

}
