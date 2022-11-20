
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="authenticated.learner.learner.form.label.school" path="school"/>
	<acme:input-textbox code="authenticated.learner.learner.form.label.statement" path="statement"/>
	<acme:input-url code="authenticated.learner.learner.form.label.link" path="link"/>
	
	<acme:submit test="${command == 'create'}" code="authenticated.learner.learner.form.button.create" action="/authenticated/learner/create"/>
	<acme:submit test="${command == 'update'}" code="authenticated.learner.learner.form.button.update" action="/authenticated/learner/update"/>
</acme:form>