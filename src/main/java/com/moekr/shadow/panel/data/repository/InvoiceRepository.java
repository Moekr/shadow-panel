package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
