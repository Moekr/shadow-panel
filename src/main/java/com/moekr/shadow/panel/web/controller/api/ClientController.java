package com.moekr.shadow.panel.web.controller.api;

import com.moekr.shadow.panel.logic.service.ClientService;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClientController {
	private final ClientService clientService;

	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@PostMapping("/clients")
	public Map<String, Object> create(@RequestBody @Valid ClientDTO clientDTO) {
		return ToolKit.assemblyResponseBody(clientService.create(clientDTO));
	}

	@GetMapping("/clients")
	public Map<String, Object> retrieve() {
		return ToolKit.assemblyResponseBody(clientService.retrieve());
	}

	@GetMapping("/client/{id}")
	public Map<String, Object> retrieve(@PathVariable int id) {
		return ToolKit.assemblyResponseBody(clientService.retrieve(id));
	}

	@PutMapping("/client/{id}")
	public Map<String, Object> update(@PathVariable int id, @RequestBody @Valid ClientDTO clientDTO) {
		return ToolKit.assemblyResponseBody(clientService.update(id, clientDTO));
	}

	@DeleteMapping("/client/{id}")
	public Map<String, Object> delete(@PathVariable int id) {
		clientService.delete(id);
		return ToolKit.emptyResponseBody();
	}
}
