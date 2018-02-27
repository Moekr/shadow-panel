package com.moekr.shadow.panel.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode
@ToString
public class InvoiceDTO {
	@Min(0)
	@NotNull
	private Integer amount;
	@Pattern(regexp = "[0-9]{32}")
	@NotNull
	private String transaction;
	@Email
	@NotNull
	private String username;
}
