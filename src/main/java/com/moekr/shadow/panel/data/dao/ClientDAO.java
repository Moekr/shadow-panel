package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Client;

import java.util.List;

public interface ClientDAO {
	Client save(Client client);

	List<Client> findAll();

	Client findById(Integer id);

	void delete(Client client);
}
