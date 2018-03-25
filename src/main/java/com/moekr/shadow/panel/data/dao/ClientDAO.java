package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDAO extends JpaRepository<Client, Integer> {
}
