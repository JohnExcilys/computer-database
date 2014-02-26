package com.excilys.computerdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdb.dao.CompanyDao;
import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.model.Computer;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/addComputer.jsp");
		CompanyDao cs = CompanyDao.getInstance();
		//Initialisation des attributs request pour éviter les plantages en cas de non remplissage
		request.setAttribute("companies", "");
		request.setAttribute("ajout", "");
		request.setAttribute("computer", new Computer());
		request.setAttribute("formState", "" );
		request.setAttribute("action", "AddComputerServlet");
		
		try {
			request.setAttribute("companies", cs.getCompanies());
			request.setAttribute("formState", "Add");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(request.getParameter("update") != null){
			ComputerDao cd = ComputerDao.getInstance();
			try {
				Computer computer = cd.getComputer(Integer.parseInt(request.getParameter("update")));
				computer.setcompanyName(cd.getCompanyName(computer.getcompanyId()));
				request.setAttribute("computer", computer);
				request.setAttribute("formState", "Update");
				request.setAttribute("action", "AddComputerServlet?update="+computer.getid());
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(request.getParameter("delete") != null){
			ComputerDao cdao = ComputerDao.getInstance();
			rd = getServletContext().getRequestDispatcher("/DashboardServlet");
			try {
				cdao.deleteComputer(Integer.parseInt(request.getParameter("delete")));
			} catch (NumberFormatException | NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date introducedDate = new Date();
		Date discontinuedDate = new Date();
		try {
			introducedDate = df.parse(request.getParameter("introducedDate"));
			discontinuedDate = df.parse(request.getParameter("discontinuedDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int companyId = Integer.parseInt(request.getParameter("company"));
		
		// Ajout d'un ordinateur
		ComputerDao cDao = ComputerDao.getInstance();
		
		if (request.getParameter("update") == null){
			//Ajout d'un ordinateur
			try {
				cDao.insertComputer(new Computer(name, introducedDate, discontinuedDate, companyId));
				request.setAttribute("formState", "Add");
				request.setAttribute("ajout", "L'ordinateur a été ajouté avec succés.");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if (request.getParameter("update") != null){
			//Modification d'un ordinateur
			try {
				cDao.updateComputer(new Computer(Integer.parseInt(request.getParameter("update")), name, introducedDate, discontinuedDate, companyId));
				request.setAttribute("computer", new Computer(name, introducedDate, discontinuedDate, companyId));
				request.setAttribute("formState", "Update");
				request.setAttribute("ajout", "L'ordinateur a été modifié avec succés.");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Renvoie vers la page d'ajout.
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/addComputer.jsp");
		CompanyDao cs = CompanyDao.getInstance();
		try {
			request.setAttribute("companies", cs.getCompanies());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rd.forward(request, response);
	}

}
