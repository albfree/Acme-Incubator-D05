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

	<acme:form-url code="administrator.notice.form.label.header-picture" path="headerPicture" />
	<acme:form-textbox code="administrator.notice.form.label.title" path="title" />
	<jstl:if test="${command != 'create'}">
		<acme:form-moment
		 code="administrator.notice.form.label.creation-date"
		 path="creationDate"
		 readonly="true" />
	</jstl:if>
	<acme:form-moment code="administrator.notice.form.label.deadline" path="deadline" />
	<acme:form-textarea code="administrator.notice.form.label.body" path="body" />
	<acme:form-url code="administrator.notice.form.label.first-optional-link" path="firstOptionalLink" />
	<acme:form-url code="administrator.notice.form.label.second-optional-link" path="secondOptionalLink" />

	<jstl:if test="${command == 'create'}">
	<acme:form-checkbox code="administrator.notice.form.label.accept" path="accept" />
	</jstl:if>

	<acme:form-submit test="${command == 'create'}" code="administrator.notice.form.button.create"
		action="/administrator/notice/create" />
	<acme:form-return code="administrator.notice.form.button.return" />

</acme:form>