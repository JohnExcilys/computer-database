package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.dao.mapper.CompanyMapper;
import com.excilys.computerdb.model.Company;

@Repository
public class DAOCompany {
	static Logger log = Logger.getLogger(DAOCompany.class.getName());
	@Autowired
	JdbcTemplate getJdbcTemplate;

	public List<Company> getCompanies() throws NamingException, SQLException {
		String query = "SELECT id, name FROM company";

		return getJdbcTemplate.query(query, new CompanyMapper());
	}

	public DAOCompany() {

	}
}
