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

import org.apache.log4j.Logger;

import com.excilys.computerdb.dao.CompanyDao;
import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.model.Computer;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Logger log = Logger.getLogger(AddComputerServlet.class.getName());

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
			rd = getServletContext().getRequestDispatcher("/DashboardServlet?page=1");
			try {
				cdao.deleteComputer(Integer.parseInt(request.getParameter("delete")));
			} catch (NumberFormatException | NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(request.getParameter("search") != null){
			ComputerDao cdao = ComputerDao.getInstance();
			rd = getServletContext().getRequestDispatcher("/dashboard.jsp");
			try {
				request.setAttribute("computers", cdao.getComputersByNameOrCompany(request.getParameter("search")));
			} catch (NumberFormatException | NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(request.getParameter("field") != null){
			
			ComputerDao cdao = ComputerDao.getInstance();
			rd = getServletContext().getRequestDispatcher("/dashboard.jsp");
			String sens = "DESC";
			
			if(request.getParameter("sens") != null){
				sens = request.getParameter("sens");
			}
			
			if (sens.equals("ASC")){
				sens = "DESC";
			}else if (sens.equals("DESC")){
				sens = "ASC";
			}
			
			request.setAttribute("sens", sens);
			try {
				request.setAttribute("computers", cdao.getComputersByTag(request.getParameter("field"), sens));
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
		Date introducedDate = null;
		Date discontinuedDate = null;

		try {
			if(isDateValid(request.getParameter("introducedDate")) && isDateValid(request.getParameter("discontinuedDate"))){
			introducedDate = df.parse(request.getParameter("introducedDate"));
			discontinuedDate = df.parse(request.getParameter("discontinuedDate"));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int companyId = Integer.parseInt(request.getParameter("company"));
		
		ComputerDao cDao = ComputerDao.getInstance();
		// Ajout d'un ordinateur
		if (request.getParameter("update") == null){
			//Ajout d'un ordinateur
			try {
				if(name != "" && isDateValid(request.getParameter("introducedDate")) && isDateValid(request.getParameter("discontinuedDate")) && companyId != 0){
					cDao.insertComputer(new Computer(name, introducedDate, discontinuedDate, companyId));
					request.setAttribute("formState", "Add");
					request.setAttribute("ajout", "L'ordinateur a été ajouté avec succés.");
				}
				else{
					request.setAttribute("formState", "Add");
					request.setAttribute("ajout", "L'ordinateur n'a pas été ajouté. Certains champs doivent être incorrects.");
					log.info("Ordinateur non ajouté - Certains champs doivent être incorrects.");
				}
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if (request.getParameter("update") != null){
			//Modification d'un ordinateur
			if(name != "" && isDateValid(request.getParameter("introducedDate")) && isDateValid(request.getParameter("discontinuedDate")) && companyId != 0){
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
			}else{
				request.setAttribute("formState", "Update");
				request.setAttribute("ajout", "L'ordinateur n'a pas été modifié. Certains champs doivent être incorrects.");
				log.info("Ordinateur non modifié - Certains champs doivent être incorrects.");
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

		public static boolean isDateValid(String date){
	        try {
	        	if(date != ""){
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
