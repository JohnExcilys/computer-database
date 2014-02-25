package com.excilys.computerdb.model;

import java.util.Date;

public class Computer {
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private int companyId;
	private String companyName;

	// Getter et Setter
	public int getid() {
		return id;
	}

	public void setid(int id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public Date getintroduced() {
		return introduced;
	}

	public void setintroduced(Date introduced) {
		this.introduced = introduced;
	}

	public Date getdiscontinued() {
		return discontinued;
	}

	public void setdiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}

	public int getcompanyId() {
		return companyId;
	}

	public void setcompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public String getcompanyName() {
		return companyName;
	}

	public void setcompanyName(String companyName) {
		this.companyName = companyName;
	}

	// Methodes
	public Computer() {
		this.id = 0;
		this.name = "";
		this.introduced = new Date();
		this.discontinued = new Date();
		this.companyId = 0;
	}
	
	public Computer(String name, Date introduced, Date discontinued,
			int companyId) {
		this.id = 0;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public Computer(int id, String name, Date introduced, Date discontinued,
			int companyId) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}
	
	public Computer(int id, String name, Date introduced, Date discontinued,
			int companyId, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}

}
