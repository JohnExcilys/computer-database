package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;

public class ComputerDao {
	private static ComputerDao _instance = null;
	
	public ComputerDao() {
		
	}
	
	synchronized public static ComputerDao getInstance(){
		if (_instance == null) {
			_instance = new ComputerDao();
		}
		return _instance;
	}
	
	public ArrayList<Computer> getComputers() throws NamingException, SQLException {
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();

		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer");
		
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		
		while (rs.next()) {
			computerList.add(new Computer(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("introduced"), rs.getTimestamp("discontinued"), rs.getInt("company_id")));
		}
		
		rs.close();
		st.close();
		cn.close();
		
		return computerList;
	}
}
