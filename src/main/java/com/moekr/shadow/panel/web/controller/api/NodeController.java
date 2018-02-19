package com.moekr.shadow.panel.web.controller.api;

import com.moekr.shadow.panel.logic.service.NodeService;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.NodeDTO;
import com.moekr.shadow.panel.web.dto.PortDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NodeController {
	private final NodeService nodeService;

	@Autowired
	public NodeController(NodeService nodeService) {
		this.nodeService = nodeService;
	}

	@PostMapping("/nodes")
	public Map<String, Object> create(@RequestBody @Valid NodeDTO nodeDTO) {
		return ToolKit.assemblyResponseBody(nodeService.create(nodeDTO));
	}

	@GetMapping("/nodes")
	public Map<String, Object> retrieve() {
		return ToolKit.assemblyResponseBody(nodeService.retrieve());
	}

	@GetMapping("/node/{id}")
	public Map<String, Object> retrieve(@PathVariable int id) {
		return ToolKit.assemblyResponseBody(nodeService.retrieve(id));
	}

	@PutMapping("/node/{id}")
	public Map<String, Object> update(@PathVariable int id, @RequestBody @Valid NodeDTO nodeDTO) {
		return ToolKit.assemblyResponseBody(nodeService.update(id, nodeDTO));
	}

	@DeleteMapping("/node/{id}")
	public Map<String, Object> delete(@PathVariable int id) {
		nodeService.delete(id);
		return ToolKit.emptyResponseBody();
	}

	@PostMapping("/node/{nid}/ports")
	public Map<String, Object> createPort(@PathVariable int nid, @RequestBody @Valid PortDTO portDTO) {
		return ToolKit.assemblyResponseBody(nodeService.createPort(nid, portDTO));
	}

	@PutMapping("/node/{nid}/port/{pid}")
	public Map<String, Object> updatePort(@PathVariable int nid, @PathVariable int pid, @RequestBody @Valid PortDTO portDTO) {
		return ToolKit.assemblyResponseBody(nodeService.updatePort(nid, pid, portDTO));
	}

	@DeleteMapping("/node/{nid}/port/{pid}")
	public Map<String, Object> deletePort(@PathVariable int nid, @PathVariable int pid) {
		return ToolKit.assemblyResponseBody(nodeService.deletePort(nid, pid));
	}

	@PostMapping("/node/{id}/start")
	public Map<String, Object> start(@PathVariable int id) {
		nodeService.start(id);
		return ToolKit.emptyResponseBody();
	}

	@PostMapping("/node/{id}/stop")
	public Map<String, Object> stop(@PathVariable int id) {
		nodeService.stop(id);
		return ToolKit.emptyResponseBody();
	}

	@PostMapping("/node/{id}/restart")
	public Map<String, Object> restart(@PathVariable int id) {
		nodeService.restart(id);
		return ToolKit.emptyResponseBody();
	}
}
