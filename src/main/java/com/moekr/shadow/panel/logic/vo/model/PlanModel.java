package com.moekr.shadow.panel.logic.vo.model;

import com.moekr.shadow.panel.data.entity.Plan;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@EqualsAndHashCode
@ToString
public class PlanModel {
	private Integer id;
	private String name;
	private String description;
	private Integer level;

	public PlanModel(Plan plan) {
		BeanUtils.copyProperties(plan, this);
	}
}
