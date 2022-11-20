/*
 * LearnerHelpRequestShowService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.learner.helpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.HelpRequest;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractShowService;
import acme.roles.Learner;

@Service
public class LearnerHelpRequestShowService implements AbstractShowService<Learner, HelpRequest> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected LearnerHelpRequestRepository repository;
	
	@Autowired
	protected AdministratorConfigurationRepository configurationRepository;

	@Override
	public boolean authorise(final Request<HelpRequest> request) {
		assert request != null;

		boolean result;
		int helpRequestId;
		HelpRequest helpRequest;
		boolean isOwner;

		helpRequestId = request.getModel().getInteger("id");
		helpRequest = this.repository.findOneHelpRequestById(helpRequestId);
		Integer teacher = this.repository.findLearnerByHelpRequestId(helpRequestId);
		isOwner = teacher == request.getPrincipal().getActiveRoleId();
		result = helpRequest != null && isOwner;

		return result;
	}

	@Override
	public HelpRequest findOne(final Request<HelpRequest> request) {
		assert request != null;

		int helpRequestId;
		HelpRequest result;

		helpRequestId = request.getModel().getInteger("id");
		result = this.repository.findOneHelpRequestById(helpRequestId);

		return result;
	}
	
	@Override
	public void unbind(final Request<HelpRequest> request, final HelpRequest entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "statement", "creationMoment", "budget", "initDate", "finishDate", "status", "link", "publish");
		
		Money totalPrice = this.convertToLocalCurrency(entity.getBudget());
		model.setAttribute("budget", totalPrice);
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
