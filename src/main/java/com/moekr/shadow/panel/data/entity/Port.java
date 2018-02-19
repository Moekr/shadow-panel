package com.moekr.shadow.panel.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = "node")
@ToString
@Entity
@Table(name = "ENTITY__PORT")
public class Port {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "port")
	private Integer port;

	@Basic
	@Column(name = "password")
	private String password;

	@Basic
	@Column(name = "method")
	private String method;

	@Basic
	@Column(name = "protocol")
	private String protocol;

	@Basic
	@Column(name = "obfs")
	private String obfs;

	@Basic
	@Column(name = "obfs_param")
	private String obfsParam;

	@ManyToOne(targetEntity = Node.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "node_id", referencedColumnName = "id")
	private Node node;
}
