package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.PropertyDAO;
import com.moekr.shadow.panel.data.entity.Property;
import com.moekr.shadow.panel.logic.service.PropertyService;
import com.moekr.shadow.panel.logic.vo.PropertyVO;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.PropertyDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {
	private final PropertyDAO propertyDAO;

	@Autowired
	public PropertyServiceImpl(PropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

	@Override
	public List<PropertyVO> retrieve() {
		return propertyDAO.findAll().stream().map(PropertyVO::new).collect(Collectors.toList());
	}

	@Override
	public PropertyVO retrieve(String name) {
		return new PropertyVO(propertyDAO.findByName(name));
	}

	@Override
	@Transactional
	public PropertyVO update(String name, PropertyDTO propertyDTO) {
		Property property = propertyDAO.findByName(name);
		ToolKit.assertNotNull(property);
		BeanUtils.copyProperties(propertyDTO, property);
		return new PropertyVO(propertyDAO.save(property));
	}
}
