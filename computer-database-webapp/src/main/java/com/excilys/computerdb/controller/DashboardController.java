package com.excilys.computerdb.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdb.model.Computer;
import com.excilys.computerdb.service.ServiceComputer;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	@Autowired
	private ServiceComputer serviceComputer;

	@RequestMapping(method = RequestMethod.GET)
	private String getDashboard(ModelMap model, Pageable pageable,
			@RequestParam(required = false) String search,
			@RequestParam(required = false) String error,
			@RequestParam(required = false) String message) {
		Page<Computer> page = null;
		if (search == null) {
			page = serviceComputer.findAll(pageable);
		} else {
			model.addAttribute("search", search);
			page = serviceComputer.findAllByName(search, pageable);
		}
		if (page.getSort() != null) {
			Order order = page.getSort().iterator().next();
			model.addAttribute("order", order.getProperty());
			model.addAttribute("dir", order.getDirection().name());
		}
		model.addAttribute("page", page);
		if (error != null) {
			model.addAttribute("error", error);
		}
		if (message != null) {
			List<String> messages = new ArrayList<>();
			messages.add(message);
			model.addAttribute("message", messages);
		}

		return "dashboard";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	private String deleteComputer(ModelMap model, @RequestParam long id) {
		List<String> message = new ArrayList<>();

		serviceComputer.delete(id);
		model.addAttribute("error", false);
		message.add("label.computer.delete.success");

		model.addAttribute("message", message);

		Page<Computer> page = serviceComputer.findAll(new PageRequest(0, 10));
		model.addAttribute("page", page);
		return "redirect:/dashboard";
	}
}
