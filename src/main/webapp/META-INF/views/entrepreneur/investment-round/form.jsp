<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	
	<jstl:if test="${command == 'create'}">
	<acme:form-textbox code="entrepreneur.investment-round.form.label.ticker" path="ticker" placeholder="SSS-YY-123456"/>
	</jstl:if>
	<jstl:if test="${command != 'create'}">
	<acme:form-textbox code="entrepreneur.investment-round.form.label.ticker" path="ticker" placeholder="SSS-YY-123456" readonly="true"/>
		<acme:form-moment 
		 code="entrepreneur.investment-round.form.label.creation-date"
		 path="creationDate"
		 readonly="true"/>
	</jstl:if>
	
	<jstl:if test="${not finalMode}">
	<acme:form-textbox code="entrepreneur.investment-round.form.label.kind-of-round" 
		placeholder="SEED, ANGEL, SERIES-A, SERIES-B, SERIES-C, BRIDGE" path="kindOfRound"/>
	<acme:form-textbox code="entrepreneur.investment-round.form.label.title" path="title"/>
	<acme:form-textarea code="entrepreneur.investment-round.form.label.description" path="description"/>
	<acme:form-money code="entrepreneur.investment-round.form.label.amount" path="amount"/>
	<acme:form-url code="entrepreneur.investment-round.form.label.optional-link" path="optionalLink"/>	
	</jstl:if>
	
	<jstl:if test="${finalMode}">
	<acme:form-textbox code="entrepreneur.investment-round.form.label.kind-of-round" 
		placeholder="SEED, ANGEL, SERIES-A, SERIES-B, SERIES-C, BRIDGE" path="kindOfRound" readonly="true"/>
	<acme:form-textbox code="entrepreneur.investment-round.form.label.title" path="title" readonly="true"/>
	<acme:form-textarea code="entrepreneur.investment-round.form.label.description" path="description" readonly="true"/>
	<acme:form-money code="entrepreneur.investment-round.form.label.amount" path="amount" readonly="true"/>
	<acme:form-url code="entrepreneur.investment-round.form.label.optional-link" path="optionalLink" readonly="true"/>
	</jstl:if>
	
	<acme:form-submit test="${command == 'show' and not finalMode}" method="get" code="entrepreneur.investment-round.form.button.activity.create"
		action="/entrepreneur/activity/create?invId=${ivID}" />
	<acme:form-submit test="${command == 'show'}" method="get" code="entrepreneur.investment-round.form.button.work-programme"
		action="/entrepreneur/activity/list?id=${ivID}" />
	<acme:form-submit test="${command == 'show'}" method="get" code="entrepreneur.investment-round.form.button.accounting-records"
		action="/entrepreneur/accounting-record/list?id=${ivID}" />
	<acme:form-submit test="${command == 'show' and not finalMode}" code="entrepreneur.investment-round.form.button.update"
		action="/entrepreneur/investment-round/update" />
	<acme:form-submit test="${command == 'show' and not haveApplications}" code="entrepreneur.investment-round.form.button.delete"
		action="/entrepreneur/investment-round/delete" />
	<acme:form-submit test="${command == 'create'}" code="entrepreneur.investment-round.form.button.create"
		action="/entrepreneur/investment-round/create" />
	<acme:form-submit test="${command == 'update'}" code="entrepreneur.investment-round.form.button.update"
		action="/entrepreneur/investment-round/update" />	
	<acme:form-submit test="${command == 'delete'}" code="entrepreneur.investment-round.form.button.delete"
		action="/entrepreneur/investment-round/delete" />

	<acme:form-return code="entrepreneur.investment-round.form.button.return"/>

</acme:form>