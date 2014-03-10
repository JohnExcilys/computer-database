package com.excilys.computerdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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

	public ComputerController() {
	}

	@RequestMapping(method = RequestMethod.GET)
	protected void doGet(ModelMap model,
			@RequestParam(required = false) Long update,
			@RequestParam(required = false) Long delete)
			throws ServletException, IOException {

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
			} catch (NumberFormatException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Delete
		if (delete != null) {
			try {
				serviceComputer.deleteComputer(delete);
			} catch (NumberFormatException | NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	protected void doPost(ModelMap model,
			@RequestParam(required = false) Long update,
			@ModelAttribute DtoComputer cDTO)
			throws ServletException, IOException, NamingException {
		
		if (update == null) {
			if (!cDTO.getName().equals("") &&  cDTO.getCompanyId() != 0) {
				try {
					// Add
					serviceComputer.saveComputer(cDTO);
					model.addAttribute("formState", "Add");
					model.addAttribute("ajout",
							"L'ordinateur a été ajouté avec succés.");
					List<Company> companies = new ArrayList<Company>();
					List<DtoCompany> companiesDto = new ArrayList<DtoCompany>();
					companies = serviceCompany.getCompanies();
					for (Company c : companies) {
						companiesDto.add(DAOCompany.createDTO(c));
					}
					System.out.println(companiesDto);
					model.addAttribute("companies", companiesDto);
				} catch (NumberFormatException | SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				model.addAttribute("formState", "Add");
				model.addAttribute("ajout",
						"L'ordinateur n'a pas été ajouté. Certains champs doivent être incorrects.");
			}
		} else {
			// Update
			if (!cDTO.getName().equals("") && cDTO.getCompanyId() != 0) {
				try {
					serviceComputer.saveComputer(cDTO);
				} catch (NumberFormatException | SQLException | ParseException e) {
					e.printStackTrace();
				}
				model.addAttribute("formState", "Update");
				model.addAttribute("ajout",
						"L'ordinateur a été modifié avec succés.");
			}
		}
		model.addAttribute("cDTO", cDTO);
	}
	
	@InitBinder
	private void dateBinder(WebDataBinder binder) {
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
		//binder.addValidators(computerValidator);
	}
}
