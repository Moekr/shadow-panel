package com.moekr.shadow.panel.logic.vo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class HourData {
	private Integer hour;
	private Long data;
}
