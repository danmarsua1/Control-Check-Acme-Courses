/*
 * TeacherTheoryTutorialShowService.java
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
import acme.entities.TheoryTutorial;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.any.course.AnyCourseRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Teacher;

@Service
public class TeacherTheoryTutorialShowService implements AbstractShowService<Teacher, TheoryTutorial> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherTheoryTutorialRepository repository;
	
	@Autowired
	protected AdministratorConfigurationRepository configurationRepository;
	
	@Autowired
	protected AnyCourseRepository courseRepository;

	@Override
	public boolean authorise(final Request<TheoryTutorial> request) {
		assert request != null;

		boolean result;
		int theoryTutorialId;
		TheoryTutorial theoryTutorial;
		boolean isOwner;

		theoryTutorialId = request.getModel().getInteger("id");
		theoryTutorial = this.repository.findOneTheoryTutorialById(theoryTutorialId);
		Integer teacher = this.repository.findTeacherByTheoryTutorialId(theoryTutorialId);
		isOwner = teacher == request.getPrincipal().getActiveRoleId();
		result = theoryTutorial != null && isOwner;

		return result;
	}

	@Override
	public TheoryTutorial findOne(final Request<TheoryTutorial> request) {
		assert request != null;

		int theoryTutorialId;
		TheoryTutorial result;

		theoryTutorialId = request.getModel().getInteger("id");
		result = this.repository.findOneTheoryTutorialById(theoryTutorialId);

		return result;
	}
	
	@Override
	public void unbind(final Request<TheoryTutorial> request, final TheoryTutorial entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "title", "abstractText", "cost", "link", "publish");
		List<Course> courses = (List<Course>) this.courseRepository.findCourses();
		model.setAttribute("courses", courses);
		
		Money totalPrice = this.convertToLocalCurrency(entity.getCost());
		model.setAttribute("cost", totalPrice);
	}
	
	// Other methods
	private Money convertToLocalCurrency(Money prices) {
		Money res = new Money();
		
		String localCurrency = this.configurationRepository.findConfiguration().getCurrency();
		Double amount;
		Double convertedAmount;
		String currency;
		
		// EUR
		final Double EUR_USD_FACTOR = 1.0006;
		final Double EUR_GBP_FACTOR = 0.881655;
					
		// USD
		final Double USD_EUR_FACTOR = 0.998169;
		final Double USD_GBP_FACTOR = 0.88121;
		
		// GBP
		final Double GBP_EUR_FACTOR = 1.14938;
		final Double GBP_USD_FACTOR = 1.137041;
		
		amount = prices.getAmount();
		currency = prices.getCurrency();
		
		Double operationGBPEUR = currency.equals("GBP")
				? amount * GBP_EUR_FACTOR
				: amount;
		
		Double operationGBPUSD = currency.equals("GBP")
				? amount * GBP_USD_FACTOR
				: amount;
		
		Double operationUSDGBP = currency.equals("USD")
				? amount * USD_GBP_FACTOR
				: amount;
		
		// If localCurrency = EUR
		if(localCurrency.equals("EUR")) {
			convertedAmount = currency.equals("USD")
				? amount * USD_EUR_FACTOR
				: operationGBPEUR;
		// If localCurrency = USD
		}else if(localCurrency.equals("USD")) {
			convertedAmount = currency.equals("EUR")
				? amount * EUR_USD_FACTOR
				: operationGBPUSD;
		// If localCurrency = GBP
		}else{
			convertedAmount = currency.equals("EUR")
				? amount * EUR_GBP_FACTOR
				: operationUSDGBP;
		}
		
		res.setAmount(convertedAmount);
		res.setCurrency(localCurrency);
		
		return res;
	}
}
