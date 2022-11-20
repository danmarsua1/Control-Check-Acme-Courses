/*
 * TeacherCourseShowService.java
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

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.entities.LabTutorial;
import acme.entities.TheoryTutorial;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.features.any.labTutorial.AnyLabTutorialRepository;
import acme.features.any.theoryTutorial.AnyTheoryTutorialRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Teacher;

@Service
public class TeacherCourseShowService implements AbstractShowService<Teacher, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherCourseRepository repository;
	
	@Autowired
	protected AnyTheoryTutorialRepository theoryTutorialRepository;
	
	@Autowired
	protected AnyLabTutorialRepository labTutorialRepository;
	
	@Autowired
	protected AdministratorConfigurationRepository configurationRepository;

	@Override
	public boolean authorise(final Request<Course> request) {
		assert request != null;

		boolean result;
		int courseId;
		Course course;
		boolean isOwner;

		courseId = request.getModel().getInteger("id");
		course = this.repository.findOneCourseById(courseId);
		Integer teacher = this.repository.findTeacherByCourseId(courseId);
		isOwner = teacher == request.getPrincipal().getActiveRoleId();
		result = course != null && isOwner;

		return result;
	}

	@Override
	public Course findOne(final Request<Course> request) {
		assert request != null;

		int courseId;
		Course result;

		courseId = request.getModel().getInteger("id");
		result = this.repository.findOneCourseById(courseId);

		return result;
	}
	
	@Override
	public void unbind(final Request<Course> request, final Course entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		boolean hasTheoryTutorial = false;
		boolean hasLabTutorial = false;

		request.unbind(entity, model, "ticker", "caption", "abstractText", "link", "publish");
		
		int courseId = request.getModel().getInteger("id");
		List<Object[]> priceTheoryTutorials = this.repository.getCourseTheoryTutorialsPrice(courseId);
		List<Object[]> priceLabTutorials = this.repository.getCourseLabTutorialsPrice(courseId);
		Money moneyTheoryTutorials = this.convertToLocalCurrencyAndSum(priceTheoryTutorials);
		Money moneyLabTutorials = this.convertToLocalCurrencyAndSum(priceLabTutorials);

		Money total = new Money();
		total.setCurrency(moneyTheoryTutorials.getCurrency());
		total.setAmount(moneyTheoryTutorials.getAmount()+moneyLabTutorials.getAmount());
		model.setAttribute("totalPrice", total);
		
		// Has Theory tutorial or Lab Tutorial
		Collection<TheoryTutorial> theoryTutorials  = this.theoryTutorialRepository.findManyTheoryTutorialsByCourseId(courseId);
		Collection<LabTutorial> labTutorials  = this.labTutorialRepository.findManyLabTutorialsByCourseId(courseId);
		hasTheoryTutorial = !theoryTutorials.isEmpty();
		hasLabTutorial = !labTutorials.isEmpty();
		model.setAttribute("hasTheoryTutorial", hasTheoryTutorial);
		model.setAttribute("hasLabTutorial", hasLabTutorial);
	}
	
	// Other methods
	private Money convertToLocalCurrencyAndSum(List<Object[]> prices) {
		Money res = new Money();
		
		String localCurrency = this.configurationRepository.findConfiguration().getCurrency();
		Double amount;
		Double sumAmount = 0.0;
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
		
		for (Object[] b:prices) {
			amount = (Double) b[0];
			currency = (String) b[1];
			
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
				sumAmount += currency.equals("USD")
					? amount * USD_EUR_FACTOR
					: operationGBPEUR;
			// If localCurrency = USD
			}else if(localCurrency.equals("USD")) {
				sumAmount += currency.equals("EUR")
					? amount * EUR_USD_FACTOR
					: operationGBPUSD;
			// If localCurrency = GBP
			}else{
				sumAmount += currency.equals("EUR")
					? amount * EUR_GBP_FACTOR
					: operationUSDGBP;
			}
		}
		
		res.setAmount(sumAmount);
		res.setCurrency(localCurrency);
		
		return res;
	}
	
}
