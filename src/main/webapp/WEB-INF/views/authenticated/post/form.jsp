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
	<acme:input-moment code="authenticated.post.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="authenticated.post.form.label.caption" path="caption"/>
	<acme:input-textarea code="authenticated.post.form.label.message" path="message"/>
	<acme:input-textbox code="authenticated.post.form.label.flag" path="flag"/>
	<acme:input-url code="authenticated.post.form.label.link" path="link"/>
</acme:form>