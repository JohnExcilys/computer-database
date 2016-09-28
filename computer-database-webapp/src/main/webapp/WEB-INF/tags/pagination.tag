<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="lastPage" required="true" type="java.lang.Integer"
	rtexprvalue="true"%>
<%@ attribute name="url" type="java.lang.String" rtexprvalue="true"
	required="true"%>
<%@ attribute name="search" type="java.lang.String" rtexprvalue="true"
	required="false"%>
<%@ attribute name="order" type="java.lang.String" rtexprvalue="true"
	required="false"%>
<%@ attribute name="dir" type="java.lang.String" rtexprvalue="true"
	required="false"%>
<%@ attribute name="curPage" type="java.lang.Integer" rtexprvalue="true"
	required="true"%>
<%@ taglib uri="tools" prefix="t"%>

<c:set var="currentPage" value="${curPage + 1}" />
<c:if test="${currentPage > 4}">
	<t:link url="${url}" search="${search}" order="${order}" dir="${dir}"
		curPage="${currentPage - 4}">&lt;&lt;</t:link>
</c:if>

<c:if test="${currentPage > 3}">
	<t:link url="${url}" search="${search}" order="${order}" dir="${dir}"
		curPage="1">1</t:link>
</c:if>

<c:if test="${currentPage > 4}">
	...
</c:if>

<c:if test="${currentPage > 2}">
	<t:link url="${url}" search="${search}" order="${order}" dir="${dir}"
		curPage="${currentPage - 2}">${currentPage - 2}</t:link>
</c:if>

<c:if test="${currentPage > 1}">
	<t:link url="${url}" search="${search}" order="${order}" dir="${dir}"
		curPage="${currentPage - 1}">${currentPage - 1}</t:link>
</c:if>

${currentPage}

<c:if test="${currentPage + 1 <= lastPage}">
	<t:link url="${url}" search="${search}" order="${order}" dir="${dir}"
		curPage="${currentPage + 1}">${currentPage + 1}</t:link>
</c:if>

<c:if test="${currentPage + 2 <= lastPage}">
	<t:link url="${url}" search="${search}" order="${order}" dir="${dir}"
		curPage="${currentPage + 2}">${currentPage + 2}</t:link>
</c:if>

<c:if test="${currentPage + 4 <= lastPage}">
	...
</c:if>

<c:if test="${currentPage + 3 <= lastPage}">
	<t:link url="${url}" search="${search}" order="${order}" dir="${dir}"
		curPage="${lastPage}">${lastPage}</t:link>
</c:if>

<c:if test="${currentPage + 4 <= lastPage}">
	<t:link url="${url}" search="${search}" order="${order}" dir="${dir}"
		curPage="${currentPage + 4}">&gt;&gt;</t:link>
</c:if>
