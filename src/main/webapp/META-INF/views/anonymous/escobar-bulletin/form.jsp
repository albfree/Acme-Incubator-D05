<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>


<acme:form>
	<acme:form-textbox code="anonymous.escobar-bulletin.form.label.author" path="author" />
	<acme:form-textbox code="anonymous.escobar-bulletin.form.label.country" path="country" />
	<acme:form-textbox code="anonymous.escobar-bulletin.form.label.link" path="link"/>
	<acme:form-textarea code="anonymous.escobar-bulletin.form.label.text" path="text" />

	<acme:form-submit code="anonymous.escobar-bulletin.form.button.create" action="/anonymous/escobar-bulletin/create/" />
	<acme:form-return code="anonymous.escobar-bulletin.form.button.return"/>
</acme:form> 