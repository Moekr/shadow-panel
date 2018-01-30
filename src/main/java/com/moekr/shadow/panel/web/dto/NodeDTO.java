package com.moekr.shadow.panel.web.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
@ToString
public class NodeDTO {
	@NotNull
	private String region;
	@NotNull
	private String name;
	@NotNull
	private String address;
	@NotNull
	private String note;
	@Range
	@NotNull
	private Integer totalData;
	@Range
	@NotNull
	private Long usedData;
}
