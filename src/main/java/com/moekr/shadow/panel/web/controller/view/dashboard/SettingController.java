package com.moekr.shadow.panel.web.controller.view.dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/setting")
public class SettingController {
	@GetMapping({"/", "/index.html"})
	public String index() {
		return "dashboard/setting/index";
	}
}
