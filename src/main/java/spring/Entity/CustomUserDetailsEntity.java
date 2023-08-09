package spring.Entity;

import java.util.Arrays;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class CustomUserDetailsEntity implements UserDetails{

	private String username;
	private String roles;
	private String password;
	
	public CustomUserDetailsEntity(String username, String roles) {
		this.username = username;
		this.roles = roles;
	}
	
	public CustomUserDetailsEntity(UserEntity userEntity) {
		this.username = userEntity.getUsername();
		this.password = userEntity.getPassword();
		this.roles = userEntity.getRoles();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return Arrays.asList(new SimpleGrantedAuthority(this.roles));
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
}
