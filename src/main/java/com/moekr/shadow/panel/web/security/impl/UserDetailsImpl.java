package com.moekr.shadow.panel.web.security.impl;

import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.web.security.SecurityConfig;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Set;

@Data
public class UserDetailsImpl implements UserDetails {

	private String username;
	private String password;
	private Set<GrantedAuthority> authorities;

	UserDetailsImpl(User user) {
		BeanUtils.copyProperties(user, this);
		this.authorities = Collections.singleton(SecurityConfig.USER_AUTHORITY);
	}

	UserDetailsImpl(String username, String password) {
		this.username = username;
		this.password = password;
		this.authorities = Collections.singleton(SecurityConfig.ADMIN_AUTHORITY);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
