package com.excilys.computerdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdb.model.Company;
import com.excilys.computerdb.model.Computer;
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

		// Add
		try {
			model.addAttribute("companies", serviceCompany.getCompanies());
			model.addAttribute("action", "./addComputer");
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
		model.addAttribute("formState", "Add");

		// Update
		if (update != null) {

			Computer computer;
			try {
				computer = serviceComputer.getComputer(update);
				model.addAttribute("computer", computer);
				model.addAttribute("formState", "Update");
				model.addAttribute("action",
						"addComputer?update=" + computer.getId());
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
			@RequestParam String name,
			@RequestParam(required = false) String introducedDate,
			@RequestParam(required = false) String discontinuedDate,
			@RequestParam(required = false) Long company) throws ServletException, IOException {

		if (update == null) {
			if (!name.equals("")
					&& isDateValid(introducedDate)
					&& isDateValid(discontinuedDate)
					&& company != 0) {
				try {
					// Add
					serviceComputer.saveComputer((long)0, name, introducedDate, discontinuedDate, new Company(company,""));
					model.addAttribute("formState", "Add");
					model.addAttribute("ajout",
							"L'ordinateur a été ajouté avec succés.");
					model.addAttribute("action", "./addComputer");

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
			if (!name.equals("")
					&& isDateValid(introducedDate)
					&& isDateValid(discontinuedDate)
					&& company != 0) {
				try {
					serviceComputer.saveComputer(update, name, introducedDate, discontinuedDate, new Company(company,""));
				} catch (NumberFormatException | SQLException | ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				model.addAttribute("formState", "Update");
				model.addAttribute("ajout",
						"L'ordinateur a été modifié avec succés.");
				model.addAttribute("action", "./addComputer");
			}
		}
	}

	public static boolean isDateValid(String date) {
		try {
			if (date != "") {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);
				df.parse(date);
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
		return false;
	}
}
