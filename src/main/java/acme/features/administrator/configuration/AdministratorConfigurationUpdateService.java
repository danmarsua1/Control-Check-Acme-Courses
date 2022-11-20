package acme.features.administrator.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.datatypes.SpamRecord;
import acme.entities.Configuration;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.roles.Administrator;
import acme.framework.services.AbstractUpdateService;
import acme.helpers.SpamHelper;

@Service
public class AdministratorConfigurationUpdateService implements AbstractUpdateService<Administrator,Configuration>{
	@Autowired
	protected AdministratorConfigurationRepository repository;
	
	@Autowired
	private SpamHelper spamHelper;
	
	@Override
	public boolean authorise(final Request<Configuration>request) {
		assert request!=null;
		return true;
	}
	
	@Override
	public Configuration findOne(final Request<Configuration>request) {
		assert request != null;
		
		Configuration result;
		
		result = this.repository.findConfiguration();
		
		return result;
	}
	
	@Override
	public void bind(final Request<Configuration> request, final Configuration entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		request.bind(entity, errors, "currency","acceptedCurrencies","spamThreshold","spamBooster");
		
		String newTerm = (String) request.getModel().getAttribute("newTerm");
		
		if(!"".equals(newTerm)) {
			Double newWeight = !"".equals((String) request.getModel().getAttribute("newWeight")) 
				?  Double.valueOf( (String)request.getModel().getAttribute("newWeight")) 
				: 0.0;
			String newBoosterTerm =	!"".equals((String) request.getModel().getAttribute("newBoosterTerm")) 
				?  (String) request.getModel().getAttribute("newBoosterTerm")
				: "X";
			
			SpamRecord newSpamRecord = new SpamRecord(newTerm, newWeight, newBoosterTerm);
			List<SpamRecord> newSpamRecords = this.spamHelper.convertStringToSpamRecords(entity.getSpamRecords());
			newSpamRecords.add(newSpamRecord);
			
			entity.setSpamRecords(this.spamHelper.convertSpamRecordsToString(newSpamRecords));
		}
	}
	
	@Override
	public void unbind(final Request<Configuration> request,final Configuration entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		
		request.unbind(entity, model,"currency","acceptedCurrencies","spamRecords","spamThreshold","spamBooster");
		model.setAttribute("spamRecordObjects", this.spamHelper.convertStringToSpamRecords(entity.getSpamRecords()));
		
	}
	
	@Override
	public void validate(final Request<Configuration> request, final Configuration entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		if(!errors.hasErrors("currency")) {
			final List<String> acceptedCurrenciesList = Arrays.asList(entity.getAcceptedCurrencies().split(","));
			errors.state(request, acceptedCurrenciesList.contains(entity.getCurrency()), "currency", "administrator.configuration.configuration.form.error.currency");
		}
		
	}
	
	@Override
	public void update(final Request<Configuration> request, final Configuration entity) {
		assert request != null;
		assert entity != null;
		
		this.repository.save(entity);
		
	}
}
