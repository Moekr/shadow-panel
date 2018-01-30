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
@Table(name = "ENTITY__USER", indexes = {@Index(columnList = "username"), @Index(columnList = "port")})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "username", unique = true)
	private String username;

	@Basic
	@Column(name = "password")
	private String password;

	@Basic
	@Column(name = "port", unique = true)
	private Integer port;

	@Basic
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@Basic
	@Column(name = "revoked_at")
	private LocalDateTime revokedAt;
}
