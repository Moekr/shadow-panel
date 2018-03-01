package com.moekr.shadow.panel.logic.service.impl;

import com.moekr.shadow.panel.data.dao.InvoiceDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Invoice;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.rpc.NodeManager;
import com.moekr.shadow.panel.logic.service.InvoiceService;
import com.moekr.shadow.panel.logic.vo.InvoiceVO;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.web.dto.InvoiceDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {
	private final UserDAO userDAO;
	private final InvoiceDAO invoiceDAO;
	private final NodeManager nodeManager;

	@Autowired
	public InvoiceServiceImpl(UserDAO userDAO, InvoiceDAO invoiceDAO, NodeManager nodeManager) {
		this.userDAO = userDAO;
		this.invoiceDAO = invoiceDAO;
		this.nodeManager = nodeManager;
	}

	@Override
	@Transactional
	public InvoiceVO create(InvoiceDTO invoiceDTO) {
		User user = userDAO.findByUsername(invoiceDTO.getUsername());
		ToolKit.assertNotNull(user);
		user.setBalance(user.getBalance() + invoiceDTO.getAmount());
		user = userDAO.save(user);
		Invoice invoice = new Invoice();
		BeanUtils.copyProperties(invoiceDTO, invoice);
		invoice.setBalance(user.getBalance());
		invoice.setCreatedAt(LocalDateTime.now());
		invoice.setUser(user);
		invoice = invoiceDAO.save(invoice);
		nodeManager.updateAvailableUser();
		return new InvoiceVO(invoice);
	}

	@Override
	public InvoiceVO retrieve(int id) {
		Invoice invoice = invoiceDAO.findById(id);
		ToolKit.assertNotNull(invoice);
		return new InvoiceVO(invoice);
	}

	@Override
	public List<InvoiceVO> recentInvoice(int userId) {
		return invoiceDAO.recentInvoice(userId).stream().map(InvoiceVO::new).collect(Collectors.toList());
	}
}
