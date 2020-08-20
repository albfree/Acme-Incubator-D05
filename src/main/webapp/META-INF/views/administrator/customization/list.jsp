
<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="administrator.customization.list.label.spam-words" path="spamWords" width="50%"/>
	<acme:list-column code="administrator.customization.list.label.threshold" path="threshold" width="10%"/>
	<acme:list-column code="administrator.customization.list.label.activity-sectors" path="activitySectors" width="40%"/>		
</acme:list>


