package com.excilys.computerdb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdb.dao.DAOComputer;
import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.model.ComputerOrder;
import com.excilys.computerdb.model.dto.DtoComputer;
import com.excilys.computerdb.service.ServiceComputer;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	@Autowired
	private ServiceComputer serviceComputer;

	@RequestMapping(method = RequestMethod.GET)
	private String doGet(ModelMap model,
			@RequestParam(defaultValue = "1", required = false) int page,
			@RequestParam(required = false) String order,
			@RequestParam(required = false) String search) {

		int numberOfPage = 1;

		ComputerOrder computerOrder = getOrder(order, model);
		Map<String, String> queryParameters = new HashMap<>();
		List<Computer> computers = new ArrayList<Computer>();
		List<DtoComputer> computersDto = new ArrayList<DtoComputer>();
		if (computerOrder != null) {
			queryParameters.put("order", computerOrder.getUrlParameter());
		}
		try {
			int numberOfResult = 0;
			if (search == null) {
				numberOfResult = serviceComputer.count(null);
				numberOfPage = (numberOfResult / 10) + 1;
				if (page < 1 || page > numberOfPage) {
					page = 1;
				}

				computers = serviceComputer.findAllByCreteria(null,
						computerOrder, (page - 1) * 10, 10);
			} else {
				numberOfResult = serviceComputer.count(search);
				numberOfPage = (numberOfResult / 10) + 1;
				queryParameters.put("search", search);
				if (page < 1 || page > numberOfPage) {
					page = 1;
				}

				computers = serviceComputer.findAllByCreteria(search,
						computerOrder, (page - 1) * 10, 10);
			}
			for (Computer c : computers) {
				computersDto.add(DAOComputer.createDTO(c));
			}
			model.addAttribute("computers", computersDto);
			model.addAttribute("current_page", page);
			model.addAttribute("last_page", numberOfPage);
			model.addAttribute("number_of_result", numberOfResult);
		} catch (SQLException e) {
			logger.error("Erreur lors de l'accès à la liste", e);
		}
		model.addAttribute("query_parameters", queryParameters);
		return "dashboard";
	}

	@RequestMapping(method = RequestMethod.POST)
	private String doPost(ModelMap model) throws IOException, NamingException {

		return "dashboard";
	}

	public ComputerOrder getOrder(String value, ModelMap model) {
		ComputerOrder order = null;
		if (value != null) {
			switch (value) {
			case "orderByNameAsc":
				order = ComputerOrder.ORDER_BY_NAME_ASC;
				break;
			case "orderByNameDesc":
				order = ComputerOrder.ORDER_BY_NAME_DESC;
				break;
			case "orderByIntroducedDateAsc":
				order = ComputerOrder.ORDER_BY_INTRODUCED_DATE_ASC;
				break;
			case "orderByIntroducedDateDesc":
				order = ComputerOrder.ORDER_BY_INTRODUCED_DATE_DESC;
				break;
			case "orderByDiscontinuedDateAsc":
				order = ComputerOrder.ORDER_BY_DISCONTINUED_DATE_ASC;
				break;
			case "orderByDiscontinuedDateDesc":
				order = ComputerOrder.ORDER_BY_DISCONTINUED_DATE_DESC;
				break;
			case "orderByCompanyNameAsc":
				order = ComputerOrder.ORDER_BY_COMPANY_NAME_ASC;
				break;
			case "orderByCompanyNameDesc":
				order = ComputerOrder.ORDER_BY_COMPANY_NAME_DESC;
				break;
			default:
				break;
			}
		}
		model.addAttribute("order", order);
		return order;
	}

	public ServiceComputer getComputerService() {
		return serviceComputer;
	}

	public void setComputerService(ServiceComputer computerService) {
		this.serviceComputer = computerService;
	}

	// Test de la page d'erreur personnalisée
	@RequestMapping(value = "/test")
	public void test() throws IOException {

		// On envoie une exception au ExceptionHandler
		if (true) {
			throw new IOException("this is io exception");
		}

	}
}
