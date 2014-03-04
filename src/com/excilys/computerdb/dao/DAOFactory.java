package com.excilys.computerdb.dao;

import org.apache.log4j.Logger;

public class DAOFactory {
	private static DAOFactory _instance = null;
	static Logger log = Logger.getLogger(DAOFactory.class.getName());
	
	// Initialisation du Singleton
	private DAOFactory() {

	}

	// Récupération de l'instance du Singleton
	synchronized public static DAOFactory getInstance() {
		if (_instance == null) {
			_instance = new DAOFactory();
		}
		return _instance;
	}
	
	public DAOComputer getDAOComputer(){
		return DAOComputer.getInstance();
	}
	
	public DAOCompany getDAOCompany(){
		return DAOCompany.getInstance();
	}
}
