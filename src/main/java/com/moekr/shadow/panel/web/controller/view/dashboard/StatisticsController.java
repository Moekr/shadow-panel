package com.moekr.shadow.panel.web.controller.view.dashboard;

import com.moekr.shadow.panel.logic.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/statistics")
public class StatisticsController {
	private final RecordService recordService;

	public StatisticsController(RecordService recordService) {
		this.recordService = recordService;
	}

	@GetMapping({"/", "/index.html"})
	public String index(Model model) {
		model.addAttribute("dayHourChartData", recordService.hourData(1));
		model.addAttribute("monthDayChartData", recordService.dayData(30));
		model.addAttribute("monthHourChartData", recordService.hourData(30));
		return "dashboard/statistics/index";
	}

}
