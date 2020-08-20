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
	<acme:form-textbox code="administrator.tool-record.form.label.title" path="title"/>
	<acme:form-textbox code="administrator.tool-record.form.label.activitySector" path="activitySector" />
	<acme:form-textbox code="administrator.tool-record.form.label.inventorsName" path="inventorsName" />	
	<acme:form-textarea code="administrator.tool-record.form.label.description" path="description" />
	<acme:form-url code="administrator.tool-record.form.label.website" path="website" />
	<acme:form-textbox code="administrator.tool-record.form.label.email" path="email" />	
	<acme:form-textbox code="administrator.tool-record.form.label.indication" path="indication" />
	<acme:form-textbox code="administrator.tool-record.form.label.starsRate" path="starsRate" />

	<acme:form-submit test="${command == 'show'}"
	code="administrator.tool-record.form.button.update" 
	action="/administrator/tool-record/update"/>
	<acme:form-submit test="${command == 'show'}"
	code="administrator.tool-record.form.button.delete" 
	action="/administrator/tool-record/delete"/>
	<acme:form-submit  test="${command == 'create'}"
	code="administrator.tool-record.form.button.create" 
	action="/administrator/tool-record/create"/>
	<acme:form-submit  test="${command == 'update'}"
	code="administrator.tool-record.form.button.update" 
	action="/administrator/tool-record/update"/>
	<acme:form-submit  test="${command == 'delete'}"
	code="administrator.tool-record.form.button.delete" 
	action="/administrator/tool-record/delete"/>
	<acme:form-return
	code="administrator.tool-record.form.button.return"/>
</acme:form>
