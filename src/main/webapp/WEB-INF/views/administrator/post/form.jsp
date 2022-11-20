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
	<acme:input-moment readonly="true" code="administrator.post.form.label.creation-moment" path="creationMoment"/>
	<acme:input-textbox code="administrator.post.form.label.caption" path="caption"/>
	<acme:input-textarea code="administrator.post.form.label.message" path="message"/>
	<acme:input-select code="administrator.post.form.label.flag" path="flag">
			<acme:input-option code="administrator.post.form.label.flag.yes" value="true" />
			<acme:input-option code="administrator.post.form.label.flag.no" value="false" />
	</acme:input-select>
	<acme:input-url code="administrator.post.form.label.link" path="link"/>
	
	<acme:input-checkbox code="administrator.post.form.label.confirmation" path="confirmation"/>
	<acme:submit code="administrator.post.form.button.create" action="/administrator/post/create"/>
</acme:form>