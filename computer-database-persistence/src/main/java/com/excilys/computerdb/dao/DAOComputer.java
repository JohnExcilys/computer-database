package com.excilys.computerdb.dao;

import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;
import com.excilys.computerdb.model.QCompany;
import com.excilys.computerdb.model.QComputer;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class DAOComputer {
	@Autowired
	DataSource dataSource;

	@PersistenceContext(unitName = "entityManagerFactory")
	private EntityManager entityManager;

	public List<Computer> getComputers() throws SQLException {
		{
			QComputer computer = QComputer.computer;
			JPAQuery query = new JPAQuery(entityManager);
			return query.from(computer).list(computer);
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

	public List<Computer> findAllByCreteria(String search, ComputerOrder order,
			int startAt, int numberOfRows) throws SQLException {
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		JPAQuery query = new JPAQuery(entityManager);
		query.from(computer);
		query.leftJoin(computer.company, company);

		if (search != null) {
			query.where(computer.name.like(search)
					.or(company.name.like(search)));
		}

		if (order != null) {
			switch (order) {
			case ORDER_BY_NAME_ASC:
				query.orderBy(computer.name.asc());
				break;

			case ORDER_BY_NAME_DESC:
				query.orderBy(computer.name.desc());
				break;

			case ORDER_BY_COMPANY_NAME_ASC:
				query.orderBy(company.name.asc());
				break;

			case ORDER_BY_COMPANY_NAME_DESC:
				query.orderBy(company.name.desc());
				break;

			case ORDER_BY_INTRODUCED_DATE_ASC:
				query.orderBy(computer.introduced.asc());
				break;

			case ORDER_BY_INTRODUCED_DATE_DESC:
				query.orderBy(computer.introduced.desc());
				break;

			case ORDER_BY_DISCONTINUED_DATE_ASC:
				query.orderBy(computer.discontinued.asc());
				break;

			case ORDER_BY_DISCONTINUED_DATE_DESC:
				query.orderBy(computer.discontinued.desc());
				break;

			default:
				break;
			}
		}

		return query.limit(numberOfRows).offset(startAt).list(computer);
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
