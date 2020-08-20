<%--
- menu.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.entities.roles.Provider,acme.entities.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>

		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.notice.list" action="/anonymous/notice/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.technology-record.list" action="/anonymous/technology-record/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.tool-record.list" action="/anonymous/tool-record/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.albgueram" action="https://github.com/albfree" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.josescrue-favourite-link" action="https://www.motogp.com/es" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.diego"
				action="https://www.linkedin.com/in/diego-horrillo-duran-99b3b7197/" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.brayan" action="https://github.com/brapercob/" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.joslobtro-favourite-link" action="https://www.youtube.com/" />
		</acme:menu-option>


		<acme:menu-option code="master.menu.bulletins.list" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.guerrero-bulletin.list" action="/anonymous/guerrero-bulletin/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.escobar-bulletin.list" action="/anonymous/escobar-bulletin/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.bulletins.horrillo-bulletin.list" action="/anonymous/horrillo-bulletin/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.perezBulletin.list" action="/anonymous/perez-bulletin/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.lobatoBulletin.list" action="/anonymous/lobato-bulletin/list" />
		</acme:menu-option>


		<acme:menu-option code="master.menu.bulletins.create" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.guerreroBulletin.create" action="/anonymous/guerrero-bulletin/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.escobar-bulletin.create" action="/anonymous/escobar-bulletin/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.bulletins.horrillo-bulletin.create" action="/anonymous/horrillo-bulletin/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.perezBulletin.create" action="/anonymous/perez-bulletin/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.anonymous.lobatoBulletin.create" action="/anonymous/lobato-bulletin/create" />
		</acme:menu-option>


		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.authenticated.forum.list" action="/authenticated/forum/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.authenticated.investment-round.list" action="/authenticated/investment-round/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.authenticated.notice.list" action="/authenticated/notice/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.authenticated.challenge" action="/authenticated/challenge/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.authenticated.overture.list" action="/authenticated/overture/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.authenticated.inquiry.list" action="/authenticated/inquiry/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.technology-record.list" action="/authenticated/technology-record/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.tool-record.list" action="/authenticated/tool-record/list" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.investor" access="hasRole('Investor')">
			<acme:menu-suboption code="master.menu.investor.application.list-mine" action="/investor/application/list-mine" />
		</acme:menu-option>


		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.customization" action="/administrator/customization/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.shutdown" action="/master/shutdown" />
		</acme:menu-option>


		<acme:menu-option code="master.menu.administrator.list" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.overture.list" action="/administrator/overture/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.notice.list" action="/administrator/notice/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.inquiry.list" action="/administrator/inquiry/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.challenge.list" action="/administrator/challenge/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.technology-record.list" action="/administrator/technology-record/list" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.tool-record.list" action="/administrator/tool-record/list" />
		</acme:menu-option>


		<acme:menu-option code="master.menu.administrator.create" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.overture.create" action="/administrator/overture/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.notice.create" action="/administrator/notice/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.inquiry.create" action="/administrator/inquiry/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.challenge.create" action="/administrator/challenge/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.technology-record.create" action="/administrator/technology-record/create" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.administrator.tool-record.create" action="/administrator/tool-record/create" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.entrepreneur" access="hasRole('Entrepreneur')">
			<acme:menu-suboption code="master.menu.entrepreneur.investment-round.list-mine" action="/entrepreneur/investment-round/list-mine" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.entrepreneur.application.list-mine" action="/entrepreneur/application/list-mine" />
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.bookkeeper" access="hasRole('Bookkeeper')">
			<acme:menu-suboption code="master.menu.bookkeeper.investment-round.list-mine" action="/bookkeeper/investment-round/list-mine" />
			<acme:menu-separator />
			<acme:menu-suboption code="master.menu.bookkeeper.investment-round.list-not-mine" action="/bookkeeper/investment-round/list-not-mine" />
		</acme:menu-option>
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()" />
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()" />

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update" />
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create"
				access="!hasRole('Provider')" />
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update"
				access="hasRole('Provider')" />
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create"
				access="!hasRole('Consumer')" />
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update"
				access="hasRole('Consumer')" />
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()" />
	</acme:menu-right>
</acme:menu-bar>

