package acme.features.any.course;

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
import acme.framework.roles.Any;
import acme.framework.services.AbstractShowService;

@Service
public class AnyCourseShowService implements AbstractShowService<Any, Course>{

	
	@Autowired
	protected AnyCourseRepository repository;
	
	@Autowired
	protected AnyTheoryTutorialRepository theoryTutorialRepository;
	
	@Autowired
	protected AnyLabTutorialRepository labTutorialRepository;
	
	@Autowired
	protected AdministratorConfigurationRepository configurationRepository;
	
	@Override
	public boolean authorise(final Request<Course> request) {
		assert request != null;
		
		return true;
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
	public void unbind(final Request<Course> request, final Course entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		boolean hasTheoryTutorial = false;
		boolean hasLabTutorial = false;
		
		final int courseId = request.getModel().getInteger("id");
		List<Object[]> priceTheoryTutorials = this.repository.getCourseTheoryTutorialsPrice(courseId);
		List<Object[]> priceLabTutorials = this.repository.getCourseLabTutorialsPrice(courseId);
		Money moneyTheoryTutorials = this.convertToLocalCurrencyAndSum(priceTheoryTutorials);
		Money moneyLabTutorials = this.convertToLocalCurrencyAndSum(priceLabTutorials);

		Money total = new Money();
		total.setCurrency(moneyTheoryTutorials.getCurrency());
		total.setAmount(moneyTheoryTutorials.getAmount()+moneyLabTutorials.getAmount());
		model.setAttribute("totalPrice", total);
		
		request.unbind(entity, model, "ticker", "caption", "abstractText", "link");
		
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
