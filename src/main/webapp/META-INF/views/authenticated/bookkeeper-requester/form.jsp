
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>

	<acme:form-textbox code="authenticated.bookkeeper-requester.form.label.firm-name" path="firmName" />
	<acme:form-textarea code="authenticated.bookkeeper-requester.form.label.responsibility-statement" path="responsibilityStatement" />

	<acme:form-submit test="${command == 'create'}" code="authenticated.bookkeeper-requester.form.button.create"
		action="/authenticated/bookkeeper-requester/create" />
		
	<acme:form-return code="authenticated.forum.form.button.return" />

</acme:form>