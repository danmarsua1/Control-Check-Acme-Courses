package acme.entities.dashboard;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard implements Serializable {

	private static final long	serialVersionUID	= 1L;

	Integer						totalNumberOfTheoryTutorials;

	List<Object>						averageCostOfTheoryTutorials;

	List<Object>						deviationCostOfTheoryTutorials;

	List<Object>						minimumCostOfTheoryTutorials;

	List<Object>						maximumCostOfTheoryTutorials;

	Integer						totalNumberOfLabTutorials;

	List<Object>						averageCostOfLabTutorials;

	List<Object>						deviationCostOfLabTutorials;

	List<Object>						minimumCostOfLabTutorials;

	List<Object>						maximumCostOfLabTutorials;

	Integer						totalNumberOfProposedHelpRequests;

	Integer						totalNumberOfAcceptedHelpRequests;

	Integer						totalNumberOfDeniedHelpRequests;

	List<Object>						averageBudgetOfProposedHelpRequests;

	List<Object>						deviationBudgetOfProposedHelpRequests;

	List<Object>						minimumBudgetOfProposedHelpRequests;

	List<Object>						maximumBudgetOfProposedHelpRequests;

	List<Object>						averageBudgetOfAcceptedHelpRequests;

	List<Object>						deviationBudgetOfAcceptedHelpRequests;

	List<Object>						minimumBudgetOfAcceptedHelpRequests;

	List<Object>						maximumBudgetOfAcceptedHelpRequests;

	List<Object>						averageBudgetOfDeniedHelpRequests;

	List<Object>						deviationBudgetOfDeniedHelpRequests;

	List<Object>						minimumBudgetOfDeniedHelpRequests;

	List<Object>						maximumBudgetOfDeniedHelpRequests;

}