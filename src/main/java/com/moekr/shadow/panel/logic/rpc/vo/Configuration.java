package com.moekr.shadow.panel.logic.rpc.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

@Data
@EqualsAndHashCode
@ToString
public class Configuration {
	private String method;
	private String protocol;
	private String protocolParam;
	private String obfs;
	private String obfsParam;
	private Map<Integer, String> portPassword;
}
