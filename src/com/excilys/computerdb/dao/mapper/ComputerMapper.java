package com.excilys.computerdb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;

public class ComputerMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int rowNumber) throws SQLException {
		return new Computer(rs.getLong(1), rs.getString(2), rs.getDate(3),
				rs.getDate(4), new Company(rs.getLong(5), rs.getString(6)));
	}

}
