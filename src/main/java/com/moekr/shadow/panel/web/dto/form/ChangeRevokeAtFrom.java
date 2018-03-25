package com.moekr.shadow.panel.web.dto.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode
@ToString
public class ChangeRevokeAtFrom {
	@NotNull(message = "缺少用户ID！")
	private Integer id;
	@Pattern(regexp = "[0-9]{4}/[0-9]{2}/[0-9]{2}", message = "到期时间格式不正确！")
	@NotNull(message = "请填写到期时间！")
	private String revokeAt;
}
