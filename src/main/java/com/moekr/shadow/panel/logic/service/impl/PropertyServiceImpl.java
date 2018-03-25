package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.PropertyDAO;
import com.moekr.shadow.panel.data.entity.Property;
import com.moekr.shadow.panel.logic.service.PropertyService;
import com.moekr.shadow.panel.logic.vo.model.PropertyModel;
import com.moekr.shadow.panel.util.Asserts;
import com.moekr.shadow.panel.util.enums.DefaultProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
	private final PropertyDAO propertyDAO;

	@Autowired
	public PropertyServiceImpl(PropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

	@PostConstruct
	private void initial() {
		Set<Property> properties = new HashSet<>();
		for (DefaultProperty defaultProperty : DefaultProperty.values()) {
			Property property = propertyDAO.findById(defaultProperty.getName()).orElse(null);
			if (property == null) {
				property = new Property();
				property.setName(defaultProperty.getName());
				property.setContent(defaultProperty.getContent());
				properties.add(property);
			}
		}
		propertyDAO.saveAll(properties);
	}

	@Override
	public List<PropertyModel> findAll() {
		return propertyDAO.findAll().stream().map(PropertyModel::new).collect(Collectors.toList());
	}

	@Override
	public PropertyModel findById(String id) {
		Property property = propertyDAO.findById(id).orElse(null);
		Asserts.isTrue(property != null, HttpStatus.NOT_FOUND.value());
		return new PropertyModel(property);
	}
}
