package com.excilys.computerdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdb.dao.DAOFactory;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ServiceComputer;

/**
 * Servlet implementation class ServletComputer
 */
@WebServlet("/addComputer")
public class ServletComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletComputer() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/addComputer.jsp");

		// Add
		try {
			request.setAttribute("companies", DAOFactory.getInstance()
					.getDAOCompany().getCompanies());
			request.setAttribute("action", "./addComputer");
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
		request.setAttribute("formState", "Add");

		// Update
		if (request.getParameter("update") != null) {

			Computer computer;
			try {
				computer = DAOFactory
						.getInstance()
						.getDAOComputer()
						.getComputer(
								Integer.parseInt(request.getParameter("update")));
				request.setAttribute("computer", computer);
				request.setAttribute("formState", "Update");
				request.setAttribute("action",
						"addComputer?update=" + computer.getId());
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Delete
		if (request.getParameter("delete") != null) {
			rd = getServletContext().getRequestDispatcher("/dashboard");
			try {
				DAOFactory
						.getInstance()
						.getDAOComputer()
						.deleteComputer(
								Integer.parseInt(request.getParameter("delete")));
			} catch (NumberFormatException | NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/addComputer.jsp");

		if (request.getParameter("update") == null) {
			if (!request.getParameter("name").equals("")
					&& isDateValid(request.getParameter("introducedDate"))
					&& isDateValid(request.getParameter("discontinuedDate"))
					&& Integer.parseInt(request.getParameter("company")) != 0) {
				try {
					// Add
					ServiceComputer.getInstance().saveComputer(
							0,
							request.getParameter("name"),
							request.getParameter("introducedDate"),
							request.getParameter("discontinuedDate"),
							new Company(Integer.parseInt(request
									.getParameter("company")), ""));
					request.setAttribute("formState", "Add");
					request.setAttribute("ajout",
							"L'ordinateur a été ajouté avec succés.");
					request.setAttribute("action", "./addComputer");

				} catch (NumberFormatException | SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				request.setAttribute("formState", "Add");
				request.setAttribute("ajout",
						"L'ordinateur n'a pas été ajouté. Certains champs doivent être incorrects.");
			}
		} else {
			// Update
			if (!request.getParameter("name").equals("")
					&& isDateValid(request.getParameter("introducedDate"))
					&& isDateValid(request.getParameter("discontinuedDate"))
					&& Integer.parseInt(request.getParameter("company")) != 0) {
				try {
					ServiceComputer.getInstance().saveComputer(
							Integer.parseInt(request.getParameter("update")),
							request.getParameter("name"),
							request.getParameter("introducedDate"),
							request.getParameter("discontinuedDate"),
							new Company(Integer.parseInt(request
									.getParameter("company")), ""));
				} catch (NumberFormatException | SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("formState", "Update");
				request.setAttribute("ajout",
						"L'ordinateur a été modifié avec succés.");
				request.setAttribute("action", "./addComputer");
			}
		}
		rd.forward(request, response);
	}

	public static boolean isDateValid(String date) {
		try {
			if (date != "") {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				df.parse(date);
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
		return false;
	}
}
