package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.model.ClientModel;
import com.moekr.shadow.panel.web.dto.form.CreateClientForm;

import java.util.List;

public interface ClientService {
	List<ClientModel> findAll();

	void create(CreateClientForm form);

	void delete(int id);
}
