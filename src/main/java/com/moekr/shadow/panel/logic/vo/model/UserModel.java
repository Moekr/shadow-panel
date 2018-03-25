package com.moekr.shadow.panel.logic.vo.model;

import com.moekr.shadow.panel.data.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@ToString
public class UserModel {
	private Integer id;
	private String username;
	private String token;
	private LocalDateTime createdAt;
	private String plan;
	private LocalDate revokeAt;

	public UserModel(User user) {
		BeanUtils.copyProperties(user, this);
		this.plan = user.getPlan().getName();
	}
}
