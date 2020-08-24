
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>

	<%-- Acceso show a publicado (readonly) --%>
	<jstl:if test="${command == 'show' && status == 'PUBLISHED'}">
		<acme:form-textbox code="bookkeeper.accounting-record.form.label.title" path="title" readonly="true" />
		<acme:form-textbox code="bookkeeper.accounting-record.form.label.status" path="status" readonly="true" />
		<acme:form-textarea code="bookkeeper.accounting-record.form.label.body" path="body" readonly="true" />
	</jstl:if>

	<%-- Acceso create, show a borrador (modificable) o update--%>
	<jstl:if test="${command == 'create' || command == 'update' || command == 'show' && status == 'DRAFT'}">
		<acme:form-textbox code="bookkeeper.accounting-record.form.label.title" path="title" />
		<acme:form-select code="bookkeeper.accounting-record.form.label.status" path="status">
			<acme:form-option code="bookkeeper.accounting-record.form.label.status.draft" value="DRAFT" />
			<acme:form-option code="bookkeeper.accounting-record.form.label.status.published" value="PUBLISHED" />
		</acme:form-select>
		<acme:form-textarea code="bookkeeper.accounting-record.form.label.body" path="body" />
	</jstl:if>


	<%-- Fecha accediendo desde show o update (readonly) --%>
	<jstl:if test="${command == 'show' || command == 'update'}">
		<acme:form-textbox code="bookkeeper.accounting-record.form.label.creationMoment" path="creationMoment" readonly="true" />
		<acme:form-textbox code="bookkeeper.accounting-record.form.label.bookkeeper" path="bookkeeper.userAccount.identity.fullName"
			readonly="true" />
		<acme:form-textbox code="bookkeeper.accounting-record.form.label.investmentRound" path="investmentRound.ticker" readonly="true" />

	</jstl:if>


	<acme:form-submit test="${command == 'create'}" code="bookkeeper.accounting-record.form.button.create"
		action="/bookkeeper/accounting-record/create?irId=${irId}" />
	<acme:form-submit test="${command == 'show' && status == 'DRAFT' || command == 'update'}"
		code="bookkeeper.accounting-record.form.button.update" action="/bookkeeper/accounting-record/update?id=${id}" />
	<acme:form-return code="bookkeeper.accounting-record.form.button.return" />


</acme:form>
