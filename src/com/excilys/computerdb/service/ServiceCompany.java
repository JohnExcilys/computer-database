package com.excilys.computerdb.service;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdb.dao.DAOCompany;
import com.excilys.computerdb.model.Company;

@Service
public class ServiceCompany {
	Logger log = Logger.getLogger(ServiceCompany.class.getName());
	@Autowired
	DAOCompany daoCompany;

	@Transactional
	public List<Company> getCompanies() throws SQLException, NamingException {
		List<Company> companyList;

		companyList = daoCompany.getCompanies();
		return companyList;
	}
}
