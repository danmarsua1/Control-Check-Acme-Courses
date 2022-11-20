<%--
- form.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for teacher particular
- purposes.  The copyright owner does not offer teacher warranties or representations, nor do
- they accept teacher liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
<jstl:choose>
	<jstl:when test="${status=='PROPOSED'}">
		<acme:input-select code="teacher.help-request.form.label.status" path="status">
			<acme:input-option code="PROPOSED" value="PROPOSED" selected="${status == 'PROPOSED'}"/>
			<acme:input-option code="ACCEPTED" value="ACCEPTED" selected="${status == 'ACCEPTED'}"/>
			<acme:input-option code="DENIED" value="DENIED" selected="${status == 'DENIED'}"/>
		</acme:input-select>
	</jstl:when>
	<jstl:otherwise>
		<acme:input-textbox readonly='true' code="teacher.help-request.form.label.status" path="status"/>
	</jstl:otherwise>
</jstl:choose>
	
	<acme:input-textbox readonly='true' code="teacher.help-request.form.label.ticker" path="ticker"/>
	<acme:input-textbox readonly='true' code="teacher.help-request.form.label.statement" path="statement"/>
	<acme:input-moment readonly='true' code="teacher.help-request.form.label.creationMoment" path="creationMoment"/>
	<acme:input-moment readonly='true' code="teacher.help-request.form.label.initDate" path="initDate"/>
	<acme:input-moment readonly='true' code="teacher.help-request.form.label.finishDate" path="finishDate"/>
	<acme:input-money readonly='true' code="teacher.help-request.form.label.budget" path="budget"/>
	<acme:input-url readonly='true' code="teacher.help-request.form.label.link" path="link"/>
	
	<jstl:if test="${acme:anyOf(command, 'show, update') && publish == 'false' && status=='PROPOSED'}">
		<acme:submit code="teacher.help-request.form.button.update" action="/teacher/help-request/update"/>
	</jstl:if>
	<jstl:if test="${status=='ACCEPTED'}">
		<acme:button code="teacher.help-request.follow-up.form.button.create" action="/teacher/follow-up/create?helperRequestId=${id}" />
	</jstl:if>
</acme:form>
