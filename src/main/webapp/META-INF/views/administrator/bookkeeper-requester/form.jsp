

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="administrator.bookkeeper-requester.form.label.firm-name" path="firmName" readonly="true" />
	<acme:form-textbox code="administrator.bookkeeper-requester.form.label.responsibility-statement" path="responsibilityStatement"
		readonly="true" />

	<acme:form-submit test="${command == 'show'}" code="administrator.bookkeeper-requester.form.button.create"
		action="/administrator/bookkeeper-requester/create" />

	<acme:form-submit test="${command == 'show'}" code="administrator.bookkeeper-requester.form.button.delete"
		action="/administrator/bookkeeper-requester/delete" />
		
	<acme:form-submit test="${command == 'create'}" code="administrator.bookkeeper-requester.form.button.create"
		action="/administrator/bookkeeper-requester/create" />
		
	<acme:form-submit test="${command == 'delete'}" code="administrator.bookkeeper-requester.form.button.delete"
		action="/administrator/bookkeeper-requester/delete" />

	<acme:form-return code="administrator.bookkeeper-requester.form.button.return" />
</acme:form>
