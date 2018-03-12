package com.moekr.shadow.panel.logic.vo;

import com.moekr.shadow.panel.data.entity.Invitation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@EqualsAndHashCode
@ToString
public class InvitationVO {
	private Integer id;
	private String code;
	private Boolean used;
	private Integer userId;

	public InvitationVO(Invitation invitation) {
		BeanUtils.copyProperties(invitation, this);
		this.userId = invitation.getUser() != null ? invitation.getUser().getId() : null;
	}
}
