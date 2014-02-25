package com.excilys.computerdb.service;

import com.excilys.computerdb.dao.ComputerDao;

public class ComputerService {
	private ComputerDao dao = ComputerDao.getInstance();
	private static ComputerService _instance = null;

	// Initialisation du Singleton
	private ComputerService() {

	}

	// Récupération de l'instance du Singleton
	synchronized public static ComputerService getInstance() {
		if (_instance == null) {
			_instance = new ComputerService();
		}
		return _instance;
	}
}
