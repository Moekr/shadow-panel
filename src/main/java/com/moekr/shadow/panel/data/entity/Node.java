package com.moekr.shadow.panel.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "ENTITY__NODE")
public class Node {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "region")
	private String region;

	@Basic
	@Column(name = "name")
	private String name;

	@Basic
	@Column(name = "address")
	private String address;

	@Basic
	@Column(name = "note")
	private String note;

	@Basic
	@Column(name = "total_data")
	private Integer totalData;

	@Basic
	@Column(name = "used_data")
	private Long usedData;

	@Basic
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Basic
	@Column(name = "revoked_at")
	private LocalDateTime revokedAt;
}
