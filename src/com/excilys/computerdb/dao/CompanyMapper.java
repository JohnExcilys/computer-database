package com.excilys.computerdb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdb.model.Company;

public class CompanyMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet rs, int rowNumber) throws SQLException {
		return new Company(rs.getInt(1), rs.getString(2));
	}

}
