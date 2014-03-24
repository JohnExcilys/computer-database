package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;

@Repository
public class DAOComputer {
	@Autowired
	JdbcTemplate getJdbcTemplate;

	@Autowired
	DataSource dataSource;

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Computer> getComputers() throws SQLException {
		{
			String query = "FROM Computer";
			return entityManager.createQuery(query).getResultList();
		}
	}

	public void saveComputer(Computer computer) throws SQLException {
		entityManager.merge(computer);
	}

	public Computer getComputer(Long id) throws SQLException {
		return entityManager.find(Computer.class, id);
	}

	public void deleteComputer(Long id) throws NamingException, SQLException {
		Computer c = entityManager.find(Computer.class, id);
		entityManager.remove(c);
	}

	@SuppressWarnings("unchecked")
	public List<Computer> findAllByCreteria(String search, ComputerOrder order,
			int startAt, int numberOfRows) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT computer FROM Computer AS computer LEFT JOIN computer.company company");
		if (search != null) {
			sql.append(" WHERE computer.name LIKE :search OR company.name LIKE :search");
		}
		if (order != null) {
			sql.append(" ORDER BY ").append(order.getOrderStatement());
		}

		Query query = entityManager.createQuery(sql.toString());
		if (search != null) {
			query.setParameter("search", new StringBuilder("%").append(search)
					.append("%").toString());
		}

		query.setFirstResult(startAt);
		query.setMaxResults(numberOfRows);

		return query.getResultList();
	}

	public int count(String search) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(id) FROM Computer AS computer LEFT JOIN computer.company company");
		if (search != null) {
			sql.append(" WHERE computer.name LIKE :search OR company.name LIKE :search");
		}

		Query query = entityManager.createQuery(sql.toString());
		if (search != null) {
			query.setParameter("search", new StringBuilder("%").append(search)
					.append("%").toString());
		}

		return Integer.parseInt(query.getSingleResult().toString());
	}

	public DAOComputer() {
	}
}
