package com.moekr.shadow.panel.logic.vo;

import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.util.enums.NodeStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.time.ZoneOffset;

@Data
@EqualsAndHashCode
@ToString
public class NodeVO {
	private Integer id;
	private String region;
	private String name;
	private String address;
	private String note;
	private Integer totalData;
	private Long usedData;
	private NodeStatus status;
	private Long createdAt;
	private Long revokedAt;

	public NodeVO(Node node) {
		BeanUtils.copyProperties(node, this, "createdAt", "revokedAt");
		this.createdAt = node.getCreatedAt().toEpochSecond(ZoneOffset.ofHours(8));
		this.revokedAt = node.getRevokedAt() == null ? null : node.getRevokedAt().toEpochSecond(ZoneOffset.ofHours(8));
	}

	public NodeVO(Node node, NodeStatus status) {
		this(node);
		this.status = status;
	}
}
