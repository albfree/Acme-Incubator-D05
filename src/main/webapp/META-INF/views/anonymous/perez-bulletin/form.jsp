
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="anonymous.perez-bulletin.form.label.author" path="author"/>
	<acme:form-textarea code="anonymous.perez-bulletin.list.label.event" path="event"/>

	<acme:form-submit code="anonymous.perez-bulletin.form.label.create" action="/anonymous/perez-bulletin/create"/>
  	<acme:form-return code="anonymous.perez-bulletin.form.label.return"/>
</acme:form>
