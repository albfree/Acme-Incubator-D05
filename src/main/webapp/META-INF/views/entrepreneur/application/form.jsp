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

	<acme:form-textbox code="entrepreneur.application.form.label.investment-ticker" path="investment.ticker" placeholder="SSS-YY-123456" readonly="true" />
	<acme:form-textbox code="entrepreneur.application.form.label.ticker" path="ticker" placeholder="SSS-YY-123456" readonly="true" />
	<acme:form-moment code="entrepreneur.application.form.label.creation-date" path="creationDate" readonly="true" />
	<acme:form-textarea code="entrepreneur.application.form.label.statement" path="statement" readonly="true" />
	<acme:form-money code="entrepreneur.application.form.label.investment-offer" path="investmentOffer" readonly="true" />

	<jstl:if test="${status != 'PENDING' and command == 'show'}">
		<acme:form-textbox code="entrepreneur.application.form.label.status" path="status" readonly="true" />
		<acme:form-textarea code="entrepreneur.application.form.label.rejectReason" path="rejectReason" readonly="true" />
	</jstl:if>

	<jstl:if test="${status == 'PENDING' or command == 'update'}">
		<acme:form-select code="entrepreneur.application.form.label.status" path="status" readonly="false">
			<acme:form-option code="entrepreneur.application.form.label.status.pending" selected="${status == 'PENDING'}" value="PENDING" />
			<acme:form-option code="entrepreneur.application.form.label.status.accept" selected="${status == 'ACCEPTED'}" value="ACCEPTED" />
			<acme:form-option code="entrepreneur.application.form.label.status.reject" selected="${status == 'REJECTED'}" value="REJECTED" />
		</acme:form-select>
	</jstl:if>

	<jstl:if test="${status == 'PENDING' or command == 'update'}">
		<acme:form-textarea code="entrepreneur.application.form.label.rejectReason" path="rejectReason" />
	</jstl:if>

	<acme:form-submit test="${status == 'PENDING' or command == 'update'}" code="entrepreneur.application.form.button.update"
		action="/entrepreneur/application/update" />

	<acme:form-return code="entrepreneur.application.form.button.return" />

</acme:form>