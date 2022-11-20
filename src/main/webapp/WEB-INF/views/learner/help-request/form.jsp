<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for learner particular
- purposes.  The copyright owner does not offer learner warranties or representations, nor do
- they accept learner liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	
	<jstl:if test="${publish eq false}">
		<acme:input-textbox readonly="true" code="learner.help-request.form.label.ticker" path="ticker"/>
		<acme:input-textbox code="learner.help-request.form.label.statement" path="statement"/>
		<acme:input-textbox readonly="true" code="learner.help-request.form.label.creationMoment" path="creationMoment"/>
		<acme:input-moment code="learner.help-request.form.label.initDate" path="initDate"/>
		<acme:input-moment code="learner.help-request.form.label.finishDate" path="finishDate"/>
		<acme:input-money code="learner.help-request.form.label.budget" path="budget"/>
		<acme:input-url code="learner.help-request.form.label.link" path="link"/>
	</jstl:if>
	
	<jstl:if test="${publish eq true}">
		<acme:input-textbox readonly="true" code="learner.help-request.form.label.ticker" path="ticker"/>
		<acme:input-textbox readonly="true" code="learner.help-request.form.label.statement" path="statement"/>
		<acme:input-moment readonly="true" code="learner.help-request.form.label.creationMoment" path="creationMoment"/>
		<acme:input-moment readonly="true" code="learner.help-request.form.label.initDate" path="initDate"/>
		<acme:input-moment readonly="true" code="learner.help-request.form.label.finishDate" path="finishDate"/>
		<acme:input-textbox readonly="true" code="learner.help-request.form.label.status" path="status"/>
		<acme:input-money readonly="true" code="learner.help-request.form.label.budget" path="budget"/>
		<acme:input-url readonly="true" code="learner.help-request.form.label.link" path="link"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${command != 'create' && publish eq false}">
			<acme:hidden-data path="status"/>
			<acme:submit code="learner.help-request.form.button.update" action="/learner/help-request/update"/>
			<acme:submit code="learner.help-request.form.button.delete" action="/learner/help-request/delete"/>
			<acme:submit code="learner.help-request.form.button.publish" action="/learner/help-request/publish"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:hidden-data path="status"/>
			<acme:input-select code="learner.help-request.form.label.select-teacher" path="teachers">
				<jstl:forEach items="${teachers}" var="teacher">
					<acme:input-option code="${teacher.userAccount.username}" value="${teacher.userAccount.username}" />
				</jstl:forEach>
			</acme:input-select>
			<acme:submit code="learner.help-request.form.button.create" action="/learner/help-request/create"/>
		</jstl:when>
	</jstl:choose>
	<jstl:if test="${status=='ACCEPTED' && publish eq true}">
		<acme:button code="learner.help-request.follow-up.form.button.create" action="/learner/follow-up/create?helperRequestId=${id}" />
	</jstl:if>
</acme:form>
