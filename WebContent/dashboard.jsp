<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="pagination" prefix="page"%>

<section id="main">
	<h1 id="homeTitle">${fn:length(requestScope['computers'])}
		Computers found</h1>
	<div id="actions">
		<form action="" method="GET">
			<input type="search" id="searchbox" name="search"
				value="${!empty param.search ? param.search : ''}"
				placeholder="Search name"> <input type="submit"
				id="searchsubmit" value="Filter by name" class="btn primary">
		</form>
		<a class="btn success" id="add" href="addComputer">Add Computer</a>
	</div>

	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<th><a
					href="./dashboard?order=${ requestScope['order'] == 'ORDER_BY_NAME_ASC' ? 'orderByNameDesc' : 'orderByNameAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}">Computer
						Name</a>&nbsp;${ requestScope['order'] == 'ORDER_BY_NAME_ASC' ? 'ASC' : ''}${ requestScope['order'] == 'ORDER_BY_NAME_DESC' ? 'DESC' : ''}</th>
				<th><a
					href="./dashboard?order=${ requestScope['order'] == 'ORDER_BY_INTRODUCED_DATE_ASC' ? 'orderByIntroducedDateDesc' : 'orderByIntroducedDateAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}">Introduced
						Date</a>&nbsp;${ requestScope['order'] == 'ORDER_BY_INTRODUCED_DATE_ASC' ? 'ASC' : ''}${ requestScope['order'] == 'ORDER_BY_INTRODUCED_DATE_DESC' ? 'DESC' : ''}</th>
				<!-- Table header for Discontinued Date -->
				<th><a
					href="./dashboard?order=${ requestScope['order'] == 'ORDER_BY_DISCONTINUED_DATE_ASC' ? 'orderByDiscontinuedDateDesc' : 'orderByDiscontinuedDateAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}">Discontinued
						Date</a>&nbsp;${ requestScope['order'] == 'ORDER_BY_DISCONTINUED_DATE_ASC' ? 'ASC' : ''}${ requestScope['order'] == 'ORDER_BY_DISCONTINUED_DATE_DESC' ? 'DESC' : ''}</th>
				<!-- Table header for Company -->
				<th><a
					href="./dashboard?order=${ requestScope['order'] == 'ORDER_BY_COMPANY_NAME_ASC' ? 'orderByCompanyNameDesc' : 'orderByCompanyNameAsc'}${!empty param.search ? '&search='.concat(param.search) : ''}">Company</a>&nbsp;${ requestScope['order'] == 'ORDER_BY_COMPANY_NAME_ASC' ? 'ASC' : ''}${ requestScope['order'] == 'ORDER_BY_COMPANY_NAME_DESC' ? 'DESC' : ''}</th>
				<!--  Header pour suppression -->
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="computer" items="${requestScope['computers']}">
				<tr>
					<td><a href="addComputer?update=${computer.id}">${computer.name}</a></td>
					<td>${computer.introduced}</td>
					<td>${computer.discontinued}</td>
					<td>${computer.company.name}</td>
					<td><a class="btn danger"
						href="./addComputer?delete=${computer.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<page:pagination lastPage="${requestScope['last_page']}"
		currentPage="${requestScope['current_page']}"
		queryParameters="${requestScope['query_parameters']}" />
</section>

<jsp:include page="include/footer.jsp" />
