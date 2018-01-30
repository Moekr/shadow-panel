package com.moekr.shadow.panel.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "ENTITY__CLIENT")
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "platform")
	private String platform;

	@Basic
	@Column(name = "name")
	private String name;

	@Basic
	@Column(name = "link")
	private String link;

	@Basic
	@Column(name = "hash")
	private String hash;
}
