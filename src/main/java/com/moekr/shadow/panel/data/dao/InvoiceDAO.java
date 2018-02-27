package com.moekr.shadow.panel.data.dao;

import com.moekr.shadow.panel.data.entity.Invoice;

public interface InvoiceDAO {
	Invoice save(Invoice invoice);

	Invoice findById(Integer id);
}
