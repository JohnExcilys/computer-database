<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<jsp:include page="include/header.jsp" />

<section id="main">
	<spring:message code="${ajout}" text=""/>
	<h1>
		<spring:message code="${formState=='Add' ? 'label.cAddTitle' : 'label.cUpdateTitle'}"/>
	</h1>
	<form:form
		action="${action}"
		method="POST" commandName="cDTO">
		<form:hidden path="id" />
		<fieldset>
			<div class="clearfix">
				<label for="name"><spring:message code="label.cName"/></label>
				<div class="input">
					<form:input path="name" />
					<span class="help-inline"><spring:message code="label.cRequired"/></span>
					<form:errors path="name" cssClass="errorMessage"/>
				</div>
			</div>
			<div class="clearfix">
				<label for="introduced"><spring:message code="label.cIntroduced"/></label>
				<div class="input">
					<form:input type="date" pattern="yyyy-MM-dd" path="introduced" />
					<span class="help-inline">YYYY-MM-DD</span>
					<form:errors path="introduced" />
				</div>
			</div>
			<div class="clearfix">
				<label for="discontinued"><spring:message code="label.cDiscontinued"/></label>
				<div class="input">
					<form:input type="date" pattern="yyyy-MM-dd" path="discontinued" />
					<span class="help-inline">YYYY-MM-DD</span>
					<form:errors path="discontinued" />
				</div>
			</div>
			<div class="clearfix">
				<label for="company"><spring:message code="label.cCompany"/></label>
				<div class="input">
					<form:select path="companyId">
						<form:option value="0">--</form:option>
						<form:options items="${companies}" itemValue="id" itemLabel="name" />
					</form:select>
					<form:errors path="companyId" cssClass="errorMessage"/>
				</div>
			</div>
		</fieldset>
		<div class="actions">
			<input type="submit"
				value="<spring:message code="${formState=='Add' ? 'label.cAdd' : 'label.cUpdate'}"/>"
				class="btn primary"> <a href="dashboard" class="btn"><spring:message code="label.cCancel"/></a>
		</div>
	</form:form>
</section>

<jsp:include page="include/footer.jsp" />