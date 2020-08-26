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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<acme:form>

	<acme:form-textbox code="authenticated.forum.form.label.title" path="title" />

	<jstl:if test="${command == 'create' or imCreator}">

		<jstl:if test="${command == 'create'}">
			<strong> <acme:message code="authenticated.forum.form.label.participants" />
			</strong>
		</jstl:if>

		<jstl:if test="${command != 'create'}">
			<strong> <acme:message code="authenticated.forum.form.label.participants.update" />
			</strong>
		</jstl:if>

		<div style="overflow-y: scroll; width: 40%; height: 50%; max-height: 220px; border: 2px outset; border-radius: 5px;">
			<jstl:forEach items="${names}" var="name" varStatus="loop">
				<acme:form-checkbox code="${name}" path="${ids[loop.index]}" />
			</jstl:forEach>
		</div>
		<br />
	</jstl:if>

	<acme:form-submit test="${command == 'show'}" method="get" code="authenticated.forum.form.button.messages"
		action="/authenticated/message/list?id=${forumId}" />
	<acme:form-submit test="${command == 'show'}" method="get" code="authenticated.forum.form.button.message.create"
		action="/authenticated/message/create?forumId=${forumId}" />

	<acme:form-submit test="${imCreator and command == 'show' or command == 'update'}" code="authenticated.forum.form.button.update"
		action="/authenticated/forum/update" />
	<acme:form-submit test="${imCreator and command == 'show' or command == 'delete'}" code="authenticated.forum.form.button.delete"
		action="/authenticated/forum/delete" />

	<acme:form-submit test="${command == 'create'}" code="authenticated.forum.form.button.create" action="/authenticated/forum/create" />

	<acme:form-return code="authenticated.forum.form.button.return" />

</acme:form>