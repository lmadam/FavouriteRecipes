package com.abnamro.nl.favouriterecipe.jwt;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lakshmimadam
 *
 */

@Component
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Value("${recipes.username:recipesadmin}")
	private String user;
	
	@Value("${recipes.password:recipesadmin123}")
	private String credential;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (StringUtils.isNotBlank(username) && username.equalsIgnoreCase(user)) {
			return new User(user, credential,
					List.of(new SimpleGrantedAuthority("ROLE_USER")));
		} else {
			throw new UsernameNotFoundException("User not found");
		}

	}

}
