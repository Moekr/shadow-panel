package com.moekr.shadow.panel.web.controller.api;

import com.moekr.shadow.panel.logic.service.PlanService;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.ChangePlanRequest;
import com.moekr.shadow.panel.web.dto.PlanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PlanController {
	private final PlanService planService;

	@Autowired
	public PlanController(PlanService planService) {
		this.planService = planService;
	}

	@PostMapping("/plans")
	public Map<String, Object> create(@RequestBody @Valid PlanDTO planDTO) {
		return ToolKit.assemblyResponseBody(planService.create(planDTO));
	}

	@GetMapping("/plans")
	public Map<String, Object> retrieve() {
		return ToolKit.assemblyResponseBody(planService.retrieve());
	}

	@GetMapping("/plan/{id}")
	public Map<String, Object> retrieve(@PathVariable int id) {
		return ToolKit.assemblyResponseBody(planService.retrieve(id));
	}

	@PutMapping("/plan/{id}")
	public Map<String, Object> update(@PathVariable int id, @RequestBody @Valid PlanDTO planDTO) {
		return ToolKit.assemblyResponseBody(planService.update(id, planDTO));
	}

	@PostMapping("/plan/change")
	public Map<String, Object> changePlan(@RequestBody @Valid ChangePlanRequest request) {
		planService.changePlan(request);
		return ToolKit.emptyResponseBody();
	}
}
