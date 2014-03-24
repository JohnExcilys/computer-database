package com.excilys.computerdb.binding;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.excilys.computerdb.model.Computer;

public class DtoComputer {
	private Long id;
	private String name;
	@CheckDate()
	private String introduced;
	@CheckDate()
	private String discontinued;
	private Long companyId;
	private String companyName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public DtoComputer(Long id, String name, String introduced,
			String discontinued, Long companyId, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}

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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		return sb.append("DtoComputer [id=").append(id).append(", name=")
				.append(name).append(", introduced=").append(introduced)
				.append(", discontinued=").append(discontinued)
				.append(", companyId=").append(companyId)
				.append(", companyName=").append(companyName).append("]")
				.toString();
	}

	public DtoComputer() {
	}
}
