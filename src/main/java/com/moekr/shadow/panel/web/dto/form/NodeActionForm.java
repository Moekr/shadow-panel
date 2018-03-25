package com.moekr.shadow.panel.web.dto.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode
@ToString
public class NodeActionForm {
	@NotNull(message = "缺少节点ID！")
	private Integer id;
	@Pattern(regexp = "^(enable|disable|remove)$", message = "不能识别的目标操作！")
	@NotNull(message = "缺少目标操作！")
	private String action;
}
