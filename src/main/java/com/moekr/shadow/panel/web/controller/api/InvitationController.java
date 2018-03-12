package com.moekr.shadow.panel.web.controller.api;

import com.moekr.shadow.panel.logic.service.InvitationService;
import com.moekr.shadow.panel.util.ToolKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class InvitationController {
	private final InvitationService invitationService;

	@Autowired
	public InvitationController(InvitationService invitationService) {
		this.invitationService = invitationService;
	}

	@PostMapping("/invitations")
	public Map<String, Object> create() {
		return ToolKit.assemblyResponseBody(invitationService.create());
	}

	@GetMapping("/invitations")
	public Map<String, Object> retrieve() {
		return ToolKit.assemblyResponseBody(invitationService.retrieve());
	}

	@GetMapping("/invitation/{id}")
	public Map<String, Object> retrieve(@PathVariable int id) {
		return ToolKit.assemblyResponseBody(invitationService.retrieve(id));
	}

	@DeleteMapping("/invitation/{id}")
	public Map<String, Object> delete(@PathVariable int id) {
		invitationService.delete(id);
		return ToolKit.emptyResponseBody();
	}
}
