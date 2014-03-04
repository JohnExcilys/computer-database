package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.excilys.computerdb.model.Company;

public class DAOCompany {
	private static DAOCompany _instance = null;
	static Logger log = Logger.getLogger(DAOCompany.class.getName());

	// Initialisation du Singleton
	private DAOCompany() {

	}

	// Récupération de l'instance du Singleton
	synchronized public static DAOCompany getInstance() {
		if (_instance == null) {
			_instance = new DAOCompany();
		}
		return _instance;
	}

	public ArrayList<Company> getCompanies() throws NamingException,
			SQLException {

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
