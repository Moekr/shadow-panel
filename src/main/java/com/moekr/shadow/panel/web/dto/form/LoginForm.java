package com.moekr.shadow.panel.web.dto.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

@Data
@EqualsAndHashCode
@ToString
public class LoginForm {
	@NotBlank(message = "请输入用户名！")
	private String username;
	@NotBlank(message = "请输入密码！")
	private String password;
	private String remember;
	private String redirect;
}
