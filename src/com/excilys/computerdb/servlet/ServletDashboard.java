package com.excilys.computerdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdb.model.ComputerOrder;
import com.excilys.computerdb.service.ServiceComputer;

/**
 * Servlet implementation class ServletDashboard
 */
@WebServlet("/dashboard")
public class ServletDashboard extends OverHttpRequest {
	private static final long serialVersionUID = 1L;
	@Autowired
	ServiceComputer serviceComputer;

	
	public ServiceComputer getServiceComputer() {
		return serviceComputer;
	}

	public void setServiceComputer(ServiceComputer serviceComputer) {
		this.serviceComputer = serviceComputer;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletDashboard() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/dashboard.jsp");

		int page = 1;
		int numberOfPage = 1;
		if (request.getParameter("page") != null) {
			try {
				page = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {

			}
		}

		ComputerOrder order = getOrder(request);
		Map<String, String> queryParameters = new HashMap<>();
		if (order != null) {
			queryParameters.put("order", order.getUrlParameter());
		}
		try {
			int numberOfResult = 0;
			if (request.getParameter("search") == null) {
				numberOfResult = serviceComputer.count(null);
				numberOfPage = (numberOfResult / 10) + 1;
				if (page < 1 || page > numberOfPage) {
					page = 1;
				}
				request.setAttribute("computers", serviceComputer
						.findAllByCreteria(null, order, (page - 1) * 10, 10));
			} else {
				numberOfResult = serviceComputer.count(
						request.getParameter("search"));
				numberOfPage = (numberOfResult / 10) + 1;
				queryParameters.put("search", request.getParameter("search"));
				if (page < 1 || page > numberOfPage) {
					page = 1;
				}
				request.setAttribute(
						"computers",
						serviceComputer.findAllByCreteria(
								request.getParameter("search"), order,
								(page - 1) * 10, 10));
			}
			request.setAttribute("current_page", page);
			request.setAttribute("last_page", numberOfPage);
			request.setAttribute("number_of_result", numberOfResult);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("query_parameters", queryParameters);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	public ComputerOrder getOrder(HttpServletRequest request) {
		ComputerOrder order = null;
		if (request.getParameter("order") != null) {
			switch (request.getParameter("order")) {
			case "orderByNameAsc":
				order = ComputerOrder.ORDER_BY_NAME_ASC;
				break;
			case "orderByNameDesc":
				order = ComputerOrder.ORDER_BY_NAME_DESC;
				break;
			case "orderByIntroducedDateAsc":
				order = ComputerOrder.ORDER_BY_INTRODUCED_DATE_ASC;
				break;
			case "orderByIntroducedDateDesc":
				order = ComputerOrder.ORDER_BY_INTRODUCED_DATE_DESC;
				break;
			case "orderByDiscontinuedDateAsc":
				order = ComputerOrder.ORDER_BY_DISCONTINUED_DATE_ASC;
				break;
			case "orderByDiscontinuedDateDesc":
				order = ComputerOrder.ORDER_BY_DISCONTINUED_DATE_DESC;
				break;
			case "orderByCompanyNameAsc":
				order = ComputerOrder.ORDER_BY_COMPANY_NAME_ASC;
				break;
			case "orderByCompanyNameDesc":
				order = ComputerOrder.ORDER_BY_COMPANY_NAME_DESC;
				break;
			default:
				break;
			}
		}
		request.setAttribute("order", order);
		return order;
	}
}
