package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyDAO extends JpaRepository<Property, String> {
}
