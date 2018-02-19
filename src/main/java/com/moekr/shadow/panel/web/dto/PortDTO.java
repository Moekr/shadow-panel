package com.moekr.shadow.panel.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode
@ToString
public class PortDTO {
	@Range(min = 256, max = 65535)
	@NotNull
	private Integer port;
	@Pattern(regexp = "^[0-9a-zA-Z]{8,16}$")
	@NotNull
	private String password;
	@NotNull
	private String method;
	@NotNull
	private String protocol;
	@NotNull
	private String obfs;
	@NotNull
	private String obfsParam;
}
