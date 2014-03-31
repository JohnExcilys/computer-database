package com.excilys.computerdb.webservice.impl;

import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ServiceComputer;
import com.excilys.computerdb.webservice.ComputerWebService;

@WebService
public class ComputerWebServiceImpl implements ComputerWebService {

	@Autowired
	ServiceComputer servicecomputer;

	@Override
	public List<Computer> findAll() {
		return servicecomputer.findAll();
	}

}
