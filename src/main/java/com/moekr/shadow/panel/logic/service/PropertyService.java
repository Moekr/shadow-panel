package com.moekr.shadow.panel.logic.service;

import com.moekr.shadow.panel.logic.vo.PropertyVO;
import com.moekr.shadow.panel.web.dto.PropertyDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface PropertyService {
	List<PropertyVO> retrieve();

	PropertyVO retrieve(String name);

	PropertyVO update(String name, PropertyDTO propertyDTO);

	default Map<String, String> properties() {
		return retrieve().stream().collect(Collectors.toMap(PropertyVO::getName, PropertyVO::getContent));
	}
}
