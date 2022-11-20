/*
 * TeacherLabTutorialPublishService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.labTutorial;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.Course;
import acme.entities.LabTutorial;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.any.course.AnyCourseRepository;
import acme.features.any.register.RegisterRepository;
import acme.features.any.labTutorial.AnyLabTutorialRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.helpers.SpamHelper;
import acme.roles.Teacher;

@Service
public class TeacherLabTutorialPublishService implements AbstractUpdateService<Teacher, LabTutorial>{

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected TeacherLabTutorialRepository repository;
	
	@Autowired
	private SpamHelper spamHelper;
	
	@Autowired
	protected AnyCourseRepository courseRepository;
	
	@Autowired
	protected AdministratorConfigurationRepository configuration;
	
	@Autowired
	protected AnyLabTutorialRepository anyLabTutorials;
	
	@Autowired
	protected RegisterRepository registerRepository;
	
	@Autowired
	protected SpamHelper helper;
	
	@Override
	public boolean authorise(final Request<LabTutorial> request) {
		assert request != null;
		
		boolean result;
		int masterId;
		LabTutorial labTutorial;

		masterId = request.getModel().getInteger("id");
		labTutorial = this.repository.findOneLabTutorialById(masterId);
		
		result = labTutorial != null;
		
		return result;
	}
	
	@Override
	public LabTutorial findOne(final Request<LabTutorial> request) {
		assert request != null;

		LabTutorial result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneLabTutorialById(id);

		return result;
	}

	@Override
	public void bind(final Request<LabTutorial> request, final LabTutorial entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "ticker", "title", "abstractText", "cost", "link");
		
	}
	
	@Override
	public void validate(final Request<LabTutorial> request, final LabTutorial entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		final Configuration conf = this.configuration.findConfiguration();
		
		if(!errors.hasErrors("title")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getTitle());
			errors.state(request, !isSpam, "title", "form.error.spam");
		}
		
		if(!errors.hasErrors("abstractText")) {
			final boolean isSpam = this.spamHelper.isSpamText(conf.getSpamRecords(), entity.getAbstractText());
			errors.state(request, !isSpam, "abstractText", "form.error.spam");
		}
		
		String learningTime = request.getModel().getAttribute("learningTime").toString();
		Pattern numericPattern = Pattern.compile("-?\\d+(\\.\\d+)?");
		
		if(String.valueOf(learningTime).length()<=0) {
			errors.state(request, false, "learningTime", "javax.validation.constraints.NotBlank.message");
		} else if(!numericPattern.matcher(learningTime).matches()) {
			errors.state(request, false, "learningTime", "javax.validation.constraints.MustNumber.message");
		}
		
	}

	@Override
	public void unbind(final Request<LabTutorial> request, final LabTutorial entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "title", "abstractText", "cost", "link", "publish");
		List<Course> courses = (List<Course>) this.courseRepository.findCourses();
		model.setAttribute("courses", courses);
		
	}	

	@Override
	public void update(final Request<LabTutorial> request, final LabTutorial entity) {
		assert request != null;
		assert entity != null;

		entity.setPublish(true);
		this.repository.save(entity);
		
	}

}
