package com.excilys.computerdb.model;

public class Company {
	private int _id;
	private String _name;
	
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
	
	//Methodes
	public Company() {
		this._id = 0;
		this._name = "";
	}
	
	public Company(int id, String name){
		this._id = id;
		this._name = name;
	}
	
	
}
