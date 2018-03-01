package com.moekr.shadow.panel.data.dao.impl;

import com.moekr.shadow.panel.data.dao.AbstractDAO;
import com.moekr.shadow.panel.data.dao.InvoiceDAO;
import com.moekr.shadow.panel.data.entity.Invoice;
import com.moekr.shadow.panel.data.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvoiceDAOImpl extends AbstractDAO<Invoice, Integer> implements InvoiceDAO {
	private final InvoiceRepository repository;

	@Autowired
	public InvoiceDAOImpl(InvoiceRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public List<Invoice> recentInvoice(Integer userId) {
		return repository.recentInvoice(userId);
	}
}
