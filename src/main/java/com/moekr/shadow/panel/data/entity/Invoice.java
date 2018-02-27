package com.moekr.shadow.panel.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(exclude = "user")
@ToString
@Entity
@Table(name = "ENTITY__INVOICE", indexes = @Index(columnList = "transaction"))
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "amount")
	private Integer amount;

	@Basic
	@Column(name = "transaction")
	private String transaction;

	@Basic
	@Column(name = "balance")
	private Double balance;

	@Basic
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
}
