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

<acme:form>
	<acme:input-moment readonly="true" code="any.blink.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="any.blink.form.label.caption" path="caption"/>
	<acme:input-textbox code="any.blink.form.label.author" path="author"/>
	<acme:input-textarea code="any.blink.form.label.message" path="message"/>
	<acme:input-email code="any.blink.form.label.email" path="email"/>
	
	<jstl:if test="${command == 'create' }">
		<acme:input-checkbox code="any.blink.form.label.confirmation" path="confirmation"/>
		<acme:submit code="any.blink.form.button.create" action="/any/blink/create"/>
	</jstl:if>
</acme:form>
