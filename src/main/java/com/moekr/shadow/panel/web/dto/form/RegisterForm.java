package com.moekr.shadow.panel.web.dto.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode
@ToString
public class RegisterForm {
	@NotBlank(message = "请输入邀请码！")
	private String invitation;
	@Email
	@NotBlank(message = "请输入用户名！")
	private String username;
	@Pattern(regexp = "[0-9a-zA-Z]{8,16}", message = "密码只能为8-16位字母与数字")
	@NotBlank(message = "请输入密码！")
	private String password;
	private String confirm;
}
