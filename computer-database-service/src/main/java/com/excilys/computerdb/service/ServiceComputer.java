package com.excilys.computerdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdb.dao.ComputerRepository;
import com.excilys.computerdb.model.Computer;

@Service
@Transactional
public class ServiceComputer {

	@Autowired
	ComputerRepository daoComputer;

	public Computer find(long id) {
		return daoComputer.findOne(id);
	}

	public Page<Computer> findAllByName(String search, Pageable pageable) {
		return daoComputer.findByNameContainingOrCompanyNameContaining(search,
				search, pageable);
	}

	public Page<Computer> findAll(Pageable pageable) {
		return daoComputer.findAll(pageable);
	}

	public List<Computer> findAll() {
		return daoComputer.findAll();
	}

	public void create(Computer c) {
		daoComputer.save(c);
	}

	public void update(Computer c) {
		daoComputer.save(c);
	}

	public void delete(long id) {
		daoComputer.delete(id);
	}
}
