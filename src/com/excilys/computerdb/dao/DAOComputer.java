package com.excilys.computerdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;

public class DAOComputer {
	private static DAOComputer _instance = null;
	static Logger log = Logger.getLogger(DAOComputer.class.getName());

	// Initialisation du Singleton
	private DAOComputer() {

	}

	// Récupération de l'instance du Singleton
	synchronized public static DAOComputer getInstance() {
		if (_instance == null) {
			_instance = new DAOComputer();
		}
		return _instance;
	}

	public ArrayList<Computer> getComputers() throws SQLException {
		{
			Connection cn = DBConnection.getConnection();

			Statement st = cn.createStatement();
			ResultSet rs = st
					.executeQuery("SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.id AS cid, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id");

			ArrayList<Computer> computerList = new ArrayList<Computer>();
			while (rs.next()) {
				computerList.add(new Computer(rs.getInt("id"), rs
						.getString("name"), rs.getDate("introduced"), rs
						.getDate("discontinued"), new Company(rs.getInt("cid"),
						rs.getString("cname"))));
			}

			rs.close();
			st.close();
			cn.close();

			return computerList;
		}
	}

	public void saveComputer(Computer computer) throws SQLException {
		String query = "INSERT INTO computer (id, name, introduced, discontinued, company_id)  VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, introduced = ?, discontinued = ?, company_id = ?";
		Connection cn = DBConnection.getConnection();
		PreparedStatement st = cn.prepareStatement(query);
		if (computer.getId() != 0) {
			st.setInt(1, computer.getId());
		} else {
			st.setNull(1, Types.INTEGER);
		}
		st.setString(2, computer.getName());
		st.setDate(3, new java.sql.Date(computer.getIntroduced().getTime()));
		st.setDate(4, new java.sql.Date(computer.getDiscontinued().getTime()));
		if (computer.getCompany().getid() == 0) {
			st.setNull(5, Types.INTEGER);
		} else {
			st.setInt(5, computer.getCompany().getid());
		}
		st.setString(6, computer.getName());
		st.setDate(7, new java.sql.Date(computer.getIntroduced().getTime()));
		st.setDate(8, new java.sql.Date(computer.getDiscontinued().getTime()));
		st.setInt(9, computer.getCompany().getid());

		st.execute();

		st.close();
		cn.close();
	}

	public Computer getComputer(int id) throws SQLException {
		Connection cn = DBConnection.getConnection();

		PreparedStatement st = cn
				.prepareStatement("SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cid, cp.name AS cname FROM computer AS c JOIN company AS cp ON c.company_id=cp.id where c.id = ?");
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		Computer computer = new Computer();
		if (rs.last()) {
			computer = new Computer(rs.getInt("id"), rs.getString("name"),
					rs.getDate("introduced"), rs.getDate("discontinued"),
					new Company(rs.getInt("cid"), rs.getString("cname")));
		}

		rs.close();
		st.close();
		cn.close();

		return computer;
	}

	public void deleteComputer(int id) throws NamingException, SQLException {
		Connection cn = DBConnection.getConnection();

		PreparedStatement st = cn
				.prepareStatement("DELETE FROM computer where id = ?");
		st.setInt(1, id);
		st.executeUpdate();

		st.close();
		cn.close();

	}

	public ArrayList<Computer> findAllByCreteria(String search, ComputerOrder order,
			int startAt, int numberOfRows) throws SQLException {
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		ArrayList<Computer> computers = null;

		try {
			con = DBConnection.getConnection();
			String sql = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.id, company.name "
					+ "FROM computer LEFT JOIN company ON computer.company_id = company.id";
			if (search != null) {
				sql += " WHERE computer.name LIKE ? OR company.name LIKE ?";
			}
			if (order != null) {
				sql += " ORDER BY " + order.getOrderStatement();
			}
			sql += " LIMIT ?, ?";

			statement = con.prepareStatement(sql);

			int i = 0;
			if (search != null) {
				statement.setString(1, "%" + search + "%");
				statement.setString(2, "%" + search + "%");
				i = 2;
			}
			statement.setInt(i + 1, startAt);
			statement.setInt(i + 2, numberOfRows);

			rs = statement.executeQuery();
			computers = new ArrayList<>();
			while (rs.next()) {
				computers.add(new Computer(rs.getInt("computer.id"), rs.getString("computer.name"),
						rs.getDate("computer.introduced"), rs.getDate("computer.discontinued"),
						new Company(rs.getInt("company.id"), rs.getString("company.name"))));
			}
		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {

			}

		}
		return computers;
	}
	
	public int count(String search) throws SQLException {

		Connection con = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		int result = 0;
		try {
			con = DBConnection.getConnection();

			String sql = "SELECT COUNT(id) FROM computer";

			if (search != null) {
				sql += " WHERE name LIKE ?";
			}

			statement = con.prepareStatement(sql);

			if (search != null) {
				statement.setString(1, "%" + search + "%");
			}

			rs = statement.executeQuery();
			rs.next();
			result = rs.getInt(1);

		} finally {
			try {
				if (con != null) {
					con.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {

			}

		}
		return result;
	}
}
