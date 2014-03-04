package com.excilys.computerdb.model;

public class Company {
	private int id;
	private String name;

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

	// Methodes
	public Company() {
		this.id = 0;
		this.name = "";
	}

	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
}
