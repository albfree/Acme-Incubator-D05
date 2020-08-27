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

<acme:form>
	<acme:form-textbox code="administrator.technology-record.form.label.title" path="title" />
	<acme:form-textbox code="administrator.technology-record.form.label.activitySector" path="activitySector" />
	<acme:form-textbox code="administrator.technology-record.form.label.inventorsName" path="inventorsName" />	
	<acme:form-textarea code="administrator.technology-record.form.label.description" path="description" />
	<acme:form-url code="administrator.technology-record.form.label.website" path="website" />
	<acme:form-textbox code="administrator.technology-record.form.label.email" path="email" />	
	<acme:form-textbox code="administrator.technology-record.form.label.indication" placeholder="open-source, closed-source" path="indication" />
	<acme:form-textbox code="administrator.technology-record.form.label.starsRate" path="starsRate" />

	<acme:form-submit test="${command == 'show'}"
	code="administrator.technology-record.form.button.update" 
	action="/administrator/technology-record/update"/>
	<acme:form-submit test="${command == 'show'}"
	code="administrator.technology-record.form.button.delete" 
	action="/administrator/technology-record/delete"/>
	<acme:form-submit test="${command == 'create'}"
	code="administrator.technology-record.form.button.create" 
	action="/administrator/technology-record/create"/>
	<acme:form-submit test="${command == 'update'}"
	code="administrator.technology-record.form.button.update" 
	action="/administrator/technology-record/update"/>
	<acme:form-submit test="${command == 'delete'}"
	code="administrator.technology-record.form.button.delete" 
	action="/administrator/technology-record/delete"/>
	<acme:form-return
	code="administrator.tool-record.form.button.return"/>
	
</acme:form>
