package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.NodeVO;
import com.moekr.shadow.panel.web.dto.NodeDTO;

import java.util.List;

public interface NodeService {
	NodeVO create(NodeDTO nodeDTO);

	List<NodeVO> retrieve();

	NodeVO retrieve(int id);

	NodeVO update(int id, NodeDTO nodeDTO);

	void delete(int id);

	void start(int id);

	void stop(int id);

	void restart(int id);
}
