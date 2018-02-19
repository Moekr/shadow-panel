package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Port;

public interface PortDAO {
	Port save(Port port);

	Port findById(Integer id);

	void delete(Port port);
}
