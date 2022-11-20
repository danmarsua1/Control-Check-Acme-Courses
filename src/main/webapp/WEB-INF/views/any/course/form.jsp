<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:input-textbox code="any.course.form.label.ticker" path="ticker"/>
	<acme:input-textbox code="any.course.form.label.caption" path="caption"/>
	<acme:input-textbox code="any.course.form.label.abstractText" path="abstractText"/>
	<acme:input-url code="any.course.form.label.link" path="link"/>
	<acme:input-textbox code="any.course.form.label.cost" path="totalPrice"/>
	<jstl:if test="${hasTheoryTutorial eq true}">
		<acme:button code="any.course.form.label.list-theory-tutorial" action="/any/theory-tutorial/list?masterId=${id}"/>
	</jstl:if>
	<jstl:if test="${hasLabTutorial eq true}">
		<acme:button code="any.course.form.label.list-lab-tutorial" action="/any/lab-tutorial/list?masterId=${id}"/>
	</jstl:if>
</acme:form>