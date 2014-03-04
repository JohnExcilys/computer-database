package com.excilys.computerdb.service;

import org.apache.log4j.Logger;

public class ServiceCompany {
	private static ServiceCompany _instance = null;
	static Logger log = Logger.getLogger(ServiceCompany.class.getName());
	
	// Initialisation du Singleton
	private ServiceCompany() {

	}

	// Récupération de l'instance du Singleton
	synchronized public static ServiceCompany getInstance() {
		if (_instance == null) {
			_instance = new ServiceCompany();
		}
		return _instance;
	}
}
