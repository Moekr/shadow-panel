package com.moekr.shadow.panel.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Data
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "ENTITY__PROPERTY")
public class Property {
	@Id
	@Column(name = "name")
	private String name;

	@Basic
	@Column(name = "content")
	private String content;
}
