package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.excilys.computerdb.dao.mapper.ComputerMapper;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;

public class DAOComputer extends JdbcDaoSupport{
	Logger log = Logger.getLogger(DAOComputer.class.getName());

	public List<Computer> getComputers() throws SQLException {
		{

			String query = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.id AS cid, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id";

			return getJdbcTemplate().query(query, new ComputerMapper());
		}
	}

	public void saveComputer(Computer computer) throws SQLException {
		String query = "INSERT INTO computer (id, name, introduced, discontinued, company_id)  VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, introduced = ?, discontinued = ?, company_id = ?";

		if (computer.getId() != 0) {
			if (computer.getCompany().getid() == 0) {
				getJdbcTemplate().update(
						query,
						new Object[] {
								computer.getId(),
								computer.getName(),
								new java.sql.Date(computer.getIntroduced()
										.getTime()),
								new java.sql.Date(computer.getDiscontinued()
										.getTime()),
								Types.INTEGER,
								computer.getName(),
								new java.sql.Date(computer.getIntroduced()
										.getTime()),
								new java.sql.Date(computer.getDiscontinued()
										.getTime()),
								computer.getCompany().getid() });
			} else {
				getJdbcTemplate().update(
						query,
						new Object[] {
								computer.getId(),
								computer.getName(),
								new java.sql.Date(computer.getIntroduced()
										.getTime()),
								new java.sql.Date(computer.getDiscontinued()
										.getTime()),
								computer.getCompany().getid(),
								computer.getName(),
								new java.sql.Date(computer.getIntroduced()
										.getTime()),
								new java.sql.Date(computer.getDiscontinued()
										.getTime()),
								computer.getCompany().getid() });
			}
		} else {
			if (computer.getCompany().getid() == 0) {
				getJdbcTemplate().update(
						query,
						new Object[] {
								Types.INTEGER,
								computer.getName(),
								new java.sql.Date(computer.getIntroduced()
										.getTime()),
								new java.sql.Date(computer.getDiscontinued()
										.getTime()),
								Types.INTEGER,
								computer.getName(),
								new java.sql.Date(computer.getIntroduced()
										.getTime()),
								new java.sql.Date(computer.getDiscontinued()
										.getTime()),
								computer.getCompany().getid() });
			} else {
				getJdbcTemplate().update(
						query,
						new Object[] {
								Types.INTEGER,
								computer.getName(),
								new java.sql.Date(computer.getIntroduced()
										.getTime()),
								new java.sql.Date(computer.getDiscontinued()
										.getTime()),
								computer.getCompany().getid(),
								computer.getName(),
								new java.sql.Date(computer.getIntroduced()
										.getTime()),
								new java.sql.Date(computer.getDiscontinued()
										.getTime()),
								computer.getCompany().getid() });
			}
		}
	}

	public Computer getComputer(Long id) throws SQLException {
		String query = "SELECT c.id, c.name, c.introduced, c.discontinued, cp.id AS cid, cp.name AS cname FROM computer AS c JOIN company AS cp ON c.company_id=cp.id where c.id = ?";
		getJdbcTemplate().queryForObject(query, new Object[] { id },
				new ComputerMapper());

		return getJdbcTemplate().queryForObject(query, new Object[] { id },
				new ComputerMapper());
	}

	public void deleteComputer(Long id) throws NamingException, SQLException {
		String query = "DELETE FROM computer where id = ?";
		getJdbcTemplate().update(query, new Object[] { id });
	}

	public List<Computer> findAllByCreteria(String search,
			ComputerOrder order, int startAt, int numberOfRows)
			throws SQLException {

		String sql = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
				+ "FROM computer LEFT JOIN company ON computer.company_id = company.id";
		if (search != null) {
			sql += " WHERE computer.name LIKE ? OR company.name LIKE ?";
		}
		if (order != null) {
			sql += " ORDER BY " + order.getOrderStatement();
		}
		sql += " LIMIT ?, ?";

		if (search != null){
			return getJdbcTemplate().query(sql, new Object[]{"%"+search+"%","%"+search+"%",startAt,numberOfRows,}, new ComputerMapper());
		}
		return getJdbcTemplate().query(sql, new Object[]{startAt,numberOfRows}, new ComputerMapper());
	}

	public int count(String search) throws SQLException {
		String sql = "SELECT COUNT(id) FROM computer";
		if (search == null) {
			return getJdbcTemplate().queryForObject(sql, Integer.class);
		} else {
			sql += " WHERE name LIKE ?";
			return getJdbcTemplate().queryForObject(sql, Integer.class,
					"%" + search + "%");
		}
	}
	
	public DAOComputer() {
	}
}
