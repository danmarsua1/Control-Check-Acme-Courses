/*
 * TeacherTheoryTutorialUpdateService.java
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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Blahblah;
import acme.entities.Configuration;
import acme.entities.Course;
import acme.entities.TheoryTutorial;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.any.course.AnyCourseRepository;
import acme.features.any.register.RegisterRepository;
import acme.features.any.theoryTutorial.AnyTheoryTutorialRepository;
import acme.features.teacher.blahblah.TeacherBlahblahRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractUpdateService;
import acme.helpers.SpamHelper;
import acme.roles.Teacher;

@Service
public class TeacherTheoryTutorialUpdateService implements AbstractUpdateService<Teacher, TheoryTutorial>{

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected TeacherTheoryTutorialRepository repository;
	
	@Autowired
	private SpamHelper spamHelper;
	
	@Autowired
	protected AnyCourseRepository courseRepository;
	
	@Autowired
	protected AdministratorConfigurationRepository configuration;
	
	@Autowired
	protected AnyTheoryTutorialRepository anyTheoryTutorials;
	
	@Autowired
	protected RegisterRepository registerRepository;
	
	@Autowired
	protected SpamHelper helper;
	
	@Autowired
	protected TeacherBlahblahRepository teacherBlahblahrepository;
	
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
	public TheoryTutorial findOne(final Request<TheoryTutorial> request) {
		assert request != null;

		TheoryTutorial result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneTheoryTutorialById(id);

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
	public void validate(final Request<TheoryTutorial> request, final TheoryTutorial entity, final Errors errors) {
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
		
		if(!errors.hasErrors("cost")) {
			errors.state(request, entity.getCost().getAmount() > 0, "cost", "teacher.theory-tutorial.form.error.negative-amount");
			final String currency = entity.getCost().getCurrency();
			final String[] currencies = this.configuration.findConfiguration().getAcceptedCurrencies().split(",");
			final Set<String> set = new HashSet<>();

			Collections.addAll(set, currencies);
			final boolean res = set.contains(currency);
			
			errors.state(request, res, "cost", "teacher.theory-tutorial.form.error.unknown-currency");
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
	public void unbind(final Request<TheoryTutorial> request, final TheoryTutorial entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "title", "abstractText", "cost", "link", "publish");
		List<Course> courses = (List<Course>) this.courseRepository.findCourses();
		model.setAttribute("courses", courses);
		
		Blahblah blahblah = this.teacherBlahblahrepository.findOneBlahblahByTheoryTutorialId(entity.getId());
		
		if(blahblah!=null) {
			model.setAttribute("hasBlahblah", true);
			model.setAttribute("blahblahId", blahblah.getId());
		}else {
			model.setAttribute("hasBlahblah", false);
			model.setAttribute("blahblahId", "");
		}
		
	}	

	@Override
	public void update(final Request<TheoryTutorial> request, final TheoryTutorial entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
		
	}

}
