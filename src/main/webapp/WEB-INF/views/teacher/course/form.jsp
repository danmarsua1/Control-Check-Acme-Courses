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
	<jstl:if test="${publish eq false}">
		<acme:input-textbox readonly="true"
			code="teacher.course.form.label.ticker" path="ticker" />
		<acme:input-textbox code="teacher.course.form.label.caption"
			path="caption" />
		<acme:input-textbox code="teacher.course.form.label.abstractText"
			path="abstractText" />
		<acme:input-url code="teacher.course.form.label.link" path="link" />
	</jstl:if>

	<jstl:if test="${publish eq true}">
		<acme:input-textbox readonly="true"
			code="teacher.course.form.label.ticker" path="ticker" />
		<acme:input-textbox readonly="true"
			code="teacher.course.form.label.caption" path="caption" />
		<acme:input-textbox readonly="true"
			code="teacher.course.form.label.abstractText" path="abstractText" />
		<acme:input-url readonly="true"
			code="teacher.course.form.label.link" path="link" />
		<jstl:if test="${hasTheoryTutorial eq true || hasLabTutorial eq true}">
			<acme:input-textbox readonly="true"
			code="teacher.course.form.label.totalPrice" path="totalPrice" />
		</jstl:if>
	</jstl:if>

	<jstl:choose>
		<jstl:when test="${command != 'create' && publish eq false}">
			<acme:submit code="teacher.course.form.button.update"
				action="/teacher/course/update" />
			<acme:submit code="teacher.course.form.button.delete"
				action="/teacher/course/delete" />
			<acme:submit code="teacher.course.form.button.publish"
				action="/teacher/course/publish" />
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="teacher.course.form.button.create"
				action="/teacher/course/create" />
		</jstl:when>
	</jstl:choose>

	<jstl:if test="${command != 'create'}">
		<jstl:if test="${hasTheoryTutorial eq true}">
			<acme:button code="teacher.course.form.label.list-theory-tutorial"
				action="/teacher/theory-tutorial/list?masterId=${id}" />
		</jstl:if>
		<jstl:if test="${hasLabTutorial eq true}">
			<acme:button code="teacher.course.form.label.list-lab-tutorial"
				action="/teacher/lab-tutorial/list?masterId=${id}" />
		</jstl:if>
	</jstl:if>


</acme:form>
