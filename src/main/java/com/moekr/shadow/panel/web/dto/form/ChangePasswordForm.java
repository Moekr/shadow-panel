package com.moekr.shadow.panel.web.dto.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode
@ToString
public class ChangePasswordForm {
	@NotBlank(message = "请输入旧密码！")
	private String origin;
	@Pattern(regexp = "[0-9a-zA-Z_]{8,16}", message = "密码只能包含大小写字母、数字和下划线，且必须为8-16位！")
	@NotBlank(message = "请输入新密码！")
	private String password;
	@NotBlank(message = "两次输入的密码不一致！")
	private String confirm;
}
