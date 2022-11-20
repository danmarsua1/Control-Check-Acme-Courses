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
	<acme:input-textbox readonly="true" code="learner.follow-up.form.label.sequenceNumber" path="sequenceNumber"/>
	<acme:input-moment readonly="true" code="learner.follow-up.form.label.creationMoment" path="creationMoment"/>
	<acme:input-textbox code="learner.follow-up.form.label.message" path="message"/>
	<acme:input-url code="learner.follow-up.form.label.link" path="link"/>
	
	<jstl:if test="${command == 'create' }">
		<acme:hidden-data path="helperRequestId"/>
		<acme:input-checkbox code="learner.follow-up.form.label.confirmation" path="confirmation"/>
		<acme:submit code="learner.follow-up.form.button.create" action="/learner/follow-up/create"/>
	</jstl:if>
</acme:form>
