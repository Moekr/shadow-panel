package com.moekr.shadow.panel.web.dto.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode
@ToString
public class RegisterForm {
	@NotNull(message = "请输入邀请码！")
	private String invitation;
	@Pattern(regexp = "[0-9a-zA-Z_]{4,16}", message = "用户名只能包含大小写字母、数字和下划线，且必须为4-16位！")
	@NotNull(message = "请填写用户名！")
	private String username;
	@Pattern(regexp = "[0-9a-zA-Z_]{8,16}", message = "密码只能包含大小写字母、数字和下划线，且必须为8-16位！")
	@NotNull(message = "请填写密码！")
	private String password;
	@NotNull(message = "两次输入的密码不一致！")
	private String confirm;
}
