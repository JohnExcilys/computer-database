package com.excilys.computerdb.model;

import java.util.List;

public class WrapperListCount {
	private List<?> list;
	private int count;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public WrapperListCount() {
		super();
	}

	public WrapperListCount(List<?> list, int count) {
		super();
		this.list = list;
		this.count = count;
	}
}
