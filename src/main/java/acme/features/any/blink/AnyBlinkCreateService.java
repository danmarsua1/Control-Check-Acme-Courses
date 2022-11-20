/*
 * AnyBlinkCreateService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.blink;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Blink;
import acme.entities.Configuration;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractCreateService;
import acme.helpers.SpamHelper;

@Service
public class AnyBlinkCreateService implements AbstractCreateService<Any, Blink> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyBlinkRepository repository;
	
	@Autowired
	protected AdministratorConfigurationRepository configuration;
	
	@Autowired
	private SpamHelper spamHelper;


	@Override
	public boolean authorise(final Request<Blink> request) {
		assert request != null;

		return true;
	}

	@Override
	public Blink instantiate(final Request<Blink> request) {
		assert request != null;

		Blink result;
		Date creationMoment;
		
		creationMoment = new Date(System.currentTimeMillis() - 1);

		result = new Blink();
		result.setCreationMoment(creationMoment);

		return result;
	}

	@Override
	public void bind(final Request<Blink> request, final Blink entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "creationMoment","caption","author","message","email");
	}

	@Override
	public void validate(final Request<Blink> request, final Blink entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		boolean confirmation;
		final Configuration conf = this.configuration.findConfiguration();

        confirmation = request.getModel().getBoolean("confirmation");
        errors.state(request, confirmation, "confirmation", "any.blink.confirmation.error");
        
		if(!errors.hasErrors("caption")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getCaption());
			errors.state(request, !isSpam, "caption", "form.error.spam");
		}
		
		if(!errors.hasErrors("message")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getMessage());
			errors.state(request, !isSpam, "message", "form.error.spam");
		}
	}

	@Override
	public void unbind(final Request<Blink> request, final Blink entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creationMoment","caption","author","message","email");
		model.setAttribute("confirmation", false);
	}

	@Override
	public void create(final Request<Blink> request, final Blink entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
