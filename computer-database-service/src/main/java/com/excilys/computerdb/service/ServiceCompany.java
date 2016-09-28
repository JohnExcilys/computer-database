package com.excilys.computerdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdb.dao.CompanyRepository;
import com.excilys.computerdb.model.Company;

@Service
public class ServiceCompany {
	@Autowired
	private CompanyRepository daoCompany;

	public Company find(long id) {
		return daoCompany.findOne(id);
	}

	public List<Company> findAll() {
		return daoCompany.findAll();
	}

	public void create(Company c) {
		daoCompany.save(c);
	}

	public void update(Company c) {
		daoCompany.save(c);
	}

	public void delete(long id) {
		daoCompany.delete(id);
	}
}
