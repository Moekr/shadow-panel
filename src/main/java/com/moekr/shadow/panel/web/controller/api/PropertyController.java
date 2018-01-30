package com.moekr.shadow.panel.web.controller.api;

import com.moekr.shadow.panel.logic.service.PropertyService;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.PropertyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PropertyController {
	private final PropertyService propertyService;

	@Autowired
	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@GetMapping("/properties")
	public Map<String, Object> retrieve() {
		return ToolKit.assemblyResponseBody(propertyService.retrieve());
	}

	@GetMapping("/property/{name}")
	public Map<String, Object> retrieve(@PathVariable String name) {
		return ToolKit.assemblyResponseBody(propertyService.retrieve(name));
	}

	@PutMapping("/property/{name}")
	public Map<String, Object> update(@PathVariable String name, @RequestBody @Valid PropertyDTO propertyDTO) {
		return ToolKit.assemblyResponseBody(propertyService.update(name, propertyDTO));
	}
}
