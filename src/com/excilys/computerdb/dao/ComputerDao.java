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

import org.apache.log4j.Logger;

import com.excilys.computerdb.model.Computer;

public class ComputerDao {
	private static ComputerDao _instance = null;
	static Logger log = Logger.getLogger(ComputerDao.class.getName());

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
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		Connection cn = DBConnection.getConnection();
		
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
	
	public ArrayList<Computer> getComputersByTag(String field, String sens) throws NamingException,
	SQLException {
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		Connection cn = DBConnection.getConnection();
		
		PreparedStatement st;
		if(!field.equals("name") && !field.equals("introduced") && !field.equals("discontinued") && !field.equals("cname")){
			// Si l'utilisateur change les champs à la main
			st = cn.prepareStatement("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id ORDER BY ? ASC");
			st.setString(1, "name");
		}else{
			// Si l'utilisateur est calme ...
			if(sens.equals("ASC")){
				st = cn.prepareStatement("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id ORDER BY "+field+" ASC");
			}else{
				st = cn.prepareStatement("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id ORDER BY "+field+" DESC");
			}
		}
		ResultSet rs = st.executeQuery();
		
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		while (rs.next()) {
			computerList.add(new Computer(rs.getInt("id"),rs.getString("name"), rs.getDate("introduced"), rs.getDate("discontinued"), rs.getInt("company_id"), rs.getString("cname")));
		}

		rs.close();
		st.close();
		cn.close();
		
		return computerList;
	}
	
	public ArrayList<Computer> getComputersByNameOrCompany(String seed) throws SQLException, NamingException{
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		Connection cn = DBConnection.getConnection();
		
		PreparedStatement st = cn.prepareStatement("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, co.name FROM computer AS c JOIN company AS co on c.company_id = co.id where c.name LIKE ? OR co.name LIKE ?");
		st.setString(1, "%"+seed+"%");
		st.setString(2, "%"+seed+"%");
		ResultSet rs = st.executeQuery();
				
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		while (rs.next()) {
			computerList.add(new Computer(rs.getInt("id"),rs.getString("name"), rs.getDate("introduced"), rs.getDate("discontinued"), rs.getInt("company_id"), rs.getString("co.name")));
		}

		rs.close();
		st.close();
		cn.close();
		
		return computerList;
		
	}
	
	public ArrayList<Computer> getComputersPaginated(int nbParPage, int pageActuelle) throws SQLException, NamingException{
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		Connection cn = DBConnection.getConnection();
		
		Statement st = cn.createStatement();
		ResultSet rs = null;
		if(pageActuelle == 1){
			rs = st.executeQuery("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id LIMIT "+(pageActuelle -1)+", "+nbParPage);
		}else{
			rs = st.executeQuery("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id LIMIT "+(((pageActuelle-1) * nbParPage))+", "+nbParPage);
		}
				
		ArrayList<Computer> computerList = new ArrayList<Computer>();
		while (rs.next()) {
			computerList.add(new Computer(rs.getInt("id"),rs.getString("name"), rs.getDate("introduced"), rs.getDate("discontinued"), rs.getInt("company_id"), rs.getString("cname")));
		}

		rs.close();
		st.close();
		cn.close();
		
		return computerList;
	}
	
	public void insertComputer(Computer computer) throws NamingException{
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		try{
			Connection cn = DBConnection.getConnection();
			
			PreparedStatement st = cn.prepareStatement("INSERT INTO computer VALUES (NULL, ?, ?, ?, ?)");
			st.setString(1, computer.getname());
			st.setDate(2, new java.sql.Date(computer.getintroduced().getTime()));
			st.setDate(3, new java.sql.Date(computer.getdiscontinued().getTime()));
			st.setInt(4, computer.getcompanyId());
			st.executeUpdate();
			
			st.close();
			cn.close();
			
			log.info("Insertion réussie - ComputerDao.insertComputer()");			
		}catch(SQLException e){
			log.info("Insertion ratée - ComputerDao.insertComputer()");
		}
		
	}
	
	public void updateComputer(Computer computer) throws NamingException, SQLException{
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		try {
			Connection cn = DBConnection.getConnection();
			
			PreparedStatement st = cn.prepareStatement("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");
			st.setString(1, computer.getname());
			st.setDate(2, new java.sql.Date(computer.getintroduced().getTime()));
			st.setDate(3, new java.sql.Date(computer.getdiscontinued().getTime()));
			st.setInt(4, computer.getcompanyId());
			st.setLong(5, computer.getid());
			st.executeUpdate();
			
			st.close();
			cn.close();
			
			log.info("La mise à jour est un succés - ComputerDao.updateComputer()");
		} catch (SQLException e) {
			
			log.info("La mise à jour est un echec - ComputerDao.updateComputer()");
		}
	}
	
	public void deleteComputer(int id) throws NamingException, SQLException{
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		try {
			Connection cn = DBConnection.getConnection();
			
			PreparedStatement st = cn.prepareStatement("DELETE FROM computer where id = ?");
			st.setInt(1, id);
			st.executeUpdate();
			
			st.close();
			cn.close();
			log.info("La suppression est un succés - ComputerDao.deleteComputer()");
		} catch (SQLException e) {
			log.info("La suppression est un echec- ComputerDao.deleteComputer()");
		}
	}
	
	public Computer getComputer(int id) throws NamingException, SQLException{
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		Connection cn = DBConnection.getConnection();
		
		PreparedStatement st = cn.prepareStatement("SELECT id, name, introduced, discontinued, company_id FROM computer where id = ?");
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		Computer computer = new Computer();
		if (rs.last()){
			computer = new Computer(rs.getInt("id"),rs.getString("name"), rs.getDate("introduced"), rs.getDate("discontinued"), rs.getInt("company_id"));
		}
		
		rs.close();
		st.close();
		cn.close();
		
		return computer;
	}
	
	public String getCompanyName(int idCompany) throws NamingException, SQLException{
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		Connection cn = DBConnection.getConnection();
		
		PreparedStatement st = cn.prepareStatement("SELECT name FROM company WHERE id = ?");
		st.setInt(1, idCompany);
		ResultSet rs = st.executeQuery();
		String name = "";
		if(rs.last()){
			name = rs.getString("name");
		}
		
		return name;
	}
	
	public int countComputers() throws SQLException, NamingException{
		/*
		Context ctx = new InitialContext();
		Context initContext = (Context) ctx.lookup("java:/comp/env");
		DataSource ds = (DataSource) initContext.lookup("computerDb");
		Connection cn = ds.getConnection();
		*/
		Connection cn = DBConnection.getConnection();
		
		Statement st = cn.createStatement();
		ResultSet rs = st.executeQuery("SELECT COUNT(*) AS cpt FROM computer");
		int count = 0;
		
		if(rs.last()){
			count = rs.getInt("cpt");
		}
		

		rs.close();
		st.close();
		cn.close();
		
		return count;
	}
	
}
