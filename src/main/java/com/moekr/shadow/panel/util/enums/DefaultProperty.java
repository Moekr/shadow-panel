package com.moekr.shadow.panel.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DefaultProperty {
	ANNOUNCEMENT("announcement", "");

	private String name;
	private String content;
}
