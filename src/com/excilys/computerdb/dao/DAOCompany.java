package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.excilys.computerdb.dao.mapper.CompanyMapper;
import com.excilys.computerdb.model.Company;

public class DAOCompany extends JdbcDaoSupport {
	static Logger log = Logger.getLogger(DAOCompany.class.getName());

	public List<Company> getCompanies() throws NamingException, SQLException {
		String query = "SELECT id, name FROM company";

		return getJdbcTemplate().query(query, new CompanyMapper());
	}
	
	public DAOCompany() {
		
	}
}
