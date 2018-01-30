package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Property;

import java.util.List;

public interface PropertyDAO {
	Property save(Property property);

	List<Property> findAll();

	Property findById(String id);

	default Property findByName(String name) {
		return findById(name);
	}
}
