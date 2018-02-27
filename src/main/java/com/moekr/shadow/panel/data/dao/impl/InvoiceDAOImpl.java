package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.InvoiceDAO;
import com.moekr.shadow.panel.data.entity.Invoice;
import com.moekr.shadow.panel.data.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class InvoiceDAOImpl extends AbstractDAO<Invoice, Integer> implements InvoiceDAO {

	@Autowired
	public InvoiceDAOImpl(InvoiceRepository repository) {
		super(repository);
	}
}
