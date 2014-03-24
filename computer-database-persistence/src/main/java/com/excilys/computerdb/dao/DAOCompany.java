package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.QCompany;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class DAOCompany {

	@Autowired
	DataSource dataSource;

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	public List<Company> getCompanies() throws NamingException, SQLException {

		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(entityManager);
		return query.from(company).list(company);
	}

	public DAOCompany() {

	}
}
