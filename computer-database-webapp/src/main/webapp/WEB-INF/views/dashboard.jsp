<jsp:include page="include/header.jsp" />
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="pagination" prefix="page"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<section id="main">
	<h1 id="homeTitle">${number_of_result}
		<spring:message code="label.computerFound" />
	</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search"
				value="${!empty param.search ? param.search : ''}"
				placeholder="<spring:message code="label.filterDefault"/>">
			<input type="submit" id="searchsubmit"
				value="<spring:message code="label.filter"/>" class="btn primary">
		</form>
		<a class="btn success" id="add" href="./computer/add"><spring:message code="label.add"/></a>
	</div>

	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<th><a
					href="./dashboard?order=${ order == 'ORDER_BY_NAME_ASC' ? 'orderByNameDesc' : 'orderByNameAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}${!empty param.lang ? '&lang='.concat(param.lang) : ''}"><spring:message
							code="label.computerName" /></a>&nbsp;${ order == 'ORDER_BY_NAME_ASC' ? 'ASC' : ''}${ order == 'ORDER_BY_NAME_DESC' ? 'DESC' : ''}</th>
				<th><a
					href="./dashboard?order=${ order == 'ORDER_BY_INTRODUCED_DATE_ASC' ? 'orderByIntroducedDateDesc' : 'orderByIntroducedDateAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}${!empty param.lang ? '&lang='.concat(param.lang) : ''}"><spring:message
							code="label.introduced" /></a>&nbsp;${ order == 'ORDER_BY_INTRODUCED_DATE_ASC' ? 'ASC' : ''}${order == 'ORDER_BY_INTRODUCED_DATE_DESC' ? 'DESC' : ''}</th>
				<!-- Table header for Discontinued Date -->
				<th><a
					href="./dashboard?order=${ order == 'ORDER_BY_DISCONTINUED_DATE_ASC' ? 'orderByDiscontinuedDateDesc' : 'orderByDiscontinuedDateAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}${!empty param.lang ? '&lang='.concat(param.lang) : ''}"><spring:message
							code="label.discontinued" /> </a>&nbsp;${ order == 'ORDER_BY_DISCONTINUED_DATE_ASC' ? 'ASC' : ''}${ order == 'ORDER_BY_DISCONTINUED_DATE_DESC' ? 'DESC' : ''}</th>
				<!-- Table header for Company -->
				<th><a
					href="./dashboard?order=${ order == 'ORDER_BY_COMPANY_NAME_ASC' ? 'orderByCompanyNameDesc' : 'orderByCompanyNameAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}${!empty param.lang ? '&lang='.concat(param.lang) : ''}"><spring:message
							code="label.company" /></a>&nbsp;${ order == 'ORDER_BY_COMPANY_NAME_ASC' ? 'ASC' : ''}${ order == 'ORDER_BY_COMPANY_NAME_DESC' ? 'DESC' : ''}</th>
				<!--  Header pour suppression -->
				<th><spring:message code="label.delete" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${computers}">
				<tr>
					<td><a href="computer/update?update=${computer.id}">${computer.name}</a></td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.companyName}</td>
					<td><a class="btn danger"
						href="./computer/delete?delete=${computer.id}"><spring:message
								code="label.delete" /></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<page:pagination lastPage="${last_page}"
		currentPage="${current_page}"
		queryParameters="${query_parameters}" />
</section>

<jsp:include page="include/footer.jsp" />
