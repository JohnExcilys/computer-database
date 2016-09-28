package com.excilys.computerdb.binding.mapper;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.excilys.computerdb.binding.DtoComputer;
import com.excilys.computerdb.model.Computer;

public class DtoComputerMapper {
	public static DtoComputer createDTO(Computer c) {
		DtoComputer cDto = null;
		if (c != null) {
			cDto = new DtoComputer();
			cDto.setId(c.getId());
			cDto.setName(c.getName());
			if (c.getIntroduced() != null) {
				cDto.setIntroduced(c.getIntroduced().toString());
			} else {
				cDto.setIntroduced("");
			}
			if (c.getDiscontinued() != null) {
				cDto.setDiscontinued(c.getDiscontinued().toString());
			} else {
				cDto.setDiscontinued("");
			}
			if (c.getCompany() != null) {
				cDto.setCompanyId(c.getCompany().getid());
				cDto.setCompanyName(c.getCompany().getname());
			}
		}
		return cDto;
	}

	public static Computer createComputerFromDto(DtoComputer dtoC,
			String pattern) {
		Computer c = null;
		DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
		if (dtoC != null) {
			c = new Computer();
			c.setId(dtoC.getId());
			c.setName(dtoC.getName());
			c.setIntroduced(dtf.parseLocalDate(dtoC.getIntroduced()));
			c.setDiscontinued(dtf.parseLocalDate(dtoC.getDiscontinued()));
			if (dtoC.getCompanyId() != null) {
				c.getCompany().setid(dtoC.getCompanyId());
			} else {
				c.getCompany().setid(0l);
			}
			c.getCompany().setname(dtoC.getName());
		}
		return c;
	}
}
