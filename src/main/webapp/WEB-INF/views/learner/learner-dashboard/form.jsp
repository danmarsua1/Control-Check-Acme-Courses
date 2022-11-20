<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>



<table class="table table-sm">
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.totalNumberOfProposedHelpRequests" />
		</th>
		<td><acme:print value="${totalNumberOfProposedHelpRequests}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.totalNumberOfAcceptedHelpRequests" /></th>
		<td><acme:print value="${totalNumberOfAcceptedHelpRequests}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.totalNumberOfDeniedHelpRequests" /></th>
		<td><acme:print value="${totalNumberOfDeniedHelpRequests}" /></td>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.averageBudgetOfProposedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${averageBudgetOfProposedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.deviationBudgetOfProposedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${deviationBudgetOfProposedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.minimumBudgetOfProposedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${minimumBudgetOfProposedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.maximumBudgetOfProposedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${maximumBudgetOfProposedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
		<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.averageBudgetOfAcceptedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${averageBudgetOfAcceptedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.deviationBudgetOfAcceptedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${deviationBudgetOfAcceptedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.minimumBudgetOfAcceptedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${minimumBudgetOfAcceptedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.maximumBudgetOfAcceptedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${maximumBudgetOfAcceptedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
			<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.averageBudgetOfDeniedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${averageBudgetOfDeniedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.deviationBudgetOfDeniedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${deviationBudgetOfDeniedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.minimumBudgetOfDeniedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${minimumBudgetOfDeniedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
	<tr>
		<th scope="row"><acme:message
				code="learner.dashboard.form.label.maximumBudgetOfDeniedHelpRequests" /></th>
		<jstl:forEach var="prices" items="${maximumBudgetOfDeniedHelpRequests}" >
			<td><acme:print value="${prices}" /></td>
		</jstl:forEach>
	</tr>
</table>