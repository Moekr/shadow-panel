package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.model.NodeModel;
import com.moekr.shadow.panel.web.dto.form.CreateNodeForm;
import com.moekr.shadow.panel.web.dto.form.NodeActionForm;

import java.util.List;

public interface NodeService {
	List<NodeModel> findAll();

	NodeModel findById(int id);

	void create(CreateNodeForm form);

	void action(NodeActionForm form);

	List<NodeModel> available(int userId);
}
