package com.moekr.shadow.panel.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeStatus {
	ONLINE("正常"),
	UNSTABLE("波动"),
	OFFLINE("离线"),
	STOPPED("停用");

	private final String name;

	@Override
	public String toString() {
		return name;
	}
}
