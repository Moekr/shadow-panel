package com.moekr.shadow.panel.web.dto.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@ToString
public class CreatePlanForm {
	@NotBlank(message = "请填写套餐名称！")
	private String name;
	@NotNull(message = "请填写套餐描述！")
	private String description;
	@Range(message = "请填写有效的权限等级！")
	@NotNull(message = "请填写权限等级！")
	private Integer level;
}
