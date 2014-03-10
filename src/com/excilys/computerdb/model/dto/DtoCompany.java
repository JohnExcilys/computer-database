package com.excilys.computerdb.model.dto;

public class DtoCompany {
	private Long id;
	private String name;

	// Getter et Setter
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

	// Methodes
	public DtoCompany() {
		this.id = (long) 0;
		this.name = "";
	}

	public DtoCompany(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "DtoCompany [id=" + id + ", name=" + name + "]";
	}
	
}
