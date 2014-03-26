<jsp:include page="include/header.jsp" />
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="tools" prefix="t"%>


<section id="main">
	<h1 id="homeTitle">${page.totalElements}
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
		<a class="btn success" id="add" href="./computer/add"><spring:message
				code="label.add" /></a>
	</div>

	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<th><t:link url="dashboard" curPage="${page.number + 1}"
						search="${search}" order="name"
						dir="${ order == 'name' && dir == 'ASC' ? 'desc' : 'asc'}">
						<spring:message code="label.computerName" text="Computer Name" />
					</t:link>&nbsp;${ order == 'name' && dir == 'ASC' ? 'ASC' : ''}${ order == 'name' && dir == 'DESC' ? 'DESC' : ''}</th>
				</th>
				<th><t:link url="dashboard" curPage="${page.number + 1}"
						search="${search}" order="introduced"
						dir="${ order == 'introduced' && dir == 'ASC' ? 'desc' : 'asc'}">
						<spring:message code="dashboard.introduced_date"
							text="Introduced Date" />
					</t:link>&nbsp;${ order == 'introduced' && dir == 'ASC' ? 'ASC' : ''}${ order == 'introduced' && dir == 'DESC' ? 'DESC' : ''}</th>
				<!-- Table header for Discontinued Date -->
				<th><t:link url="dashboard" curPage="${page.number + 1}"
						search="${search}" order="discontinued"
						dir="${ order == 'discontinued' && dir == 'ASC' ? 'desc' : 'asc'}">
						<spring:message code="dashboard.discontinued_date"
							text="Discontinued Date" />
					</t:link>&nbsp;${ order == 'discontinued' && dir == 'ASC' ? 'ASC' : ''}${ order == 'discontinued' && dir == 'DESC' ? 'DESC' : ''}</th>
				<!-- Table header for Company -->
				<th><t:link url="dashboard" curPage="${page.number + 1}"
						search="${search}" order="company.name"
						dir="${ order == 'company.name' && dir == 'ASC' ? 'desc' : 'asc'}">
						<spring:message code="dashboard.company" text="Company" />
					</t:link>&nbsp;${ order == 'company.name' && dir == 'ASC' ? 'ASC' : ''}${ order == 'company.name' && dir == 'DESC' ? 'DESC' : ''}
				</th>
				<!--  Header pour suppression -->
				<th><spring:message code="label.delete" /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${page.content}">
				<tr>
					<td><a href="computer/update?update=${computer.id}">${computer.name}</a></td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company.name}</td>
					<td><form class="delete_form" action="./dashboard"
							method="POST">
							<input type="hidden" name="id" value="${computer.id}" /> <input
								type="submit" class="btn danger"
								value="<spring:message code="label.delete" text="Delete" />" />
						</form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<t:pagination url="dashboard" lastPage="${page.totalPages}"
		curPage="${page.number}" search="${search}" order="${order}"
		dir="${dir}" />
</section>

<jsp:include page="include/footer.jsp" />
