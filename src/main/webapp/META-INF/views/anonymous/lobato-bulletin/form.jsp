<%--
- list.jsp
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

	<acme:form-textbox code="bulletinLobato.scientist" path="scientist"/>
	<acme:form-textbox code="bulletinLobato.investigation" path="investigation"/>

	<acme:form-submit code="anonymous.create" action="/anonymous/lobato-bulletin/create"/>
	<acme:form-return code="anonymous.return"/>

</acme:form> 