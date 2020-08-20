

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list readonly="true">
	<acme:list-column code="anonymous.perez-bulletin.list.label.moment" path="moment" width="20%"/>
	<acme:list-column code="anonymous.perez-bulletin.list.label.author" path="author" width="30%"/>
	<acme:list-column code="anonymous.perez-bulletin.list.label.event" path="event" width="40%"/>		
</acme:list>


