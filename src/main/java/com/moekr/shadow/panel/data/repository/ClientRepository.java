package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
