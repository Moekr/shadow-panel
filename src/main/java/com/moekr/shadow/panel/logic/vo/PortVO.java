package com.moekr.shadow.panel.logic.vo;

import com.moekr.shadow.panel.data.entity.Port;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@EqualsAndHashCode
@ToString
public class PortVO {
	private Integer id;
	private Integer port;
	private String password;
	private String method;
	private String protocol;
	private String obfs;
	private String obfsParam;

	public PortVO(Port port) {
		BeanUtils.copyProperties(port, this);
	}
}
