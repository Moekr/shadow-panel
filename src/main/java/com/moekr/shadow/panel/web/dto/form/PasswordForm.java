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
public class PasswordForm {
	@Email(message = "用户名格式不正确！")
	@NotBlank(message = "请输入需要重置密码的用户名！")
	private String username;
	private String origin;
	@Pattern(regexp = "^[0-9a-zA-Z]{8,16}$", message = "密码只能为8-16位字母与数字！")
	private String password;
	private String confirm;
}
