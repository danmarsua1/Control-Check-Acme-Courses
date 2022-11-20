/*
 * TeacherTheoryTutorialListService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.theoryTutorial;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.TheoryTutorial;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractListService;
import acme.roles.Teacher;

@Service
public class TeacherTheoryTutorialListService implements AbstractListService<Teacher, TheoryTutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherTheoryTutorialRepository repository;

	@Override
	public boolean authorise(final Request<TheoryTutorial> request) {
		assert request != null;
		
		boolean result;
		
		result = request.getPrincipal().hasRole(Teacher.class); 

		return result;
	}

	@Override
	public Collection<TheoryTutorial> findMany(Request<TheoryTutorial> request) {
		assert request != null;
		
		Collection<TheoryTutorial> result;
		Integer masterId;
		
		if(request.getModel().hasAttribute("masterId")){
			masterId = request.getModel().getInteger("masterId");
			result = this.repository.findManyTheoryTutorialsByTeacherAndCourseId(request.getPrincipal().getActiveRoleId(),masterId);
		}else {
			result = this.repository.findManyTheoryTutorialsByTeacherId(request.getPrincipal().getActiveRoleId());
		}
		
		return result;
	}
	
	@Override
	public void unbind(final Request<TheoryTutorial> request, final TheoryTutorial entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "abstractText");
	}

}
