package com.moekr.shadow.panel.data.repository;

import com.moekr.shadow.panel.data.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	@Query(value = "SELECT * FROM ENTITY__INVOICE AS i WHERE i.user_id = ?1 ORDER BY i.created_at DESC LIMIT 25", nativeQuery = true)
	List<Invoice> recentInvoice(Integer userId);
}
