package com.future.yingyue.base;

import com.future.yingyue.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class AdminDetails implements UserDetails{

	private static final long serialVersionUID = 6568790118514627816L;

	private Admin admin;

	private Set<GrantedAuthority> authorities;

	public AdminDetails(Admin admin, Set<GrantedAuthority> authorities) {
		this.admin = admin;
		this.authorities = Collections.unmodifiableSet(authorities);
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Integer getId() {
		return admin.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return admin.getPassword();
	}

	@Override
	public String getUsername() {
		return admin.getAccountNumber();
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
