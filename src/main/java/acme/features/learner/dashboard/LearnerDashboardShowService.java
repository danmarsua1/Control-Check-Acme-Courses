package acme.features.learner.dashboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.dashboard.LearnerDashboard;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Learner;


@Service
public class LearnerDashboardShowService implements AbstractShowService<Learner,LearnerDashboard>{
		@Autowired
		protected LearnerDashboardRepository repository;
		
		@Override
		public boolean authorise(final Request<LearnerDashboard>request) {
			assert request != null;
			return true;
		}

		@Override
		public LearnerDashboard findOne(final Request<LearnerDashboard>request) {
			assert request != null;
			
			LearnerDashboard result;
			
			// Manage TOTALS
			Integer	totalNumberOfProposedHelpRequests;
			Integer	totalNumberOfAcceptedHelpRequests;
			Integer	totalNumberOfDeniedHelpRequests;
			
			// Manage PROPOSED
			List<Object> averageBudgetOfProposedHelpRequests;
			List<Object> deviationBudgetOfProposedHelpRequests;
			List<Object> minimumBudgetOfProposedHelpRequests;
			List<Object> maximumBudgetOfProposedHelpRequests;
			
			// Manage ACCEPTED
			List<Object> averageBudgetOfAcceptedHelpRequests;
			List<Object> deviationBudgetOfAcceptedHelpRequests;
			List<Object> minimumBudgetOfAcceptedHelpRequests;
			List<Object> maximumBudgetOfAcceptedHelpRequests;
			
			// Manage DENIED
			List<Object> averageBudgetOfDeniedHelpRequests;
			List<Object> deviationBudgetOfDeniedHelpRequests;
			List<Object> minimumBudgetOfDeniedHelpRequests;
			List<Object> maximumBudgetOfDeniedHelpRequests;
			
			totalNumberOfProposedHelpRequests = this.repository.totalNumberOfProposedHelpRequests();
			averageBudgetOfProposedHelpRequests = this.repository.averageBudgetOfProposedHelpRequests();
			deviationBudgetOfProposedHelpRequests = this.repository.deviationBudgetOfProposedHelpRequests();
			minimumBudgetOfProposedHelpRequests = this.repository.minimumBudgetOfProposedHelpRequests();
			maximumBudgetOfProposedHelpRequests = this.repository.maximumBudgetOfProposedHelpRequests();
			
			totalNumberOfAcceptedHelpRequests = this.repository.totalNumberOfAcceptedHelpRequests();
			averageBudgetOfAcceptedHelpRequests = this.repository.averageBudgetOfAcceptedHelpRequests();
			deviationBudgetOfAcceptedHelpRequests = this.repository.deviationBudgetOfAcceptedHelpRequests();
			minimumBudgetOfAcceptedHelpRequests = this.repository.minimumBudgetOfAcceptedHelpRequests();
			maximumBudgetOfAcceptedHelpRequests = this.repository.maximumBudgetOfAcceptedHelpRequests();
			
			totalNumberOfDeniedHelpRequests = this.repository.totalNumberOfDeniedHelpRequests();
			averageBudgetOfDeniedHelpRequests = this.repository.averageBudgetOfDeniedHelpRequests();
			deviationBudgetOfDeniedHelpRequests = this.repository.deviationBudgetOfDeniedHelpRequests();
			minimumBudgetOfDeniedHelpRequests = this.repository.minimumBudgetOfDeniedHelpRequests();
			maximumBudgetOfDeniedHelpRequests = this.repository.maximumBudgetOfDeniedHelpRequests();
			
			result = new LearnerDashboard();
			result.setTotalNumberOfProposedHelpRequests(totalNumberOfProposedHelpRequests);
			result.setAverageBudgetOfProposedHelpRequests(averageBudgetOfProposedHelpRequests);
			result.setDeviationBudgetOfProposedHelpRequests(deviationBudgetOfProposedHelpRequests);
			result.setMinimumBudgetOfProposedHelpRequests(minimumBudgetOfProposedHelpRequests);
			result.setMaximumBudgetOfProposedHelpRequests(maximumBudgetOfProposedHelpRequests);
			
			result.setTotalNumberOfAcceptedHelpRequests(totalNumberOfAcceptedHelpRequests);
			result.setAverageBudgetOfAcceptedHelpRequests(averageBudgetOfAcceptedHelpRequests);
			result.setDeviationBudgetOfAcceptedHelpRequests(deviationBudgetOfAcceptedHelpRequests);
			result.setMinimumBudgetOfAcceptedHelpRequests(minimumBudgetOfAcceptedHelpRequests);
			result.setMaximumBudgetOfAcceptedHelpRequests(maximumBudgetOfAcceptedHelpRequests);
			
			result.setTotalNumberOfDeniedHelpRequests(totalNumberOfDeniedHelpRequests);
			result.setAverageBudgetOfDeniedHelpRequests(averageBudgetOfDeniedHelpRequests);
			result.setDeviationBudgetOfDeniedHelpRequests(deviationBudgetOfDeniedHelpRequests);
			result.setMinimumBudgetOfDeniedHelpRequests(minimumBudgetOfDeniedHelpRequests);
			result.setMaximumBudgetOfDeniedHelpRequests(maximumBudgetOfDeniedHelpRequests);
			
			return result;
		}
		
		@Override
		public void unbind(final Request<LearnerDashboard>request, final LearnerDashboard entity, final Model model) {
			assert request != null;
			assert entity != null;
			assert model != null;
			
			request.unbind(entity, model, "totalNumberOfProposedHelpRequests","totalNumberOfAcceptedHelpRequests","totalNumberOfDeniedHelpRequests","averageBudgetOfProposedHelpRequests",
											"deviationBudgetOfProposedHelpRequests","minimumBudgetOfProposedHelpRequests","maximumBudgetOfProposedHelpRequests",
											"averageBudgetOfAcceptedHelpRequests","deviationBudgetOfAcceptedHelpRequests","minimumBudgetOfAcceptedHelpRequests",
											"maximumBudgetOfAcceptedHelpRequests","averageBudgetOfDeniedHelpRequests","deviationBudgetOfDeniedHelpRequests",
											"minimumBudgetOfDeniedHelpRequests","maximumBudgetOfDeniedHelpRequests");
		}
}
