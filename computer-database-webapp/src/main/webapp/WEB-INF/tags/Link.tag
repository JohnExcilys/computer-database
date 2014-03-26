<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="url" type="java.lang.String" rtexprvalue="true"
	required="true"%>
<%@ attribute name="search" type="java.lang.String" rtexprvalue="true"
	required="false"%>
<%@ attribute name="order" type="java.lang.String" rtexprvalue="true"
	required="false"%>
<%@ attribute name="dir" type="java.lang.String" rtexprvalue="true"
	required="false"%>
<%@ attribute name="curPage" type="java.lang.Integer" rtexprvalue="true"
	required="false"%>
<%@ attribute name="lang" type="java.lang.String" rtexprvalue="true"
	required="false"%>

<c:url value="${url}" var="generateUrl">
	<c:if test="${not empty search}">
		<c:param name="search" value="${search}" />
	</c:if>
	<c:if test="${not empty order}">
		<c:param name="page.sort" value="${order}" />
	</c:if>
	<c:if test="${not empty dir}">
		<c:param name="page.sort.dir" value="${dir}" />
	</c:if>
	<c:if test="${not empty curPage}">
		<c:param name="page.page" value="${curPage}" />
	</c:if>
	<c:if test="${not empty lang}">
		<c:param name="lang" value="${lang}" />
	</c:if>
</c:url>

<a href="${generateUrl}"><jsp:doBody/></a>