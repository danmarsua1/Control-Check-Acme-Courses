<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="any.course.list.label.caption" path="caption" width="20%"/>
	<acme:list-column code="any.course.list.label.abstractText" path="abstractText" width="60%"/>		
</acme:list>