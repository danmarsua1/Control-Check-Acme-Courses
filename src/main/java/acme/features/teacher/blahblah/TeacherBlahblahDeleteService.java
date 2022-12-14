/*
 * TeacherBlahblahDeleteService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.blahblah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Blahblah;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Teacher;

@Service
public class TeacherBlahblahDeleteService implements AbstractDeleteService<Teacher, Blahblah>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherBlahblahRepository repository;
	
	@Override
	public boolean authorise(final Request<Blahblah> request) {
		assert request != null;
		
		boolean result;
		int masterId;
		Blahblah labTutorial;

		masterId = request.getModel().getInteger("id");
		labTutorial = this.repository.findOneBlahblahById(masterId);

		result = labTutorial != null;
		
		return result;
	}

	@Override
	public void bind(final Request<Blahblah> request, final Blahblah entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "ticker", "caption", "summary", "creationMoment", "cost", "initDate", "finishDate", "hlink");
		
	}

	@Override
	public void unbind(final Request<Blahblah> request, final Blahblah entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "caption", "summary", "creationMoment", "cost", "initDate", "finishDate", "hlink");
		model.setAttribute("masterId", entity.getTheoryTutorial().getId());
		
		boolean publishedTutorial = Boolean.parseBoolean((String) model.getAttribute("publishedTutorial"));
		model.setAttribute("publishedTutorial", publishedTutorial);
	}

	@Override
	public Blahblah findOne(final Request<Blahblah> request) {
		assert request != null;

		Blahblah result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneBlahblahById(id);

		return result;
	}

	@Override
	public void validate(final Request<Blahblah> request, final Blahblah entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<Blahblah> request, final Blahblah entity) {
		
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
		
	}	

}
