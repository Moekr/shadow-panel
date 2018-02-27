package com.moekr.shadow.panel.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(exclude = {"user", "node"})
@Entity
@Table(name = "ENTITY__RECORD", indexes = @Index(columnList = "date"))
public class Record {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "date")
	private LocalDate date;

	@Basic
	@Column(name = "hour")
	private Integer hour;

	@Basic
	@Column(name = "data")
	private Long data;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToOne(targetEntity = Node.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "node_id", referencedColumnName = "id")
	private Node node;
}
