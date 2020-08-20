

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="bookkeeper.investment-round.list.label.ticker" path="ticker" width="30%" />
	<acme:list-column code="bookkeeper.investment-round.list.label.title" path="title" width="30%" />
	<acme:list-column code="bookkeeper.investment-round.list.label.amount" path="amount" width="30%" />
</acme:list>


