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

	<jstl:if test="${command == 'create'}">
		<acme:form-textbox code="authenticated.message.form.label.title" path="title" />
		<acme:form-textbox code="authenticated.message.form.label.tags" placeholder="tag1, tag2, tag3" path="tags" />
		<acme:form-textarea code="authenticated.message.form.label.body" path="body" />
		<acme:form-checkbox code="authenticated.message.form.label.confirmation" path="confirmation" />
	</jstl:if>

	<jstl:if test="${command == 'show'}">
		<acme:form-textbox code="authenticated.message.form.label.title" path="title" readonly="true" />
		<acme:form-moment code="authenticated.message.form.label.creation-moment" path="creationMoment" readonly="true" />
		<acme:form-textbox code="authenticated.message.form.label.tags" path="tags" readonly="true" />
		<acme:form-textarea code="authenticated.message.form.label.body" path="body" readonly="true" />
		<acme:form-textbox code="authenticated.message.form.label.user" path="user.identity.fullName" readonly="true" />
	</jstl:if>

	<acme:form-submit test="${command == 'create'}" code="authenticated.message.form.button.create"
		action="/authenticated/message/create?forumId=${forumId}" />
		
	<input id="forumId" name="forumId" value="${forumId}" type="hidden" />

	<acme:form-return code="authenticated.message.form.button.return" />

</acme:form>