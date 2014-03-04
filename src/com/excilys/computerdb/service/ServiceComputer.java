package com.excilys.computerdb.service;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.excilys.computerdb.dao.DAOFactory;
import com.excilys.computerdb.dao.DBConnection;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;
import com.mysql.jdbc.Connection;

public class ServiceComputer {
	private static ServiceComputer _instance = null;
	static Logger log = Logger.getLogger(ServiceComputer.class.getName());

	// Initialisation du Singleton
	private ServiceComputer() {

	}

	// Récupération de l'instance du Singleton
	synchronized public static ServiceComputer getInstance() {
		if (_instance == null) {
			_instance = new ServiceComputer();
		}
		return _instance;
	}

	public ArrayList<Computer> getComputers() throws SQLException {
		return DAOFactory.getInstance().getDAOComputer().getComputers();
	}

	public boolean saveComputer(int id, String name, String introduced,
			String discontinued, Company company) throws SQLException,
			ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		if (!name.equals("") && isDateValid(introduced)
				&& isDateValid(discontinued) && company.getid() != 0) {
			DAOFactory
					.getInstance()
					.getDAOComputer()
					.saveComputer(
							new Computer(id, name, df.parse(introduced), df
									.parse(discontinued), company));
		}
		return false;
	}

	public void deleteComputer(int id) throws NamingException, SQLException {
		DAOFactory.getInstance().getDAOComputer().deleteComputer(id);
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
		count = DAOFactory.getInstance().getDAOComputer().count(search);

		return count;
	}

	public List<Computer> findAllByCreteria(String search, ComputerOrder order,
			int startAt, int numberOfRows) throws SQLException {

		List<Computer> computers = null;
		computers = DAOFactory.getInstance().getDAOComputer()
				.findAllByCreteria(search, order, startAt, numberOfRows);

		return computers;
	}
}
