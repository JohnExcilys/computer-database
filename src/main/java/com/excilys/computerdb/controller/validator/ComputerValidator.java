package com.excilys.computerdb.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.computerdb.model.dto.DtoComputer;

@Component
public class ComputerValidator implements Validator {
	final Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return DtoComputer.class.equals(clazz);
	}

	@Override
	public void validate(Object ob, Errors e) {
		DtoComputer obj = (DtoComputer) ob;
		if (obj.getName() == null || obj.getName().trim().isEmpty()) {
			e.rejectValue("name", "label.invName", "Invalid name");
		}

		if (obj.getCompanyId() <= 0) {
			e.rejectValue("companyId", "label.invCompany", "Invalid company");
		}

		if (obj.getIntroduced() != null) {
			if (obj.getIntroduced().getYear() < 1000
					|| obj.getIntroduced().getMonthOfYear() < 0
					|| obj.getIntroduced().getDayOfMonth() < 0) {
				e.rejectValue("introduced", "label.badDate", "Invalid Date");
			}
		}

		if (obj.getDiscontinued() != null) {
			if (obj.getDiscontinued().getYear() < 1000
					|| obj.getDiscontinued().getMonthOfYear() < 0
					|| obj.getDiscontinued().getDayOfMonth() < 0) {
				e.rejectValue("discontinued", "label.badDate", "Invalid Date");
			}
		}

	}
}
