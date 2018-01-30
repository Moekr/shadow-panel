package com.moekr.shadow.panel.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode
@ToString
public class UserDTO {
	@Email
	@NotNull
	private String username;
	@Pattern(regexp = "^[0-9a-zA-Z]{8,16}$")
	@NotNull
	private String password;
	@Range(min = 1024, max = 65535)
	@NotNull
	private Integer port;
}
