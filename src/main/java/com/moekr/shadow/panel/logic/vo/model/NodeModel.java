package com.moekr.shadow.panel.logic.vo.model;

import com.moekr.shadow.panel.data.entity.Node;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@ToString
public class NodeModel {
	private Integer id;
	private String region;
	private String name;
	private Integer level;
	private Boolean enable;
	private String address;
	private String note;
	private Integer totalData;
	private Long usedData;
	private Integer port;
	private String password;
	private String method;
	private String protocol;
	private String obfs;
	private String serverObfsParam;
	private String clientObfsParam;
	private LocalDateTime createdAt;

	public NodeModel(Node node) {
		BeanUtils.copyProperties(node, this);
	}
}
