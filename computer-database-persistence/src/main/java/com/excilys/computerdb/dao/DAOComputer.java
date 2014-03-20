package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.excilys.computerdb.dao.mapper.ComputerMapper;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;

public class DAOComputer extends JdbcDaoSupport {
	Logger log = Logger.getLogger(DAOComputer.class.getName());

	public List<Computer> getComputers() throws SQLException {
		{

			String query = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.id AS cid, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id";

			return getJdbcTemplate().query(query, new ComputerMapper());
		}
	}

	public void saveComputer(Computer computer) throws SQLException {
		String query = "INSERT INTO computer (id, name, introduced, discontinued, company_id)  VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, introduced = ?, discontinued = ?, company_id = ?";
		Date introduced = null;
		Date discontinued = null;
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toDate();
		}
		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toDate();
		}
		getJdbcTemplate()
				.update(query,
						new Object[] { computer.getId(), computer.getName(),
								introduced, discontinued,
								computer.getCompany().getid(),
								computer.getName(), introduced, discontinued,
								computer.getCompany().getid() });

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

	public List<Computer> findAllByCreteria(String search, ComputerOrder order,
			int startAt, int numberOfRows) throws SQLException {
		StringBuilder sql = new StringBuilder();
		StringBuilder sbSearch = new StringBuilder();
		sql.append("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id");
		if (search != null) {
			sql.append(" WHERE computer.name LIKE ? OR company.name LIKE ?");
		}
		if (order != null) {
			sql.append(" ORDER BY ").append(order.getOrderStatement());
		}
		sql.append(" LIMIT ?, ?");

		if (search != null) {
			sbSearch.append("%").append(search).append("%");
			return getJdbcTemplate().query(
					sql.toString(),
					new Object[] { sbSearch.toString(), sbSearch.toString(),
							startAt, numberOfRows, }, new ComputerMapper());
		}
		return getJdbcTemplate().query(sql.toString(),
				new Object[] { startAt, numberOfRows }, new ComputerMapper());
	}

	public int count(String search) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(id) FROM computer");
		if (search == null) {
			return getJdbcTemplate().queryForObject(sql.toString(),
					Integer.class);
		} else {
			sql.append(" WHERE name LIKE ?");
			return getJdbcTemplate().queryForObject(sql.toString(),
					Integer.class, "%" + search + "%");
		}
	}

	public DAOComputer() {
	}
}
