package com.moekr.shadow.panel.web.controller.view.dashboard;

import com.moekr.shadow.panel.logic.service.NodeService;
import com.moekr.shadow.panel.logic.vo.model.NodeModel;
import com.moekr.shadow.panel.util.ServiceException;
import com.moekr.shadow.panel.web.dto.form.CreateNodeForm;
import com.moekr.shadow.panel.web.dto.form.NodeActionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard/node")
public class NodeManagementController {
	private final NodeService nodeService;

	@Autowired
	public NodeManagementController(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	@GetMapping({"/", "/index.html"})
	public String index(Model model) {
		model.addAttribute("nodes", nodeService.findAll().stream().collect(Collectors.groupingBy(NodeModel::getRegion)));
		return "dashboard/node/index";
	}

	@PostMapping({"/", "/index.html"})
	public String index(@ModelAttribute @Valid NodeActionForm form, Errors errors, Model model) {
		if (errors.hasFieldErrors()) {
			model.addAttribute("error", Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
		} else {
			try {
				nodeService.action(form);
				model.addAttribute("success", "操作成功！");
			} catch (ServiceException e) {
				model.addAttribute("error", e.getMessage());
			}
		}
		model.addAttribute("nodes", nodeService.findAll().stream().collect(Collectors.groupingBy(NodeModel::getRegion)));
		return "dashboard/node/index";
	}

	@GetMapping("/create.html")
	public String create() {
		return "dashboard/node/create";
	}

	@PostMapping("/create.html")
	public String create(@ModelAttribute @Valid CreateNodeForm form, Errors errors, Model model) {
		if (errors.hasFieldErrors()) {
			model.addAttribute("error", Objects.requireNonNull(errors.getFieldError()).getDefaultMessage());
		} else {
			try {
				nodeService.create(form);
				model.addAttribute("success", "创建成功，页面将在5秒后跳转！");
			} catch (ServiceException e) {
				model.addAttribute("error", e.getMessage());
			}
		}
		return "dashboard/node/create";
	}
}
