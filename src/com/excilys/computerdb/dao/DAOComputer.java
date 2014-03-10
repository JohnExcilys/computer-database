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
import com.excilys.computerdb.model.dto.DtoComputer;

public class DAOComputer extends JdbcDaoSupport {
	Logger log = Logger.getLogger(DAOComputer.class.getName());

	public List<Computer> getComputers() throws SQLException {
		{

			String query = "SELECT c.id, c.name, c.introduced, c.discontinued, c.company_id, cp.id AS cid, cp.name AS cname FROM computer AS c LEFT JOIN company AS cp ON c.company_id = cp.id";

			return getJdbcTemplate().query(query, new ComputerMapper());
		}
	}

	public void saveComputer(DtoComputer computer) throws SQLException {
		String query = "INSERT INTO computer (id, name, introduced, discontinued, company_id)  VALUES(?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE name = ?, introduced = ?, discontinued = ?, company_id = ?";
		System.out.println(computer);

		if (computer.getId() == null ) {
			if (computer.getCompanyId() == 0) {
				getJdbcTemplate().update(
						query,
						new Object[] {
								computer.getId(),
								computer.getName(),
								computer.getIntroduced(),
								computer.getDiscontinued(),
								Types.INTEGER,
								computer.getName(),
								computer.getIntroduced(),
								computer.getDiscontinued(),
								computer.getCompanyId() });
			} else {
				getJdbcTemplate().update(
						query,
						new Object[] {
								computer.getId(),
								computer.getName(),
								computer.getIntroduced(),
								computer.getDiscontinued(),
								computer.getCompanyId(),
								computer.getName(),
								computer.getIntroduced(),
								computer.getDiscontinued(),
								computer.getCompanyId() });
			}
		} else {
			if (computer.getCompanyId() == 0) {
				getJdbcTemplate().update(
						query,
						new Object[] {
								Types.INTEGER,
								computer.getName(),
								computer.getIntroduced(),
								computer.getDiscontinued(),
								Types.INTEGER,
								computer.getName(),
								computer.getIntroduced(),
								computer.getDiscontinued(),
								computer.getCompanyId() });
			} else {
				getJdbcTemplate().update(
						query,
						new Object[] {
								Types.INTEGER,
								computer.getName(),
								computer.getIntroduced(),
								computer.getDiscontinued(),
								computer.getCompanyId(),
								computer.getName(),
								computer.getIntroduced(),
								computer.getDiscontinued(),
								computer.getCompanyId() });
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

	public List<Computer> findAllByCreteria(String search, ComputerOrder order,
			int startAt, int numberOfRows) throws SQLException {

		String sql = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
				+ "FROM computer LEFT JOIN company ON computer.company_id = company.id";
		if (search != null) {
			sql += " WHERE computer.name LIKE ? OR company.name LIKE ?";
		}
		if (order != null) {
			sql += " ORDER BY " + order.getOrderStatement();
		}
		sql += " LIMIT ?, ?";

		if (search != null) {
			return getJdbcTemplate().query(
					sql,
					new Object[] { "%" + search + "%", "%" + search + "%",
							startAt, numberOfRows, }, new ComputerMapper());
		}
		return getJdbcTemplate().query(sql,
				new Object[] { startAt, numberOfRows }, new ComputerMapper());
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

	public static DtoComputer createDTO(Computer c) {
		DtoComputer cDto = null;
		if (c != null) {
			cDto = new DtoComputer();
			cDto.setId(c.getId());
			cDto.setName(c.getName());
			cDto.setIntroduced(c.getIntroduced());
			cDto.setDiscontinued(c.getDiscontinued());
			cDto.setCompanyId(c.getCompany().getid());
			cDto.setCompanyName(c.getCompany().getname());
		}
		return cDto;
	}

	public static Computer createComputerFromDto(DtoComputer dtoC) {
		Computer c = null;
		if (dtoC != null) {
			c = new Computer();
			c.setId(dtoC.getId());
			c.setName(dtoC.getName());
			c.setIntroduced(dtoC.getIntroduced());
			c.setDiscontinued(dtoC.getDiscontinued());
			c.getCompany().setid(dtoC.getCompanyId());
			c.getCompany().setname(dtoC.getName());
		}
		return c;
	}
}
