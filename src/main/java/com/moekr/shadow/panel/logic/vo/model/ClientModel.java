package com.moekr.shadow.panel.logic.vo.model;

import com.moekr.shadow.panel.data.entity.Client;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@EqualsAndHashCode
@ToString
public class ClientModel {
	private Integer id;
	private String platform;
	private String name;
	private String link;
	private String hash;

	public ClientModel(Client client) {
		BeanUtils.copyProperties(client, this);
	}
}
