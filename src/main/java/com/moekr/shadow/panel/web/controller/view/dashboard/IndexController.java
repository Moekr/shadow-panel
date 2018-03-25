package com.moekr.shadow.panel.web.controller.view.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class IndexController {

	@Autowired
	public IndexController() {

	}

	@GetMapping({"/", "/index.html"})
	public String index(Model model) {
		return "dashboard/index";
	}
}
