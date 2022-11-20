<%--
- list.jsp
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
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<acme:list>
	<acme:list-column  code="authenticated.post.form.label.flag"
		path="flag" />
	<acme:list-column  code="authenticated.post.list.label.caption"
		path="caption" />
	<acme:list-column code="authenticated.post.list.label.creation-moment"
		path="creationMoment" />
	<acme:list-column code="authenticated.post.list.label.message"
		path="message" width="60%" />
</acme:list>
