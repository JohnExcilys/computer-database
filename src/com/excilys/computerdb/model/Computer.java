package com.excilys.computerdb.model;

import java.util.Date;

public class Computer {
	private int _id;
	private String _name;
	private Date _introduced;
	private Date _discontinued;
	private int _companyId;

	// Getter et Setter
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public Date get_introduced() {
		return _introduced;
	}

	public void set_introduced(Date _introduced) {
		this._introduced = _introduced;
	}

	public Date get_discontinued() {
		return _discontinued;
	}

	public void set_discontinued(Date _discontinued) {
		this._discontinued = _discontinued;
	}

	public int get_companyId() {
		return _companyId;
	}

	public void set_companyId(int _companyId) {
		this._companyId = _companyId;
	}

	// Methodes
	public Computer() {
		this._id = 0;
		this._name = "";
		this._introduced = new Date();
		this._discontinued = new Date();
		this._companyId = 0;
	}

	public Computer(int id, String name, Date introduced, Date discontinued,
			int companyId) {
		this._id = id;
		this._name = name;
		this._introduced = introduced;
		this._discontinued = discontinued;
		this._companyId = companyId;
	}

}
