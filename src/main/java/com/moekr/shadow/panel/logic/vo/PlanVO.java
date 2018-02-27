package com.moekr.shadow.panel.logic.vo;

import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.data.entity.Plan;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode
@ToString
public class PlanVO {
	private Integer id;
	private String name;
	private String description;
	private Boolean available;
	private Double price;
	private Set<Integer> nodeIdSet;

	public PlanVO(Plan plan) {
		BeanUtils.copyProperties(plan, this);
		nodeIdSet = plan.getNodeSet().stream().map(Node::getId).collect(Collectors.toSet());
	}
}
