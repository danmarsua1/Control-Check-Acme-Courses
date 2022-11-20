/*
 * TeacherTheoryTutorialDeleteService.java
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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.Register;
import acme.entities.TheoryTutorial;
import acme.features.any.course.AnyCourseRepository;
import acme.features.any.register.RegisterRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractDeleteService;
import acme.roles.Teacher;

@Service
public class TeacherTheoryTutorialDeleteService implements AbstractDeleteService<Teacher, TheoryTutorial>{

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherTheoryTutorialRepository repository;
	
	@Autowired
	protected AnyCourseRepository courseRepository;
	
	@Autowired
	protected RegisterRepository registerRepository;

	@Override
	public boolean authorise(final Request<TheoryTutorial> request) {
		assert request != null;
		
		boolean result;
		int masterId;
		TheoryTutorial theoryTutorial;

		masterId = request.getModel().getInteger("id");
		theoryTutorial = this.repository.findOneTheoryTutorialById(masterId);

		result = theoryTutorial != null;
		
		return result;
	}

	@Override
	public void bind(final Request<TheoryTutorial> request, final TheoryTutorial entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "ticker", "title", "abstractText", "cost", "link");
		
	}

	@Override
	public void unbind(final Request<TheoryTutorial> request, final TheoryTutorial entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "title", "abstractText", "cost", "link", "publish");
		List<Course> courses = (List<Course>) this.courseRepository.findCourses();
		model.setAttribute("courses", courses);
		
	}

	@Override
	public TheoryTutorial findOne(final Request<TheoryTutorial> request) {
		assert request != null;

		TheoryTutorial result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneTheoryTutorialById(id);

		return result;
	}

	@Override
	public void validate(final Request<TheoryTutorial> request, final TheoryTutorial entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
	}

	@Override
	public void delete(final Request<TheoryTutorial> request, final TheoryTutorial entity) {
		
		assert request != null;
		assert entity != null;
		
		Register register = this.registerRepository.findOneRegisterByTheoryTutorial(entity.getId());
		this.registerRepository.delete(register);

		this.repository.delete(entity);
		
	}	

}
