package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.PropertyDAO;
import com.moekr.shadow.panel.data.entity.Property;
import com.moekr.shadow.panel.data.repository.PropertyRepository;
import com.moekr.shadow.panel.util.enums.DefaultProperty;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@CommonsLog
public class PropertyDAOImpl extends AbstractDAO<Property, String> implements PropertyDAO {
	@Autowired
	public PropertyDAOImpl(PropertyRepository repository) {
		super(repository);
		checkDefaultProperty();
	}

	private void checkDefaultProperty() {
		for (DefaultProperty defaultProperty : DefaultProperty.values()) {
			Property property = findByName(defaultProperty.getName());
			if (property == null) {
				property = new Property();
				BeanUtils.copyProperties(defaultProperty, property);
				save(property);
			}
		}
	}
}
