package com.moekr.shadow.panel.logic.vo;

import com.moekr.shadow.panel.data.entity.Invoice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.time.ZoneOffset;

@Data
@EqualsAndHashCode
@ToString
public class InvoiceVO {
	private Integer id;
	private Integer amount;
	private String transaction;
	private Long createdAt;
	private Integer userId;

	public InvoiceVO(Invoice invoice) {
		BeanUtils.copyProperties(invoice, this, "createdAt");
		createdAt = invoice.getCreatedAt().toEpochSecond(ZoneOffset.ofHours(8));
		userId = invoice.getUser().getId();
	}
}
