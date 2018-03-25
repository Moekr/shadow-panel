package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.model.PropertyModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface PropertyService {
	List<PropertyModel> findAll();

	PropertyModel findById(String id);

	default PropertyModel findByName(String name) {
		return findById(name);
	}

	default Map<String, String> properties() {
		return findAll().stream().collect(Collectors.toMap(PropertyModel::getName, PropertyModel::getContent));
	}
}
