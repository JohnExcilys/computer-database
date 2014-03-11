package com.excilys.computerdb.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdb.dao.DAOComputer;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;
import com.excilys.computerdb.model.dto.DtoComputer;

@Service
public class ServiceComputer {
	Logger log = Logger.getLogger(ServiceComputer.class.getName());
	@Autowired
	DAOComputer daoComputer;
	
	@Transactional
	public List<Computer> getComputers() throws SQLException {
		List<Computer> computerList;
		computerList = daoComputer.getComputers();
		System.out.println(computerList);
		return computerList;
	}
	
	@Transactional
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
	
	@Transactional
	public boolean saveComputer(DtoComputer cDTO) throws SQLException,
			ParseException {
					daoComputer.saveComputer(cDTO);
		return false;
	}

	@Transactional
	public void deleteComputer(Long id) throws NamingException, SQLException {
		daoComputer.deleteComputer(id);
	}

	@Transactional
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

	@Transactional
	public int count(String search) throws SQLException {
		int count = 0;
		count = daoComputer.count(search);
		return count;
	}

	@Transactional
	public List<Computer> findAllByCreteria(String search, ComputerOrder order,
			int startAt, int numberOfRows) throws SQLException {

		List<Computer> computers = null;
		computers = daoComputer.findAllByCreteria(search, order, startAt, numberOfRows);
		
		return computers;
	}

	@Transactional
	public Computer getComputer(Long id) throws SQLException {
		Computer cp;

		cp = daoComputer.getComputer(id);
		return cp;
	}
}
