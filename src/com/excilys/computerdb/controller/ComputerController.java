package com.excilys.computerdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdb.controller.validator.ComputerValidator;
import com.excilys.computerdb.dao.DAOCompany;
import com.excilys.computerdb.dao.DAOComputer;
import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.dto.DtoCompany;
import com.excilys.computerdb.model.dto.DtoComputer;
import com.excilys.computerdb.service.ServiceCompany;
import com.excilys.computerdb.service.ServiceComputer;

@Controller
@RequestMapping("/addComputer")
public class ComputerController {
	final Logger logger = LoggerFactory.getLogger(ComputerController.class);

	@Autowired
	ServiceCompany serviceCompany;
	@Autowired
	ServiceComputer serviceComputer;

	@Autowired
	private ComputerValidator computerValidator;

	public ServiceCompany getServiceCompany() {
		return serviceCompany;
	}

	public void setServiceCompany(ServiceCompany serviceCompany) {
		this.serviceCompany = serviceCompany;
	}

	public ServiceComputer getServiceComputer() {
		return serviceComputer;
	}

	public void setServiceComputer(ServiceComputer serviceComputer) {
		this.serviceComputer = serviceComputer;
	}

	public ComputerValidator getComputerValidator() {
		return computerValidator;
	}

	public void setComputerValidator(ComputerValidator computerValidator) {
		this.computerValidator = computerValidator;
	}

	public ComputerController() {
	}

	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(ModelMap model,
			@RequestParam(required = false) BindingResult result,
			@RequestParam(required = false) Long update,
			@RequestParam(required = false) Long delete)
			throws ServletException, IOException, NamingException {

		DtoComputer cDTO = new DtoComputer();
		model.addAttribute("cDTO", cDTO);

		// Add
		try {
			List<Company> companies = new ArrayList<Company>();
			List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();

			companies = serviceCompany.getCompanies();
			for (Company c : companies) {
				companiesDto.add(DAOCompany.createDTO(c));
			}
			model.addAttribute("companies", companiesDto);
			model.addAttribute("action", "./addComputer");
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
		model.addAttribute("formState", "Add");

		// Update
		if (update != null) {

			try {
				cDTO = DAOComputer.createDTO(serviceComputer
						.getComputer(update));
				model.addAttribute("cDTO", cDTO);
				model.addAttribute("formState", "Update");
				model.addAttribute("action",
						"./addComputer?update=" + cDTO.getId());
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Delete
		if (delete != null) {
			try {
				serviceComputer.deleteComputer(delete);
				model.addAttribute("ajout", "label.computer.delete.success");
			} catch (NumberFormatException | NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(ModelMap model,
			@RequestParam(required = false) Long update,
			@ModelAttribute("cDTO") @Valid DtoComputer cDTO,
			BindingResult result) throws ServletException, IOException,
			NamingException, SQLException {
		if (!result.hasErrors()) {
			if (update == null) {
				try {
					// Add
					System.out.println("Add controller");
					serviceComputer.saveComputer(cDTO);

					List<Company> companies = new ArrayList<Company>();
					List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();
					companies = serviceCompany.getCompanies();
					for (Company c : companies) {
						companiesDto.add(DAOCompany.createDTO(c));
					}
					model.addAttribute("companies", companiesDto);
				} catch (NumberFormatException | SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// Update
				try {
					serviceComputer.saveComputer(cDTO);
				} catch (NumberFormatException | SQLException | ParseException e) {
					e.printStackTrace();
				}
			}
			if (cDTO.getId() != null && cDTO.getId() != 0) {
				model.addAttribute("formState", "Update");
				model.addAttribute("ajout", "label.computer.update.success");
				List<Company> companies = new ArrayList<Company>();
				List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();

				companies = serviceCompany.getCompanies();
				for (Company c : companies) {
					companiesDto.add(DAOCompany.createDTO(c));
				}
				model.addAttribute("companies", companiesDto);
			} else {
				model.addAttribute("formState", "Add");
				model.addAttribute("ajout", "label.computer.add.success");
			}
			model.addAttribute("cDTO", cDTO);
		} else {
			// Affichage des erreurs via <form:errors/> dans la jsp
			List<Company> companies = new ArrayList<Company>();
			List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();
			companies = serviceCompany.getCompanies();
			for (Company c : companies) {
				companiesDto.add(DAOCompany.createDTO(c));
			}
			model.addAttribute("companies", companiesDto);
			if (cDTO.getId() != null && cDTO.getId() != 0) {
				model.addAttribute("formState", "Update");
				model.addAttribute("action",
						"./addComputer?update=" + cDTO.getId());
			} else {
				model.addAttribute("formState", "Add");
			}
		}

		return "addComputer";
	}

	@InitBinder
	private void binder(WebDataBinder binder) {
		binder.addValidators(computerValidator);
	}
}
