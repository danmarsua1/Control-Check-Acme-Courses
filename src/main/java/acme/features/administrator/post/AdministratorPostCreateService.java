/*
 * AdministratorPostCreateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for teacher particular
 * purposes. The copyright owner does not offer teacher warranties or representations, nor do
 * they accept teacher liabilities with respect to them.
 */

package acme.features.administrator.post;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.Post;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractCreateService;
import acme.helpers.SpamHelper;

@Service
public class AdministratorPostCreateService implements AbstractCreateService<Administrator, Post> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AdministratorPostRepository repository;

	@Autowired
	protected AdministratorConfigurationRepository configuration;

	@Autowired
	private SpamHelper spamHelper;

	@Override
	public boolean authorise(final Request<Post> request) {
		assert request != null;

		return true;
	}

	@Override
	public Post instantiate(final Request<Post> request) {
		assert request != null;

		Post result;
		Date creationMoment;
		
		creationMoment = new Date(System.currentTimeMillis() - 1);

		result = new Post();
		result.setCreationMoment(creationMoment);

		return result;
	}

	@Override
	public void bind(final Request<Post> request, final Post entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationMoment","caption","message","flag","link");
	}

	@Override
	public void validate(final Request<Post> request, final Post entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean confirmation;
		final Configuration conf = this.configuration.findConfiguration();
		
		confirmation = request.getModel().getBoolean("confirmation");
		errors.state(request, confirmation, "confirmation", "administrator.post.confirmation.error");

		if (!errors.hasErrors("caption")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getCaption());
			errors.state(request, !isSpam, "caption", "form.error.spam");
		}
		
		if (!errors.hasErrors("message")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getMessage());
			errors.state(request, !isSpam, "message", "form.error.spam");
		}

	}

	@Override
	public void unbind(final Request<Post> request, final Post entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creationMoment","caption","message","flag","link");
		model.setAttribute("confirmation", false);
	}

	@Override
	public void create(final Request<Post> request, final Post entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
