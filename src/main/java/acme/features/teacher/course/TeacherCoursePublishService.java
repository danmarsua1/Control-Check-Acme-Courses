/*
 * TeacherCoursePublishService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.Course;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.any.course.AnyCourseRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.helpers.SpamHelper;
import acme.roles.Teacher;

@Service
public class TeacherCoursePublishService implements AbstractUpdateService<Teacher, Course>{

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected TeacherCourseRepository repository;
	
	@Autowired
	private SpamHelper spamHelper;
	
	@Autowired
	protected AnyCourseRepository courseRepository;
	
	@Autowired
	protected AdministratorConfigurationRepository configuration;
	
	@Autowired
	protected AnyCourseRepository anyCourses;
	
	@Autowired
	protected SpamHelper helper;
	
	@Override
	public boolean authorise(final Request<Course> request) {
		assert request != null;
		
		boolean result;
		int masterId;
		Course labTutorial;

		masterId = request.getModel().getInteger("id");
		labTutorial = this.repository.findOneCourseById(masterId);
		
		result = labTutorial != null;
		
		return result;
	}
	
	@Override
	public Course findOne(final Request<Course> request) {
		assert request != null;

		Course result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneCourseById(id);

		return result;
	}

	@Override
	public void bind(final Request<Course> request, final Course entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "ticker", "caption", "abstractText", "link");
	}
	
	@Override
	public void validate(final Request<Course> request, final Course entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Configuration conf = this.configuration.findConfiguration();
		
		if(!errors.hasErrors("caption")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getCaption());
			errors.state(request, !isSpam, "caption", "form.error.spam");
		}
		
		if(!errors.hasErrors("abstractText")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getAbstractText());
			errors.state(request, !isSpam, "abstractText", "form.error.spam");
		}
		
	}

	@Override
	public void unbind(final Request<Course> request, final Course entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "caption", "abstractText", "link", "publish");
		
	}	

	@Override
	public void update(final Request<Course> request, final Course entity) {
		assert request != null;
		assert entity != null;

		entity.setPublish(true);
		this.repository.save(entity);
		
	}

}
