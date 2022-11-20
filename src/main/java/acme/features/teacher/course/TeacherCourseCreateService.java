/*
 * TeacherCourseCreateService.java
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

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.Course;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.any.course.AnyCourseRepository;
import acme.features.any.register.RegisterRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.helpers.SpamHelper;
import acme.roles.Teacher;

@Service
public class TeacherCourseCreateService implements AbstractCreateService<Teacher, Course>{

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
	protected RegisterRepository registerRepository;

	@Override
	public boolean authorise(final Request<Course> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public Course instantiate(final Request<Course> request) {
		assert request != null;

		Course result;
		Teacher teacher;

		result = new Course();
		
		// Manage unique code
		String ticker = "";

		do
			ticker = this.createTicker();
		while (!this.isTickerUnique(ticker));
		result.setTicker(ticker);
		result.setPublish(false);
		
		teacher = this.repository.findOneTeacherById(request.getPrincipal().getAccountId());
		result.setTeacher(teacher);

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
	public void create(final Request<Course> request, final Course entity) {
		assert request != null;
		assert entity != null;
		
		entity.setTeacher(this.repository.findOneTeacherById(request.getPrincipal().getAccountId()));
		this.repository.save(entity);
		
	}
	
	// Other methods
	
	public String createTicker() {

		// The ticker must be as follow: COU-XXX
		String ticker = "";
		final String TICKER_PREFIX = "COU";

		// Set ticker format
		ticker = TICKER_PREFIX + "-" + this.nextSequenceNumber();

		return ticker;

	}
	
	public boolean isTickerUnique(final String ticker) {

		Boolean result = true;

		final ArrayList<Course> courses = new ArrayList<>(this.anyCourses.findAllUnpublishedCourses());

		final ArrayList<String> tickers = new ArrayList<>();

		for (final Course t : courses) {
			tickers.add(t.getTicker());
		}

		if (tickers.contains(ticker)) {
			result = false;
			this.createTicker();
		}

		return result;

	}
	
	public String nextSequenceNumber() {
		
		String res = "";
		int total;
		
		final ArrayList<Course> courses = new ArrayList<>(this.anyCourses.findAllUnpublishedCourses());
		int size = courses.size();
		
		total = size + 1;
		
		if(String.valueOf(total).length()==1) {
			res = "00" + total;
		} else if (String.valueOf(total).length()==2){
			res = "0" + total;
		} else if (String.valueOf(total).length()>2){
			res = String.valueOf(total);
		}
		
		return res;
		
		
	}
	
}
