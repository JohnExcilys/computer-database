package com.excilys.computerdb.controller.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.computerdb.binding.DtoComputer;

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

		// // Tests à améliorer -- Plante l'appli dans certains cas (date en
		// l'an
		// // 1000)
		// if (obj.getIntroduced() != null) {
		// if (obj.getIntroduced().getYear() < 1000
		// || obj.getIntroduced().getMonthOfYear() < 1
		// || obj.getIntroduced().getDayOfMonth() < 1) {
		// e.rejectValue("introduced", "label.badDate", "Invalid Date");
		// }
		// }
		//
		// if (obj.getDiscontinued() != null) {
		// if (obj.getDiscontinued().getYear() < 1000
		// || obj.getDiscontinued().getMonthOfYear() < 1
		// || obj.getDiscontinued().getDayOfMonth() < 1) {
		// e.rejectValue("discontinued", "label.badDate", "Invalid Date");
		// }
		// }

	}
}