package com.excilys.computerdb.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.LocalDate;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;

public class ComputerMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int rowNumber) throws SQLException {
		LocalDate introduced = null;
		LocalDate discontinued = null;
		if (rs.getDate(3) != null) {
			introduced = new LocalDate(rs.getDate(3));
		}
		if (rs.getDate(4) != null) {
			discontinued = new LocalDate(rs.getDate(4));
		}
		return new Computer(rs.getLong(1), rs.getString(2), introduced,
				discontinued, new Company(rs.getLong(5), rs.getString(6)));
	}

}
