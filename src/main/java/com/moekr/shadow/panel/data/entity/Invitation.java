package com.moekr.shadow.panel.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode(exclude = {"plan", "user"})
@ToString
@Entity
@Table(name = "ENTITY__INVITATION")
public class Invitation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "code")
	private String code;

	@Basic
	@Column(name = "used")
	private Boolean used;

	@ManyToOne(targetEntity = Plan.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id", referencedColumnName = "id")
	private Plan plan;

	@OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
}
