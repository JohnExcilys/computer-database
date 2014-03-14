package com.excilys.computerdb.model.dto;

import org.joda.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class DtoComputer {
	private Long id;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate introduced;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate discontinued;
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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
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

	public DtoComputer(Long id, String name, LocalDate introduced,
			LocalDate discontinued, Long companyId, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "DtoComputer [id=" + id + ", name=" + name + ", introduced="
				+ introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + ", companyName=" + companyName
				+ "]";
	}

	public DtoComputer() {
		// TODO Auto-generated constructor stub
	}
}
