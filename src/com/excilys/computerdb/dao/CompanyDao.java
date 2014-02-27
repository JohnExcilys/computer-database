package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.excilys.computerdb.model.Company;

public class CompanyDao {
	private static CompanyDao _instance = null;
	static Logger log = Logger.getLogger(CompanyDao.class.getName());
	
	// Initialisation du Singleton
	private CompanyDao() {

	}

	// Récupération de l'instance du Singleton
	synchronized public static CompanyDao getInstance() {
		if (_instance == null) {
			_instance = new CompanyDao();
		}
		return _instance;
	}

	public ArrayList<Company> getCompanies() throws NamingException,
			SQLException {
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		
		Connection cn = DBConnection.getConnection();
		
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("SELECT id, name FROM company");

		ArrayList<Company> companyList = new ArrayList<Company>();

		while (rs.next()) {
			companyList.add(new Company(rs.getInt("id"), rs.getString("name")));
		}

		rs.close();
		st.close();
		cn.close();

		return companyList;
	}
	
}
