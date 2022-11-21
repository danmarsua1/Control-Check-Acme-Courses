/*
 * TeacherBlahblahShowService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.blahblah;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Blahblah;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Teacher;

@Service
public class TeacherBlahblahShowService implements AbstractShowService<Teacher, Blahblah> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherBlahblahRepository repository;
	
	@Autowired
	protected AdministratorConfigurationRepository configurationRepository;

	@Override
	public boolean authorise(final Request<Blahblah> request) {
		assert request != null;

		boolean result;
		int blahblahId;
		Blahblah blahblah;

		blahblahId = request.getModel().getInteger("id");
		blahblah = this.repository.findOneBlahblahById(blahblahId);
		result = blahblah != null;

		return result;
	}

	@Override
	public Blahblah findOne(final Request<Blahblah> request) {
		assert request != null;

		int blahblahId;
		Blahblah result;

		blahblahId = request.getModel().getInteger("id");
		result = this.repository.findOneBlahblahById(blahblahId);

		return result;
	}
	
	@Override
	public void unbind(final Request<Blahblah> request, final Blahblah entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "caption", "summary", "creationMoment", "cost", "initDate", "finishDate", "hlink");
		
		Money cost = this.convertToLocalCurrency(entity.getCost());
		model.setAttribute("cost", cost);
		
		model.setAttribute("masterId", entity.getTheoryTutorial().getId());
		
		boolean publishedTutorial = entity.getTheoryTutorial().isPublish();
		model.setAttribute("publishedTutorial", publishedTutorial);
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
