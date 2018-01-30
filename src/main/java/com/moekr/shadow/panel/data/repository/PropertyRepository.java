package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, String> {
}
