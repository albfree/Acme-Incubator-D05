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

	<acme:form-textbox code="bookkeeper.activity.form.label.title" path="title"/>
	<acme:form-moment code="bookkeeper.activity.form.label.start-date-time" path="startDateTime"/>
	<acme:form-moment code="bookkeeper.activity.form.label.end-date-time" path="endDateTime"/>
	<acme:form-money code="bookkeeper.activity.form.label.budget" path="budget"/>

	<acme:form-return code="bookkeeper.activity.form.button.return"/>

</acme:form>