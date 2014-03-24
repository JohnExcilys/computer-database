package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;

@Repository
public class DAOComputer {
	@Autowired
	DataSource dataSource;

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	public List<Computer> getComputers() throws SQLException {
		{
			// Sans Criteria
			// String query = "FROM Computer";
			// return entityManager.createQuery(query).getResultList();

			// Avec Criteria
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Computer> criteriaQuery = builder
					.createQuery(Computer.class);
			Root<Computer> computerRoot = criteriaQuery.from(Computer.class);
			criteriaQuery.select(computerRoot);
			List<Computer> computerList = entityManager.createQuery(
					criteriaQuery).getResultList();
			return computerList;
		}
	}

	public void saveComputer(Computer computer) throws SQLException {
		entityManager.merge(computer);
	}

	public Computer getComputer(Long id) throws SQLException {
		return entityManager.find(Computer.class, id);
	}

	public void deleteComputer(Long id) throws NamingException, SQLException {
		entityManager.remove(getComputer(id));
	}

	@SuppressWarnings("unchecked")
	public List<Computer> findAllByCreteria(String search, ComputerOrder order,
			int startAt, int numberOfRows) throws SQLException {

		// Sans Criteria
		// StringBuilder sql = new StringBuilder();
		// sql.append("SELECT computer FROM Computer AS computer LEFT JOIN computer.company company");
		// if (search != null) {
		// sql.append(" WHERE computer.name LIKE :search OR company.name LIKE :search");
		// }
		// if (order != null) {
		// sql.append(" ORDER BY ").append(order.getOrderStatement());
		// }
		//
		// Query query = entityManager.createQuery(sql.toString());
		// if (search != null) {
		// query.setParameter("search", new StringBuilder("%").append(search)
		// .append("%").toString());
		// }
		//
		// query.setFirstResult(startAt);
		// query.setMaxResults(numberOfRows);
		//
		// return query.getResultList();

		// Avec Criteria
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Computer> criteriaQuery = builder
				.createQuery(Computer.class);
		Root<Computer> computerRoot = criteriaQuery.from(Computer.class);
		Join<Computer, Company> companyJoin = computerRoot.join("company",
				JoinType.LEFT);

		criteriaQuery.select(computerRoot);
		if (search != null) {
			criteriaQuery.where(builder.or(builder.like(computerRoot
					.get("name").as(String.class), search), builder.like(
					companyJoin.get("name").as(String.class), search)));
		}

		if (order != null) {
			switch (order) {
			case ORDER_BY_NAME_ASC:
				criteriaQuery.orderBy(builder.asc(computerRoot.get("name")));
				break;

			case ORDER_BY_NAME_DESC:
				criteriaQuery.orderBy(builder.desc(computerRoot.get("name")));
				break;

			case ORDER_BY_COMPANY_NAME_ASC:
				criteriaQuery.orderBy(builder.asc(companyJoin.get("name")));
				break;

			case ORDER_BY_COMPANY_NAME_DESC:
				criteriaQuery.orderBy(builder.desc(companyJoin.get("name")));
				break;

			case ORDER_BY_INTRODUCED_DATE_ASC:
				criteriaQuery.orderBy(builder.asc(computerRoot
						.get("introduced")));
				break;

			case ORDER_BY_INTRODUCED_DATE_DESC:
				criteriaQuery.orderBy(builder.desc(computerRoot
						.get("introduced")));
				break;

			case ORDER_BY_DISCONTINUED_DATE_ASC:
				criteriaQuery.orderBy(builder.asc(computerRoot
						.get("discontinued")));
				break;

			case ORDER_BY_DISCONTINUED_DATE_DESC:
				criteriaQuery.orderBy(builder.desc(computerRoot
						.get("discontinued")));
				break;

			default:
				break;
			}
		}

		List<Computer> computerList = entityManager.createQuery(criteriaQuery)
				.getResultList();
		return computerList;
	}

	public int count(String search) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(computer.id) FROM Computer AS computer LEFT JOIN computer.company company");
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
