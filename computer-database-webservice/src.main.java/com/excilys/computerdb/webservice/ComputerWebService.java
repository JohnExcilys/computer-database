package com.excilys.computerdb.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.excilys.computerdb.model.Computer;

@WebService
public interface ComputerWebService {

	@WebMethod
	public List<Computer> findAll();
}
