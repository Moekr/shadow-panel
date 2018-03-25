package com.moekr.shadow.panel.web.dto.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@ToString
public class CreateClientForm {
	@NotNull(message = "请输入平台！")
	private String platform;
	@NotNull(message = "请输入名称！")
	private String name;
	@URL(message = "链接格式不正确！")
	@NotNull(message = "请输入链接！")
	private String link;
	@NotNull(message = "请输入校验码！")
	private String hash;
}
