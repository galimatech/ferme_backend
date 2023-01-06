package com.projet.ferme.entity.person;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class MyUserDetails implements UserDetails{

	private String username;
    private String password;
   // private Collection<? extends GrantedAuthority> authorities;
    
    
	public MyUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        //this.authorities = (Collection<? extends GrantedAuthority>) user.getRoles();
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
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
