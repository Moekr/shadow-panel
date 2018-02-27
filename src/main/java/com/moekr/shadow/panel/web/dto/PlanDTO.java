package com.moekr.shadow.panel.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Data
@EqualsAndHashCode
@ToString
public class PlanDTO {
	@NotBlank
	private String name;
	@NotNull
	private String description;
	@NotNull
	private Boolean available;
	@DecimalMin("0")
	@NotNull
	private BigDecimal price;
	@NotNull
	private Set<Integer> nodeIdSet;
}
