package com.moekr.shadow.panel.logic.vo;

import com.moekr.shadow.panel.data.entity.Property;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@EqualsAndHashCode
@ToString
public class PropertyVO {
	private String name;
	private String content;

	public PropertyVO(Property property) {
		BeanUtils.copyProperties(property, this);
	}
}
