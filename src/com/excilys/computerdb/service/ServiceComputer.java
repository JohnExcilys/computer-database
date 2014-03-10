package com.excilys.computerdb.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.computerdb.dao.DAOComputer;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;
import com.excilys.computerdb.model.dto.DtoComputer;

@Component
public class ServiceComputer {
	Logger log = Logger.getLogger(ServiceComputer.class.getName());
	@Autowired
	DAOComputer daoComputer;
	
	public ServiceComputer() {
		// TODO Auto-generated constructor stub
	}
	
	public DAOComputer getDaoComputer() {
		return daoComputer;
	}

	public void setDaoComputer(DAOComputer daoComputer) {
		this.daoComputer = daoComputer;
	}

	public List<Computer> getComputers() throws SQLException {
		List<Computer> computerList;
		computerList = daoComputer.getComputers();
		System.out.println(computerList);
		return computerList;
	}

	public boolean saveComputer(Long id, String name, String introduced,
			String discontinued, Company company) throws SQLException,
			ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		if (!name.equals("") && isDateValid(introduced)
				&& isDateValid(discontinued) && company.getid() != 0) {

			daoComputer.saveComputer(DAOComputer.createDTO(new Computer(id, name, df
					.parse(introduced), df.parse(discontinued), company)));

		}
		return false;
	}
	
	public boolean saveComputer(DtoComputer cDTO) throws SQLException,
			ParseException {
					daoComputer.saveComputer(cDTO);
		return false;
	}

	public void deleteComputer(Long id) throws NamingException, SQLException {
		daoComputer.deleteComputer(id);
	}

	public static boolean isDateValid(String date) {
		try {
			if (date != "") {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				df.parse(date);
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
		return false;
	}

	public int count(String search) throws SQLException {
		int count = 0;
		count = daoComputer.count(search);
		return count;
	}

	public List<Computer> findAllByCreteria(String search, ComputerOrder order,
			int startAt, int numberOfRows) throws SQLException {

		List<Computer> computers = null;
		computers = daoComputer.findAllByCreteria(search, order, startAt, numberOfRows);
		
		return computers;
	}

	public Computer getComputer(Long id) throws SQLException {
		Computer cp;

		cp = daoComputer.getComputer(id);
		return cp;
	}
}
