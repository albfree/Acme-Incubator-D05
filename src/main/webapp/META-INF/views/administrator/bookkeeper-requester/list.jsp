
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="administrator.bookkeeper-requester.list.label.firm-name" path="firmName" width="40%"/>
	<acme:list-column code="administrator.bookkeeper-requester.form.label.responsibility-statement" path="responsibilityStatement" width="60%"/>
</acme:list>


