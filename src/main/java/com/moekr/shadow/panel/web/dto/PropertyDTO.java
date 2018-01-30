package com.moekr.shadow.panel.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@ToString
public class PropertyDTO {
	@NotNull
	private String content;
}
