package com.moekr.shadow.panel.logic.rpc.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class Status {
	private boolean running;
	private Configuration configuration;
}
