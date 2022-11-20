package acme.features.learner.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface LearnerDashboardRepository extends AbstractRepository {

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