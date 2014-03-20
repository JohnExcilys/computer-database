package com.excilys.computerdb.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdb.binding.DtoCompany;
import com.excilys.computerdb.binding.DtoComputer;
import com.excilys.computerdb.controller.validator.ComputerValidator;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ServiceCompany;
import com.excilys.computerdb.service.ServiceComputer;

@Controller
@RequestMapping("/computer")
public class ComputerController implements MessageSourceAware {
	final Logger logger = LoggerFactory.getLogger(ComputerController.class);

	@Autowired
	ServiceCompany serviceCompany;
	@Autowired
	ServiceComputer serviceComputer;

	@Autowired
	private ComputerValidator computerValidator;
	private MessageSource message;

	public ComputerController() {
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	protected String deleteComputer(ModelMap model,
			@RequestParam(required = false) Long delete)
			throws NamingException, SQLException {

		DtoComputer cDTO = new DtoComputer();
		model.addAttribute("cDTO", cDTO);

		if (delete != null) {
			serviceComputer.deleteComputer(delete);
			model.addAttribute("ajout", "label.computer.delete.success");
		}
		return "addComputer";
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	protected String updateComputerGet(ModelMap model,
			@RequestParam(required = false) Long update)
			throws NamingException, SQLException {

		DtoComputer cDTO = new DtoComputer();
		model.addAttribute("cDTO", cDTO);

		if (update != null) {
			StringBuilder sb = new StringBuilder();
			Computer c = serviceComputer.getComputer(update);
			cDTO = DtoComputer.createDTO(c);
			List<Company> companies = new ArrayList<Company>();
			List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();

			companies = serviceCompany.getCompanies();
			for (Company cp : companies) {
				companiesDto.add(DtoCompany.createDTO(cp));
			}
			model.addAttribute("companies", companiesDto);
			model.addAttribute("cDTO", cDTO);
			model.addAttribute("formState", "Update");
			model.addAttribute("action",
					sb.append("./update?update=").append(cDTO.getId())
							.toString());
		}

		return "addComputer";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	protected String updateComputerPost(ModelMap model,
			@RequestParam(required = false) Long update,
			@ModelAttribute("cDTO") @Valid DtoComputer cDTO,
			BindingResult result) throws NoSuchMessageException, SQLException,
			ParseException, NamingException {

		if (!result.hasErrors()) {

			serviceComputer.saveComputer(DtoComputer.createComputerFromDto(
					cDTO, message.getMessage("label.datePatternUsed", null,
							LocaleContextHolder.getLocale())));

			model.addAttribute("formState", "Update");
			model.addAttribute("ajout", "label.computer.update.success");
			List<Company> companies = new ArrayList<Company>();
			List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();

			companies = serviceCompany.getCompanies();
			for (Company c : companies) {
				companiesDto.add(DtoCompany.createDTO(c));
			}
			model.addAttribute("companies", companiesDto);
		} else {
			StringBuilder sb = new StringBuilder();
			List<Company> companies = new ArrayList<Company>();
			List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();
			companies = serviceCompany.getCompanies();
			for (Company c : companies) {
				companiesDto.add(DtoCompany.createDTO(c));
			}
			model.addAttribute("companies", companiesDto);
			model.addAttribute("formState", "Update");
			model.addAttribute("action",
					sb.append("./update?update=").append(cDTO.getId())
							.toString());
		}

		return "addComputer";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	protected String addComputerGet(ModelMap model) throws SQLException,
			NamingException {

		DtoComputer cDTO = new DtoComputer();
		model.addAttribute("cDTO", cDTO);

		List<Company> companies = new ArrayList<Company>();
		List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();

		companies = serviceCompany.getCompanies();
		for (Company c : companies) {
			companiesDto.add(DtoCompany.createDTO(c));
		}
		model.addAttribute("companies", companiesDto);
		model.addAttribute("action", "./add");

		model.addAttribute("formState", "Add");

		return "addComputer";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	protected String addComputerPost(ModelMap model,
			@ModelAttribute("cDTO") @Valid DtoComputer cDTO,
			BindingResult result) throws NoSuchMessageException, SQLException,
			ParseException, NamingException {

		if (!result.hasErrors()) {
			serviceComputer.saveComputer(DtoComputer.createComputerFromDto(
					cDTO, message.getMessage("label.datePatternUsed", null,
							LocaleContextHolder.getLocale())));

			List<Company> companies = new ArrayList<Company>();
			List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();
			companies = serviceCompany.getCompanies();
			for (Company c : companies) {
				companiesDto.add(DtoCompany.createDTO(c));
			}
			model.addAttribute("companies", companiesDto);
			model.addAttribute("formState", "Add");
			model.addAttribute("ajout", "label.computer.add.success");
		} else {
			List<Company> companies = new ArrayList<Company>();
			List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();
			companies = serviceCompany.getCompanies();
			for (Company c : companies) {
				companiesDto.add(DtoCompany.createDTO(c));
			}
			model.addAttribute("companies", companiesDto);
			model.addAttribute("formState", "Add");
		}

		return "addComputer";
	}

	@InitBinder
	private void binder(WebDataBinder binder) {
		binder.addValidators(computerValidator);
	}

	@Override
	public void setMessageSource(MessageSource message) {
		this.message = message;

	}
}
