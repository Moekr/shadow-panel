package com.moekr.shadow.panel.logic.vo.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class DayData {
	private LocalDate date;
	private Long data;
}
