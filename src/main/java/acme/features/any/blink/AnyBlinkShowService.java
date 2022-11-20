/*
 * AnyDutyShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Blink;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;

@Service
public class AnyBlinkShowService implements AbstractShowService<Any, Blink> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyBlinkRepository repository;

	@Override
	public boolean authorise(final Request<Blink> request) {
		assert request!=null;
		
		return true;
	}

	@Override
	public Blink findOne(final Request<Blink> request) {
		assert request!=null;
		
		Blink result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOneBlinkById(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<Blink> request, final Blink entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		request.unbind(entity, model, "creationMoment","caption","author","message","email");		
	}


}
