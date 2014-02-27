package com.excilys.computerdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.computerdb.dao.ComputerDao;
import com.excilys.computerdb.model.Computer;

/**
 * Servlet implementation class DashboardServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/dashboard.jsp");
		
		ComputerDao cs = ComputerDao.getInstance();
		try {
			request.setAttribute("computers", cs.getComputers());
			request.setAttribute("sens", "ASC");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(request.getParameter("page") == null){
			rd = getServletContext().getRequestDispatcher("/DashboardServlet?page=1");
		}
		
		if(request.getParameter("page") != null){
			ComputerDao cdao = ComputerDao.getInstance();
			int count = 0;
			try {
				count = cdao.countComputers();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(Integer.parseInt(request.getParameter("page")) <= 0){
				request.setAttribute("page", 1);
			}else if (Integer.parseInt(request.getParameter("page")) > (count/10)){
				request.setAttribute("page", (count / 10));
			}
			
			ArrayList<Computer> computerList = new ArrayList<Computer>();
			try {
				 computerList = cdao.getComputersPaginated(10 , Integer.parseInt(request.getParameter("page")));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.setAttribute("computers", computerList);
		}
		
				
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
