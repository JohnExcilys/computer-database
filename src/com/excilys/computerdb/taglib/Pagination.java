package com.excilys.computerdb.taglib;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.excilys.computerdb.dao.ComputerDao;

public class Pagination extends TagSupport{

	private static final long serialVersionUID = 1L;
	private int nombreParPage = 10;
	private int pageDebut = 1;
	private int pageFin = 1;
	private int pageActuelle = 1;
	
	//Getter et Setter
	public int getNombreParPage() {
		return nombreParPage;
	}
	public void setNombreParPage(int nombreParPage) {
		this.nombreParPage = nombreParPage;
	}
	public int getPageDebut() {
		return pageDebut;
	}
	public void setPageDebut(int pageDebut) {
		this.pageDebut = pageDebut;
	}
	public int getPageFin() {
		return pageFin;
	}
	public void setPageFin(int pageFin) {
		this.pageFin = pageFin;
	}
	public int getPageActuelle() {
		return pageActuelle;
	}
	public void setPageActuelle(int pageActuelle) {
		this.pageActuelle = pageActuelle;
	}
	
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		ComputerDao cdao = ComputerDao.getInstance();
		int count = 0;
		int page = 1;
		ServletRequest sRequest = pageContext.getRequest();
		try {
			count = cdao.countComputers();
		} catch (SQLException | NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sRequest.getParameter("page") != null){
			// Sécurité contre les utilisateurs chiants ...
			page =  Integer.parseInt(sRequest.getParameter("page"));
			if(page <= 0){
				page = 1;
			}else if(page > (count/nombreParPage)){
				page = (count/nombreParPage);
			}
			
			if(count > 0){
				//Décompte des pages pour créer les liens
				//Avant page courrante
				int temp = 0;
				if(page < 5){
					//Si la page actuelle est en dessous de 5
					//Avant la page actuelle
					for(int i = 0; i < (page-1); i++){
						if(temp < 5){
							try {
								out.append("<a href=\"DashboardServlet?page="+(i+1)+"\">"+(i+1)+"</a> ");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							temp++;
						}
					}
					temp = 0;
					
					//Page actuelle
					try {
						out.append(" ... <a href=\"DashboardServlet?page="+page+"\">"+page+" </a>... ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Après la page actuelle
					for(int i = 0; i < 5; i++){
						if(temp < 5){
							try {
								out.append(" <a href=\"DashboardServlet?page="+(1+i+page)+"\">"+(1+i+page)+"</a>");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							temp++;
						}
					}
				}else if(page >= 5){
					//Si la page actuelle est au dessus de 5
					//Avant la page actuelle
					for(int i = 0; i < 5; i++){
							try {
								if(page == 5){
									out.append("<a href=\"DashboardServlet?page="+(((page-4)+(i+1))-1)+"\">"+(((page-4)+(i+1))-1)+"</a> ");
									if(i == 3){
										i++;
									}
								}else{
									out.append("<a href=\"DashboardServlet?page="+(((page-5)+(i+1))-1)+"\">"+(((page-5)+(i+1))-1)+"</a> ");
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					//Page actuelle
					try {
						out.append(" ... <a href=\"DashboardServlet?page="+page+"\">"+page+" </a>... ");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					//Après la page actuelle
					if (page < (count/nombreParPage - 5)){ 
						for(int i = 0; i < 5; i++){
								try {
									out.append(" <a href=\"DashboardServlet?page="+((i+page)+1)+"\">"+((i+page)+1)+"</a>");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
					}else{
						for(int i = 0; i < (count/nombreParPage - page); i++){
							try {
								out.append(" <a href=\"DashboardServlet?page="+((i+page)+1)+"\">"+((i+page)+1)+"</a>");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
			
			// On affiche le tout
			try {
				out.println();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return SKIP_BODY;
	}
	
	
	

}
