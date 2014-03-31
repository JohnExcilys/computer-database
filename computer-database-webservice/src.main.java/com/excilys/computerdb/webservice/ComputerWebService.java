package com.excilys.computerdb.webservice;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.excilys.computerdb.model.Computer;

public interface ComputerWebService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Computer> findAll();

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Computer findOne(@PathParam("id") int id);
}