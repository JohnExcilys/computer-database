package com.excilys.computerdb.binding;

import com.excilys.computerdb.model.Company;

public class DtoCompany {
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

	public DtoCompany() {
		this.id = (long) 0;
		this.name = "";
	}

	public DtoCompany(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static DtoCompany createDTO(Company c) {
		DtoCompany cDto = null;
		if (c != null) {
			cDto = new DtoCompany();
			cDto.setid(c.getid());
			cDto.setname(c.getname());
		}
		return cDto;
	}

	public static Company createCompanyFromDto(DtoCompany cDto) {
		Company c = null;
		if (cDto != null) {
			c = new Company();
			c.setid(cDto.getid());
			c.setname(cDto.getname());
		}
		return c;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		return sb.append("DtoCompany [id=").append(id).append(", name=")
				.append(name).append("]").toString();
	}

}
