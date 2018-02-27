package com.moekr.shadow.panel.web.controller.api;

import com.moekr.shadow.panel.logic.service.InvoiceService;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.InvoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class InvoiceController {
	private final InvoiceService invoiceService;

	@Autowired
	public InvoiceController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@PostMapping("/invoices")
	public Map<String, Object> create(@RequestBody @Valid InvoiceDTO invoiceDTO) {
		return ToolKit.assemblyResponseBody(invoiceService.create(invoiceDTO));
	}

	@GetMapping("/invoice/{id}")
	public Map<String, Object> retrieve(@PathVariable int id) {
		return ToolKit.assemblyResponseBody(invoiceService.retrieve(id));
	}
}
