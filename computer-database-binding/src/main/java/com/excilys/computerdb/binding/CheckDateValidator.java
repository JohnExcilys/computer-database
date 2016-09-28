package com.excilys.computerdb.binding;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;

public class CheckDateValidator implements
		ConstraintValidator<CheckDate, String>, MessageSourceAware {

	private MessageSource message;

	@Override
	public void initialize(CheckDate constraintAnnotation) {

	}

	@Override
	public boolean isValid(String date,
			ConstraintValidatorContext constraintContext) {
		if (!date.matches(message.getMessage("date.regexp", null, "yyyy-MM-dd",
				LocaleContextHolder.getLocale()))) {
			return false;
		}
		return true;
	}

	@Override
	public void setMessageSource(MessageSource message) {
		this.message = message;
	}

}
