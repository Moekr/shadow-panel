package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.ClientDAO;
import com.moekr.shadow.panel.data.entity.Client;
import com.moekr.shadow.panel.logic.service.ClientService;
import com.moekr.shadow.panel.logic.vo.ClientVO;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.ClientDTO;
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
	@Transactional
	public ClientVO create(ClientDTO clientDTO) {
		Client client = new Client();
		BeanUtils.copyProperties(clientDTO, client);
		return new ClientVO(clientDAO.save(client));
	}

	@Override
	public List<ClientVO> retrieve() {
		return clientDAO.findAll().stream().map(ClientVO::new).collect(Collectors.toList());
	}

	@Override
	public ClientVO retrieve(int id) {
		Client client = clientDAO.findById(id);
		ToolKit.assertNotNull(client);
		return new ClientVO(client);
	}

	@Override
	@Transactional
	public ClientVO update(int id, ClientDTO clientDTO) {
		Client client = clientDAO.findById(id);
		ToolKit.assertNotNull(client);
		BeanUtils.copyProperties(clientDTO, client);
		return new ClientVO(clientDAO.save(client));
	}

	@Override
	@Transactional
	public void delete(int id) {
		Client client = clientDAO.findById(id);
		ToolKit.assertNotNull(client);
		clientDAO.delete(client);
	}
}
