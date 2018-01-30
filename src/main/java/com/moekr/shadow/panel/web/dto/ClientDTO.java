package com.moekr.shadow.panel.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@ToString
public class ClientDTO {
	@NotNull
	private String platform;
	@NotNull
	private String name;
	@NotNull
	private String link;
	@NotNull
	private String hash;
}
