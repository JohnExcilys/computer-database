package com.excilys.computerdb.model;

public class Company {
	private Long id;
	private String name;

	public Long getid() {
		return id;
	}

	public void setid(Long id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

	public Company() {
		this.id = (long) 0;
		this.name = "";
	}

	public Company(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("Company [id=").append(id).append(", name=")
				.append(name).append("]").toString();
	}
}
