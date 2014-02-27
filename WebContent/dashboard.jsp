<jsp:include page="include/header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/WEB-INF/pagination.tld" prefix="pagination" %>

<section id="main">
	<h1 id="homeTitle">${fn:length(requestScope['computers'])} Computers found</h1>
	<div id="actions">
		<form action="./AddComputerServlet" method="GET">
			<input type="search" id="searchbox" name="search" value=""
				placeholder="Search name"> <input type="submit"
				id="searchsubmit" value="Filter by name" class="btn primary">
		</form>
		<a class="btn success" id="add" href="AddComputerServlet">Add
			Computer</a>
	</div>

	<table class="computers zebra-striped">
		<thead>
			<tr>
				<!-- Variable declarations for passing labels as parameters -->
				<!-- Table header for Computer Name -->
				<th><a href="AddComputerServlet?field=name&sens=<c:out value="${requestScope['sens']}" default=""/>&page=1">Computer Name</a></th>
				<th><a href="AddComputerServlet?field=introduced&sens=<c:out value="${requestScope['sens']}" default=""/>&page=1">Introduced Date</a></th>
				<!-- Table header for Discontinued Date -->
				<th><a href="AddComputerServlet?field=discontinued&sens=<c:out value="${requestScope['sens']}" default=""/>&page=1">Discontinued Date</a></th>
				<!-- Table header for Company -->
				<th><a href="AddComputerServlet?field=cname&sens=<c:out value="${requestScope['sens']}" default=""/>&page=1">Company</a></th>
				<!--  Header pour suppression -->
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="computer" items="${requestScope['computers']}" >
		<tr>
		<td><a href="AddComputerServlet?update=${computer.id}">${computer.name}</a></td>
		<td>${computer.introduced}</td>
		<td>${computer.discontinued}</td>
		<td>${computer.companyName}</td>
		<td><a class="btn danger" href="AddComputerServlet?delete=${computer.id}">Delete</a></td>
		</tr>
		</c:forEach>
		</tbody>
	</table>
	<pagination:pagination/>
</section>

<jsp:include page="include/footer.jsp" />
