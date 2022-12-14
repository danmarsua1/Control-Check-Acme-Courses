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
		<acme:input-textbox readonly="true" code="teacher.theory-tutorial.form.label.ticker" path="ticker"/>
		<acme:input-textbox code="teacher.theory-tutorial.form.label.title" path="title"/>
		<acme:input-textbox code="teacher.theory-tutorial.form.label.abstractText" path="abstractText"/>
		<acme:input-money code="teacher.theory-tutorial.form.label.cost" path="cost"/>
		<acme:input-url code="teacher.theory-tutorial.form.label.link" path="link"/>
	</jstl:if>
	
	<jstl:if test="${publish eq true}">
		<acme:input-textbox readonly="true" code="teacher.theory-tutorial.form.label.ticker" path="ticker"/>
		<acme:input-textbox readonly="true" code="teacher.theory-tutorial.form.label.title" path="title"/>
		<acme:input-textbox readonly="true" code="teacher.theory-tutorial.form.label.abstractText" path="abstractText"/>
		<acme:input-money readonly="true" code="teacher.theory-tutorial.form.label.cost" path="cost"/>
		<acme:input-url readonly="true" code="teacher.theory-tutorial.form.label.link" path="link"/>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${command != 'create' && publish eq false}">
			<acme:input-select code="teacher.course.form.label.selectCourse" path="courses">
				<jstl:forEach items="${courses}" var="course">
					<acme:input-option code="${course.caption}" value="${course.ticker}" />
				</jstl:forEach>
			</acme:input-select>
			<acme:input-integer code="teacher.theory-tutorial.form.label.learning-time" path="learningTime"/>
			<acme:submit code="teacher.theory-tutorial.form.button.update" action="/teacher/theory-tutorial/update"/>
			<acme:submit code="teacher.theory-tutorial.form.button.delete" action="/teacher/theory-tutorial/delete"/>
			<acme:submit code="teacher.theory-tutorial.form.button.publish" action="/teacher/theory-tutorial/publish"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:input-select code="teacher.course.form.label.selectCourse" path="courses">
				<jstl:forEach items="${courses}" var="course">
					<acme:input-option code="${course.caption}" value="${course.ticker}" />
				</jstl:forEach>
			</acme:input-select>
			<acme:input-integer code="teacher.theory-tutorial.form.label.learning-time" path="learningTime"/>
			<acme:submit code="teacher.theory-tutorial.form.button.create" action="/teacher/theory-tutorial/create"/>
		</jstl:when>
	</jstl:choose>
	
	<jstl:if test="${command != 'create'}">
		<jstl:if test="${hasBlahblah eq false && publish eq false}">
			<acme:button code="teacher.theory-tutorial.blahblah.form.button.create" action="/teacher/blahblah/create?masterId=${id}&publishedTutorial=${publish}" />
		</jstl:if>
		<jstl:if test="${hasBlahblah eq true}">
			<acme:button code="teacher.theory-tutorial.blahblah.form.button.view-blahblah" action="/teacher/blahblah/show?id=${blahblahId}" />
		</jstl:if>
	</jstl:if>
	
</acme:form>
