
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox code="authenticated.teacher.teacher.form.label.school" path="school"/>
	<acme:input-textbox code="authenticated.teacher.teacher.form.label.statement" path="statement"/>
	<acme:input-url code="authenticated.teacher.teacher.form.label.link" path="link"/>
	
	<acme:submit test="${command == 'create'}" code="authenticated.teacher.teacher.form.button.create" action="/authenticated/teacher/create"/>
	<acme:submit test="${command == 'update'}" code="authenticated.teacher.teacher.form.button.update" action="/authenticated/teacher/update"/>
</acme:form>