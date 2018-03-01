package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Invoice;

import java.util.List;

public interface InvoiceDAO {
	Invoice save(Invoice invoice);

	Invoice findById(Integer id);

	List<Invoice> recentInvoice(Integer userId);
}
