
/*
 * AuthenticatedTeacherCreateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractCreateService;
import acme.roles.Teacher;

@Service
public class AuthenticatedTeacherCreateService implements AbstractCreateService<Authenticated, Teacher> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedTeacherRepository repository;


	@Override
	public boolean authorise(final Request<Teacher> request) {
		assert request != null;

		boolean result;

		result = !request.getPrincipal().hasRole(Teacher.class);

		return result;
	}

	@Override
	public void validate(final Request<Teacher> request, final Teacher entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void bind(final Request<Teacher> request, final Teacher entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "school", "statement", "link");
	}

	@Override
	public void unbind(final Request<Teacher> request, final Teacher entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "school", "statement", "link");
	}

	@Override
	public Teacher instantiate(final Request<Teacher> request) {
		assert request != null;

		Teacher result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new Teacher();
		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void create(final Request<Teacher> request, final Teacher entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}

