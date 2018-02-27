package com.moekr.shadow.panel.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = {"userSet", "nodeSet"})
@ToString
@Entity
@Table(name = "ENTITY__PLAN")
public class Plan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "name")
	private String name;

	@Basic
	@Column(name = "description")
	private String description;

	@Basic
	@Column(name = "available")
	private Boolean available;

	@Basic
	@Column(name = "price")
	private Double price;

	@OneToMany(targetEntity = User.class, mappedBy = "plan")
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<User> userSet = new HashSet<>();

	@ManyToMany(targetEntity = Node.class)
	@JoinTable(name = "LINK__PLAN_NODE",
			joinColumns = @JoinColumn(name = "plan_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "node_id", referencedColumnName = "id")
	)
	@LazyCollection(LazyCollectionOption.EXTRA)
	private Set<Node> nodeSet = new HashSet<>();
}
