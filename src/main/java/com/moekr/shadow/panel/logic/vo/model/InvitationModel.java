package com.moekr.shadow.panel.logic.vo.model;

import com.moekr.shadow.panel.data.entity.Invitation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@ToString
public class InvitationModel {
	private Integer id;
	private String code;
	private Boolean used;
	private String plan;
	private Integer user;
	private LocalDateTime usedAt;

	public InvitationModel(Invitation invitation) {
		BeanUtils.copyProperties(invitation, this);
		this.plan = invitation.getPlan().getName();
		this.user = used ? invitation.getUser().getId() : null;
		this.usedAt = used ? invitation.getUser().getCreatedAt() : null;
	}
}
