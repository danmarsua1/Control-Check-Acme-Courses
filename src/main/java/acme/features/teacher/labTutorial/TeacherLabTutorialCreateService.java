/*
 * TeacherLabTutorialCreateService.java
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Configuration;
import acme.entities.Course;
import acme.entities.Register;
import acme.entities.LabTutorial;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.any.course.AnyCourseRepository;
import acme.features.any.register.RegisterRepository;
import acme.features.any.labTutorial.AnyLabTutorialRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.helpers.SpamHelper;
import acme.roles.Teacher;

@Service
public class TeacherLabTutorialCreateService implements AbstractCreateService<Teacher, LabTutorial>{

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

	@Override
	public boolean authorise(final Request<LabTutorial> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public LabTutorial instantiate(final Request<LabTutorial> request) {
		assert request != null;

		LabTutorial result;

		result = new LabTutorial();
		
		// Manage unique code
		String ticker = "";

		do
			ticker = this.createTicker();
		while (!this.isTickerUnique(ticker));
		result.setTicker(ticker);
		result.setPublish(false);

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
		
		if(!errors.hasErrors("cost")) {
			errors.state(request, entity.getCost().getAmount() > 0, "cost", "teacher.lab-tutorial.form.error.negative-amount");
			final String currency = entity.getCost().getCurrency();
			final String[] currencies = this.configuration.findConfiguration().getAcceptedCurrencies().split(",");
			final Set<String> set = new HashSet<>();

			Collections.addAll(set, currencies);
			final boolean res = set.contains(currency);
			
			errors.state(request, res, "cost", "teacher.lab-tutorial.form.error.unknown-currency");
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
	public void create(final Request<LabTutorial> request, final LabTutorial entity) {
		assert request != null;
		assert entity != null;
		
		String tickerCourse = String.valueOf(request.getModel().getAttribute("courses"));
		int learningTime = Integer.parseInt((String) request.getModel().getAttribute("learningTime"));
		
		Course course = this.courseRepository.findByTicker(tickerCourse);
		
		this.repository.save(entity);
		
		Register register = new Register();
		register.setLabTutorial(entity);
		register.setLearningTime(learningTime);
		register.setCourse(course);
		register.setUnity("minutes");
		this.registerRepository.save(register);
		
		course.setTeacher(this.repository.findOneTeacherById(request.getPrincipal().getAccountId()));
		this.courseRepository.save(course);

		
	}
	
	// Other methods
	
	public String createTicker() {

		// The ticker must be as follow: LTU-XXX
		String ticker = "";
		final String TICKER_PREFIX = "LTU";

		// Set ticker format
		ticker = TICKER_PREFIX + "-" + this.nextSequenceNumber();

		return ticker;

	}
	
	public boolean isTickerUnique(final String ticker) {

		Boolean result = true;

		final ArrayList<LabTutorial> labTutorials = new ArrayList<>(this.anyLabTutorials.findAllUnpublishedLabTutorials());

		final ArrayList<String> tickers = new ArrayList<>();

		for (final LabTutorial t : labTutorials) {
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
		
		final ArrayList<LabTutorial> labTutorials = new ArrayList<>(this.anyLabTutorials.findAllUnpublishedLabTutorials());
		int size = labTutorials.size();
		
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
