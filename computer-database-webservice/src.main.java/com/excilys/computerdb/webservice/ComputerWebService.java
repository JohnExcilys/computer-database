package com.excilys.computerdb.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ServiceComputer;

@WebService
public class ComputerWebService {

	@Autowired
	ServiceComputer servicecomputer;

	@WebMethod
	public List<Computer> findAll() {
		return servicecomputer.findAll();
	}
}
