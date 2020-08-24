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

<acme:form readonly="true">

	<acme:form-textbox code="investor.application.form.label.ticker" path="ticker"/>
	<acme:form-moment code="investor.application.form.label.creation-date" path="creationDate"/>
	<acme:form-textarea code="investor.application.form.label.statement" path="statement"/>
	<acme:form-money code="investor.application.form.label.investment-offer" path="investmentOffer"/>
	<acme:form-money code="investor.application.form.label.status" path="status"/>
	<jstl:if test="${status != 'PENDING'}">
		<acme:form-textarea  code="investor.application.form.label.rejectReason" path="rejectReason" readonly="true"/>
	</jstl:if>

	<acme:form-return code="investor.application.form.button.return"/>

</acme:form>