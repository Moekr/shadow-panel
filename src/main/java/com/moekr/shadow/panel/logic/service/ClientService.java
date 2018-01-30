package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.ClientVO;
import com.moekr.shadow.panel.web.dto.ClientDTO;

import java.util.List;

public interface ClientService {
	ClientVO create(ClientDTO clientDTO);

	List<ClientVO> retrieve();

	ClientVO retrieve(int id);

	ClientVO update(int id, ClientDTO clientDTO);

	void delete(int id);
}
