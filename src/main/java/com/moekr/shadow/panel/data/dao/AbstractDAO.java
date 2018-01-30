package com.moekr.shadow.panel.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDAO<T, ID extends Serializable> {
	private final JpaRepository<T, ID> repository;

	public AbstractDAO(JpaRepository<T, ID> repository) {
		this.repository = repository;
	}

	public T save(T entity) {
		return repository.save(entity);
	}

	public List<T> findAll() {
		return repository.findAll();
	}

	public T findById(ID entityId) {
		return repository.findOne(entityId);
	}

	public void delete(T entity) {
		repository.delete(entity);
	}
}
