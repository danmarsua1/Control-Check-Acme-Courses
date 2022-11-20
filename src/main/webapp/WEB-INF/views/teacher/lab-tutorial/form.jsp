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
	<jstl:if test="${publish eq false}">
		<acme:input-textbox readonly="true" code="teacher.lab-tutorial.form.label.ticker" path="ticker"/>
		<acme:input-textbox code="teacher.lab-tutorial.form.label.title" path="title"/>
		<acme:input-textbox code="teacher.lab-tutorial.form.label.abstractText" path="abstractText"/>
		<acme:input-money code="teacher.lab-tutorial.form.label.cost" path="cost"/>
		<acme:input-url code="teacher.lab-tutorial.form.label.link" path="link"/>
	</jstl:if>
	
	<jstl:if test="${publish eq true}">
		<acme:input-textbox readonly="true" code="teacher.lab-tutorial.form.label.ticker" path="ticker"/>
		<acme:input-textbox readonly="true" code="teacher.lab-tutorial.form.label.title" path="title"/>
		<acme:input-textbox readonly="true" code="teacher.lab-tutorial.form.label.abstractText" path="abstractText"/>
		<acme:input-money readonly="true" code="teacher.lab-tutorial.form.label.cost" path="cost"/>
		<acme:input-url readonly="true" code="teacher.lab-tutorial.form.label.link" path="link"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${command != 'create' && publish eq false}">
			<acme:input-select code="teacher.course.form.label.selectCourse" path="courses">
				<jstl:forEach items="${courses}" var="course">
					<acme:input-option code="${course.caption}" value="${course.ticker}" />
				</jstl:forEach>
			</acme:input-select>
			<acme:input-integer code="teacher.lab-tutorial.form.label.learning-time" path="learningTime"/>
			<acme:submit code="teacher.lab-tutorial.form.button.update" action="/teacher/lab-tutorial/update"/>
			<acme:submit code="teacher.lab-tutorial.form.button.delete" action="/teacher/lab-tutorial/delete"/>
			<acme:submit code="teacher.lab-tutorial.form.button.publish" action="/teacher/lab-tutorial/publish"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:input-select code="teacher.course.form.label.selectCourse" path="courses">
				<jstl:forEach items="${courses}" var="course">
					<acme:input-option code="${course.caption}" value="${course.ticker}" />
				</jstl:forEach>
			</acme:input-select>
			<acme:input-integer code="teacher.lab-tutorial.form.label.learning-time" path="learningTime"/>
			<acme:submit code="teacher.lab-tutorial.form.button.create" action="/teacher/lab-tutorial/create"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
