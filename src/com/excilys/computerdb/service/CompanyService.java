package com.excilys.computerdb.service;

import com.excilys.computerdb.dao.CompanyDao;

public class CompanyService {
	private CompanyDao dao = CompanyDao.getInstance();
	private static CompanyService _instance = null;

	// Initialisation du Singleton
	private CompanyService() {

	}

	// Récupération de l'instance du Singleton
	synchronized public static CompanyService getInstance() {
		if (_instance == null) {
			_instance = new CompanyService();
		}
		return _instance;
	}
}
