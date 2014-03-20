package com.excilys.computerdb.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.NamingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdb.dao.DAOComputer;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;
import com.excilys.computerdb.model.WrapperListCount;

@Service
@Transactional
public class ServiceComputer {
	Logger log = Logger.getLogger(ServiceComputer.class.getName());
	@Autowired
	DAOComputer daoComputer;

	public List<Computer> getComputers() throws SQLException {
		List<Computer> computerList;
		computerList = daoComputer.getComputers();
		return computerList;
	}

	public boolean saveComputer(Computer c) throws SQLException, ParseException {
		daoComputer.saveComputer(c);
		return false;
	}

	public void deleteComputer(Long id) throws NamingException, SQLException {
		daoComputer.deleteComputer(id);
	}

	public int count(String search) throws SQLException {
		int count = 0;
		count = daoComputer.count(search);
		return count;
	}

	public WrapperListCount findAllByCreteria(String search,
			ComputerOrder order, int startAt, int numberOfRows)
			throws SQLException {

		List<Computer> computers = null;
		computers = daoComputer.findAllByCreteria(search, order, startAt,
				numberOfRows);
		int count = daoComputer.count(search);
		WrapperListCount wrapper = new WrapperListCount(computers, count);

		return wrapper;
	}

	public Computer getComputer(Long id) throws SQLException {
		Computer cp;

		cp = daoComputer.getComputer(id);
		return cp;
	}
}
