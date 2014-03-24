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

@Repository
public class DAOCompany {

	@Autowired
	DataSource dataSource;

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Company> getCompanies() throws NamingException, SQLException {
		String query = "FROM Company";
		return entityManager.createQuery(query).getResultList();
	}

	public DAOCompany() {

	}
}
