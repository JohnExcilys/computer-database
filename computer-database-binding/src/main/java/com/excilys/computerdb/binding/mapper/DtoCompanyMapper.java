package com.excilys.computerdb.binding.mapper;

import com.excilys.computerdb.binding.DtoCompany;
import com.excilys.computerdb.model.Company;

public class DtoCompanyMapper {

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
}
