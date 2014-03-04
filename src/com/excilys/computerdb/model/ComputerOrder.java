package com.excilys.computerdb.model;

public enum ComputerOrder {
	ORDER_BY_NAME_ASC("computer.name ASC", "orderByNameAsc"), ORDER_BY_NAME_DESC(
			"computer.name DESC", "orderByNameDesc"), ORDER_BY_INTRODUCED_DATE_ASC(
			"computer.introduced ASC", "orderByIntroducedDateAsc"), ORDER_BY_INTRODUCED_DATE_DESC(
			"computer.introduced DESC", "orderByIntroducedDateDesc"), ORDER_BY_DISCONTINUED_DATE_ASC(
			"computer.discontinued ASC", "orderByDiscontinuedDateAsc"), ORDER_BY_DISCONTINUED_DATE_DESC(
			"computer.discontinued DESC", "orderByDiscontinuedDateDesc"), ORDER_BY_COMPANY_NAME_ASC(
			"company.name ASC", "orderByCompanyNameAsc"), ORDER_BY_COMPANY_NAME_DESC(
			"company.name DESC", "orderByCompanyNameDesc");

	private final String orderStatement;
	private final String urlParameter;

	private ComputerOrder(String orderStatement, String urlParameter) {
		this.orderStatement = orderStatement;
		this.urlParameter = urlParameter;
	}

	public String getOrderStatement() {
		return orderStatement;
	}

	public String getUrlParameter() {
		return urlParameter;
	}

}
