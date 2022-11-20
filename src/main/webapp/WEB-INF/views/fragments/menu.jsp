<%--
- menu.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.roles.Teacher,acme.roles.Learner"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link"
				action="http://www.example.com/" />
			<acme:menu-suboption
				code="master.menu.anonymous.daniel-favourite-link"
				action="http://www.twitter.com/" />
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.user-accounts" action="/any/user-account/list"/>
			<acme:menu-suboption code="master.menu.anonymous.create-blink" action="/any/blink/create"/>
			<acme:menu-suboption code="master.menu.anonymous.list-blink" action="/any/blink/list"/>
			<acme:menu-suboption code="master.menu.anonymous.list-theory-tutorial" action="/any/theory-tutorial/list"/>
			<acme:menu-suboption code="master.menu.anonymous.list-lab-tutorial" action="/any/lab-tutorial/list"/>
			<acme:menu-suboption code="master.menu.anonymous.list-course" action="/any/course/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.view-configuration" action="/administrator/configuration/show"/>
			<acme:menu-suboption code="master.menu.administrator.view-dashboard" action="/administrator/administrator-dashboard/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.create-post" action="/administrator/post/create"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.view-configuration" action="/authenticated/configuration/show"/>
			<acme:menu-suboption code="master.menu.authenticated.list-post" action="/authenticated/post/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.teacher" access="hasRole('Teacher')">
			<acme:menu-suboption code="master.menu.teacher.create-course" action="/teacher/course/create"/>
			<acme:menu-suboption code="master.menu.teacher.list-course" action="/teacher/course/list"/>
			<acme:menu-suboption code="master.menu.teacher.create-theory-tutorial" action="/teacher/theory-tutorial/create"/>
			<acme:menu-suboption code="master.menu.teacher.list-theory-tutorial" action="/teacher/theory-tutorial/list"/>
			<acme:menu-suboption code="master.menu.teacher.create-lab-tutorial" action="/teacher/lab-tutorial/create"/>
			<acme:menu-suboption code="master.menu.teacher.list-lab-tutorial" action="/teacher/lab-tutorial/list"/>
			<acme:menu-suboption code="master.menu.teacher.list-help-request" action="/teacher/help-request/list"/>
			<acme:menu-suboption code="master.menu.teacher.list-follow-up" action="/teacher/follow-up/list"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.learner" access="hasRole('Learner')">
			<acme:menu-suboption code="master.menu.learner.view-dashboard" action="/learner/learner-dashboard/show"/>
			<acme:menu-suboption code="master.menu.learner.create-help-request" action="/learner/help-request/create"/>
			<acme:menu-suboption code="master.menu.learner.list-help-request" action="/learner/help-request/list"/>
			<acme:menu-suboption code="master.menu.learner.list-follow-up" action="/learner/follow-up/list"/>
		</acme:menu-option>
	</acme:menu-left>
	
	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>
		
		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>			
			<acme:menu-suboption code="master.menu.user-account.become-teacher" action="/authenticated/teacher/create" access="!hasRole('Teacher')"/>
			<acme:menu-suboption code="master.menu.user-account.teacher" action="/authenticated/teacher/update" access="hasRole('Teacher')"/>
			<acme:menu-suboption code="master.menu.user-account.become-learner" action="/authenticated/learner/create" access="!hasRole('Learner')"/>
			<acme:menu-suboption code="master.menu.user-account.learner" action="/authenticated/learner/update" access="hasRole('Learner')"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

