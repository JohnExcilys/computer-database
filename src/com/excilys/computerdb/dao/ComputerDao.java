package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

import java.sql.Statement;

public class ComputerDao {
	private static ComputerDao _instance = null;

	public ComputerDao() {

	}

	synchronized public static ComputerDao getInstance() {
		if (_instance == null) {
			_instance = new ComputerDao();
		}
		return _instance;
	}

	public ArrayList<Computer> getComputers() throws NamingException,
			SQLException {
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();

		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id ");

		ArrayList<Computer> computerList = new ArrayList<Computer>();
		while (rs.next()) {
			computerList.add(new Computer(rs.getInt("id"),rs.getString("name"), rs.getDate("introduced"), rs.getDate("discontinued"), rs.getInt("company_id"), rs.getString("cname")));
		}

		rs.close();
		st.close();
		cn.close();
		
		return computerList;
	}
	
	public void insertComputer(Computer computer) throws NamingException, SQLException{
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();

		PreparedStatement st = cn.prepareStatement("INSERT INTO computer VALUES (NULL, ?, ?, ?, ?)");
		st.setString(1, computer.getname());
		st.setDate(2, new java.sql.Date(computer.getintroduced().getTime()));
		st.setDate(3, new java.sql.Date(computer.getdiscontinued().getTime()));
		st.setInt(4, computer.getcompanyId());
		st.executeUpdate();
		
		st.close();
		cn.close();
	}
}
