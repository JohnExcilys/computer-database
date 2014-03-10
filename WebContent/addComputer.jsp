<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="include/header.jsp" />

<section id="main">
	<c:out value="${requestScope['ajout']}" default="" />
	<h1>
		<c:out value="${requestScope['formState']}" default="" />
		Computer
	</h1>

	<form:form
		action="./addComputer${!empty computer ? '?update='.concat(computer.id) : ''}"
		method="POST"
		commandName="cDTO">
		<form:hidden path="id" />
		<fieldset>
			<div class="clearfix">
				<label for="name">Computer name:</label>
				<div class="input">
					<form:input path="name" />
					<span class="help-inline">Required</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="introduced">Introduced date:</label>
				<div class="input">
					<form:input type="date" pattern="yyyy-MM-dd" path="introduced" />
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued">Discontinued date:</label>
				<div class="input">
					<form:input type="date" pattern="yyyy-MM-dd" path="discontinued" />
					<span class="help-inline">YYYY-MM-DD</span>
				</div>
			</div>
			<div class="clearfix">
				<label for="company">Company Name:</label>
				<div class="input">
					<form:select path="companyId">
						<form:option value="0">--</form:option>
						<form:options items="${companies}"
							itemValue="id" itemLabel="name" />
					</form:select>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit" value="${!empty computer ? 'Update' : 'Add'}"
				class="btn primary"> or <a href="dashboard" class="btn">Cancel</a>
		</div>
	</form:form>
</section>

<jsp:include page="include/footer.jsp" />