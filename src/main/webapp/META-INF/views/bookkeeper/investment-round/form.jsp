
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">

	<acme:form-textbox code="bookkeeper.investment-round.form.label.ticker" path="ticker" />
	<acme:form-moment code="bookkeeper.investment-round.form.label.creation-date" path="creationDate" />
	<acme:form-textbox code="bookkeeper.investment-round.form.label.kind-of-round" path="kindOfRound" />
	<acme:form-textbox code="bookkeeper.investment-round.form.label.title" path="title" />
	<acme:form-textarea code="bookkeeper.investment-round.form.label.description" path="description" />
	<acme:form-money code="bookkeeper.investment-round.form.label.amount" path="amount" />
	<acme:form-url code="bookkeeper.investment-round.form.label.optional-link" path="optionalLink" />

	<acme:form-submit method="get" code="bookkeeper.investment-round.form.button.work-programme"
		action="/bookkeeper/activity/list?id=${ivID}" />
	<acme:form-submit method="get" code="bookkeeper.investment-round.form.button.accounting-records"
		action="/bookkeeper/accounting-record/list?id=${ivID}" />

	<acme:form-return code="bookkeeper.investment-round.form.button.return" />

</acme:form>