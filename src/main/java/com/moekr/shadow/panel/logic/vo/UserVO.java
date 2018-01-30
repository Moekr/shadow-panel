package com.moekr.shadow.panel.logic.vo;

import com.moekr.shadow.panel.data.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.time.ZoneOffset;

@Data
@EqualsAndHashCode
@ToString
public class UserVO {
	private Integer id;
	private String username;
	private Integer port;
	private Long createdAt;
	private Long revokedAt;

	public UserVO(User user) {
		BeanUtils.copyProperties(user, this, "createdAt", "revokedAt");
		this.createdAt = user.getCreatedAt().toEpochSecond(ZoneOffset.ofHours(8));
		this.revokedAt = user.getRevokedAt() == null ? null : user.getRevokedAt().toEpochSecond(ZoneOffset.ofHours(8));
	}
}
