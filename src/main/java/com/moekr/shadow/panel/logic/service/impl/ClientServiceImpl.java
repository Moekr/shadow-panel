package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.ClientDAO;
import com.moekr.shadow.panel.data.entity.Client;
import com.moekr.shadow.panel.logic.service.ClientService;
import com.moekr.shadow.panel.logic.vo.model.ClientModel;
import com.moekr.shadow.panel.util.Asserts;
import com.moekr.shadow.panel.web.dto.form.CreateClientForm;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
	private final ClientDAO clientDAO;

	@Autowired
	public ClientServiceImpl(ClientDAO clientDAO) {
		this.clientDAO = clientDAO;
	}

	@Override
	public List<ClientModel> findAll() {
		return clientDAO.findAll().stream().map(ClientModel::new).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void create(CreateClientForm form) {
		Client client = new Client();
		BeanUtils.copyProperties(form, client);
		clientDAO.save(client);
	}

	@Override
	@Transactional
	public void delete(int id) {
		Client client = clientDAO.findById(id).orElse(null);
		Asserts.isTrue(client != null, "目标客户端不存在！");
		clientDAO.delete(client);
	}
}
