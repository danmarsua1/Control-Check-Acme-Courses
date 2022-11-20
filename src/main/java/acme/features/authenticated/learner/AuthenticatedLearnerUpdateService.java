/*
 * AuthenticatedLearnerUpdateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.learner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractUpdateService;
import acme.roles.Learner;

@Service
public class AuthenticatedLearnerUpdateService implements AbstractUpdateService<Authenticated, Learner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedLearnerRepository repository;


	@Override
	public boolean authorise(final Request<Learner> request) {
		assert request != null;

		return true;
	}

	@Override
	public void validate(final Request<Learner> request, final Learner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void bind(final Request<Learner> request, final Learner entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "school", "statement", "link");
	}

	@Override
	public void unbind(final Request<Learner> request, final Learner entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "school", "statement", "link");
	}

	@Override
	public Learner findOne(final Request<Learner> request) {
		assert request != null;

		Learner result;
		Principal principal;
		int userAccountId;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();

		result = this.repository.findOneLearnerByUserAccountId(userAccountId);

		return result;
	}

	@Override
	public void update(final Request<Learner> request, final Learner entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}
	
}
	
