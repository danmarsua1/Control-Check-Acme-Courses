/*
 * TeacherTheoryTutorialCreateService.java
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
import acme.entities.TheoryTutorial;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.any.course.AnyCourseRepository;
import acme.features.any.register.RegisterRepository;
import acme.features.any.theoryTutorial.AnyTheoryTutorialRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.helpers.SpamHelper;
import acme.roles.Teacher;

@Service
public class TeacherTheoryTutorialCreateService implements AbstractCreateService<Teacher, TheoryTutorial>{

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

	@Override
	public boolean authorise(final Request<TheoryTutorial> request) {
		assert request != null;

		return true;
	}
	
	@Override
	public TheoryTutorial instantiate(final Request<TheoryTutorial> request) {
		assert request != null;

		TheoryTutorial result;

		result = new TheoryTutorial();
		
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
		
	}

	@Override
	public void create(final Request<TheoryTutorial> request, final TheoryTutorial entity) {
		assert request != null;
		assert entity != null;
		
		String tickerCourse = String.valueOf(request.getModel().getAttribute("courses"));
		int learningTime = Integer.parseInt((String) request.getModel().getAttribute("learningTime"));
		
		Course course = this.courseRepository.findByTicker(tickerCourse);
		
		this.repository.save(entity);
		
		Register register = new Register();
		register.setTheoryTutorial(entity);
		register.setLearningTime(learningTime);
		register.setCourse(course);
		register.setUnity("minutes");
		this.registerRepository.save(register);
		
		course.setTeacher(this.repository.findOneTeacherById(request.getPrincipal().getAccountId()));
		this.courseRepository.save(course);

		
	}
	
	// Other methods
	
	public String createTicker() {

		// The ticker must be as follow: TTU-XXX
		String ticker = "";
		final String TICKER_PREFIX = "TTU";

		// Set ticker format
		ticker = TICKER_PREFIX + "-" + this.nextSequenceNumber();

		return ticker;

	}
	
	public boolean isTickerUnique(final String ticker) {

		Boolean result = true;

		final ArrayList<TheoryTutorial> theoryTutorials = new ArrayList<>(this.anyTheoryTutorials.findAllUnpublishedTheoryTutorials());

		final ArrayList<String> tickers = new ArrayList<>();

		for (final TheoryTutorial t : theoryTutorials) {
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
		
		final ArrayList<TheoryTutorial> theoryTutorials = new ArrayList<>(this.anyTheoryTutorials.findAllUnpublishedTheoryTutorials());
		int size = theoryTutorials.size();
		
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
