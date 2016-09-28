package com.excilys.computerdb.controller;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ServiceComputer;

@WebService
@SOAPBinding(style = Style.RPC)
public class ComputerWebService {

	@Autowired
	ServiceComputer servicecomputer;

	@WebMethod(operationName = "findAll")
	public List<Computer> findAllWS() {
		return servicecomputer.findAll();
	}
}
