package com.moekr.shadow.panel.logic.rpc.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class Instruction {
	private String command;
	private String payload;
}
