/*
 * AnyLabTutorialListService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.labTutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.LabTutorial;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyLabTutorialListService implements AbstractListService<Any, LabTutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AnyLabTutorialRepository repository;

	@Override
	public boolean authorise(final Request<LabTutorial> request) {
		assert request!=null;
		
		return true;
	}

	@Override
	public Collection<LabTutorial> findMany(final Request<LabTutorial> request) {
		assert request!=null;
		
		Collection<LabTutorial> result;
		Integer masterId;
		
		if(request.getModel().hasAttribute("masterId")){
			masterId = request.getModel().getInteger("masterId");
			result = this.repository.findManyLabTutorialsByCourseId(masterId);
		}else {
			result = this.repository.findAllLabTutorials();
		}
		
		return result;
	}

	@Override
	public void unbind(final Request<LabTutorial> request, final LabTutorial entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		request.unbind(entity, model, "title","abstractText");
	}


}
