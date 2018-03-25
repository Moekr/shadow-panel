package com.moekr.shadow.panel.web.controller.view.dashboard;

import com.moekr.shadow.panel.logic.service.ClientService;
import com.moekr.shadow.panel.logic.vo.model.ClientModel;
import com.moekr.shadow.panel.util.ServiceException;
import com.moekr.shadow.panel.web.dto.form.CreateClientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/client")
public class ClientManagementController {
	private final ClientService clientService;

	@Autowired
	public ClientManagementController(ClientService clientService) {
		this.clientService = clientService;
	}

	@GetMapping({"/", "/index.html"})
	public String index(Model model) {
		model.addAttribute("clients", clientService.findAll().stream().collect(Collectors.groupingBy(ClientModel::getPlatform)));
		return "dashboard/client/index";
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@PostMapping({"/", "/index.html"})
	public String index(@RequestParam Optional<Integer> id, Model model) {
		if (!id.isPresent()) {
			return "redirect:/dashboard/client/";
		}
		try {
			clientService.delete(id.get());
			model.addAttribute("success", "操作成功！");
		} catch (ServiceException e) {
			model.addAttribute("error", e.getMessage());
		}
		model.addAttribute("clients", clientService.findAll().stream().collect(Collectors.groupingBy(ClientModel::getPlatform)));
		return "dashboard/client/index";
	}

	@GetMapping("/create.html")
	public String create() {
		return "dashboard/client/create";
	}

	@PostMapping("/create.html")
	public String create(@ModelAttribute @Valid CreateClientForm form, Errors errors, Model model) {
		if (errors.hasFieldErrors()) {
			model.addAttribute("error", Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
		} else {
			try {
				clientService.create(form);
				model.addAttribute("success", "增加成功，页面将在5秒后跳转！");
			} catch (ServiceException e) {
				model.addAttribute("error", e.getMessage());
			}
		}
		return "dashboard/client/create";
	}
}
