package com.moekr.shadow.panel.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum DefaultProperty {
	METHOD("method", "aes-256-cfb"),
	PROTOCOL("protocol", "origin"),
	PROTOCOL_PARAM("protocol_param", ""),
	OBFS("obfs", "plain"),
	OBFS_PARAM("obfs_param", ""),
	ANNOUNCEMENT("announcement", "");

	public static final Set<String> CONF_PROPERTY = Stream.of(METHOD, PROTOCOL, PROTOCOL_PARAM, OBFS, OBFS_PARAM)
			.map(DefaultProperty::getName)
			.collect(Collectors.toSet());

	private String name;
	private String content;
}
