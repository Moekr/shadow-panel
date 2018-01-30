package com.moekr.shadow.panel.web.controller.api;

import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public Map<String, Object> create(@RequestBody @Valid UserDTO userDTO) {
		return ToolKit.assemblyResponseBody(userService.create(userDTO));
	}

	@GetMapping("/users")
	public Map<String, Object> retrieve() {
		return ToolKit.assemblyResponseBody(userService.retrieve());
	}

	@GetMapping("/user/{id}")
	public Map<String, Object> retrieve(@PathVariable int id) {
		return ToolKit.assemblyResponseBody(userService.retrieve(id));
	}

	@PutMapping("/user/{id}")
	public Map<String, Object> update(@PathVariable int id, @RequestBody @Valid UserDTO userDTO) {
		return ToolKit.assemblyResponseBody(userService.update(id, userDTO));
	}

	@DeleteMapping("/user/{id}")
	public Map<String, Object> delete(@PathVariable int id) {
		userService.delete(id);
		return ToolKit.emptyResponseBody();
	}
}
