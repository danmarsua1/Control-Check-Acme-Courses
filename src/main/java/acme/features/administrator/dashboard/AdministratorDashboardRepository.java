package acme.features.administrator.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {


	// Manage Theory tutorials
	@Query("select count(t) from TheoryTutorial t")
	Integer totalNumberOfTheoryTutorials();

	@Query("select avg(t.cost.amount), t.cost.currency from TheoryTutorial t group by t.cost.currency")
	List<Object> averageCostOfTheoryTutorials();

	@Query("select stddev(t.cost.amount), t.cost.currency from TheoryTutorial t group by t.cost.currency")
	List<Object> deviationCostOfTheoryTutorials();
	
	@Query("select min(t.cost.amount), t.cost.currency from TheoryTutorial t group by t.cost.currency")
	List<Object> minimumCostOfTheoryTutorials();

	@Query("select max(t.cost.amount), t.cost.currency from TheoryTutorial t group by t.cost.currency")
	List<Object> maximumCostOfTheoryTutorials();
	
	// Manage Lab tutorials
	@Query("select count(l) from LabTutorial l")
	Integer totalNumberOfLabTutorials();
	
	@Query("select avg(l.cost.amount), l.cost.currency from LabTutorial l group by l.cost.currency")
	List<Object> averageCostOfLabTutorials();

	@Query("select stddev(l.cost.amount), l.cost.currency from LabTutorial l group by l.cost.currency")
	List<Object> deviationCostOfLabTutorials();

	@Query("select min(l.cost.amount), l.cost.currency from LabTutorial l group by l.cost.currency")
	List<Object> minimumCostOfLabTutorials();
	
	@Query("select max(l.cost.amount), l.cost.currency from LabTutorial l group by l.cost.currency")
	List<Object> maximumCostOfLabTutorials();
	
	// Manage TOTALS
	@Query("select count(h) from HelpRequest h where h.status = 'PROPOSED'")
	Integer totalNumberOfProposedHelpRequests();

	@Query("select count(h) from HelpRequest h where h.status = 'ACCEPTED'")
	Integer totalNumberOfAcceptedHelpRequests();

	@Query("select count(h) from HelpRequest h where h.status = 'DENIED'")
	Integer totalNumberOfDeniedHelpRequests();

	// Manage PROPOSED
	@Query("select avg(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'PROPOSED' group by h.budget.currency")
	List<Object> averageBudgetOfProposedHelpRequests();

	@Query("select stddev(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'PROPOSED' group by h.budget.currency")
	List<Object> deviationBudgetOfProposedHelpRequests();

	@Query("select min(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'PROPOSED' group by h.budget.currency")
	List<Object> minimumBudgetOfProposedHelpRequests();

	@Query("select max(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'PROPOSED' group by h.budget.currency")
	List<Object> maximumBudgetOfProposedHelpRequests();

	// Manage ACCEPTED
	@Query("select avg(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'ACCEPTED' group by h.budget.currency")
	List<Object> averageBudgetOfAcceptedHelpRequests();

	@Query("select stddev(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'ACCEPTED' group by h.budget.currency")
	List<Object> deviationBudgetOfAcceptedHelpRequests();

	@Query("select min(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'ACCEPTED' group by h.budget.currency")
	List<Object> minimumBudgetOfAcceptedHelpRequests();

	@Query("select max(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'ACCEPTED' group by h.budget.currency")
	List<Object> maximumBudgetOfAcceptedHelpRequests();

	// Manage DENIED
	@Query("select avg(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'DENIED' group by h.budget.currency")
	List<Object> averageBudgetOfDeniedHelpRequests();

	@Query("select stddev(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'DENIED' group by h.budget.currency")
	List<Object> deviationBudgetOfDeniedHelpRequests();

	@Query("select min(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'DENIED' group by h.budget.currency")
	List<Object> minimumBudgetOfDeniedHelpRequests();

	@Query("select max(h.budget.amount), h.budget.currency from HelpRequest h where h.status = 'DENIED' group by h.budget.currency")
	List<Object> maximumBudgetOfDeniedHelpRequests();
	
}