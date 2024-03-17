package com.abnamro.nl.favouriterecipe.config;

import com.abnamro.nl.favouriterecipe.jwt.JwtTokenFilter;
import com.abnamro.nl.favouriterecipe.jwt.JwtUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author laskhmimadam
 *
 */

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtUserDetailsServiceImpl jwtUserDetailsService;

	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	@Autowired
	@Override
	public void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	   protected void configure(HttpSecurity http) throws Exception {
	      http.csrf()
	      .disable()
	      .authorizeRequests()
	      .antMatchers("/favourites/v1/authenticate","/swagger-ui.html","/swagger-ui/**","/v3/api-docs","/v3/api-docs/**")
	      .permitAll()
	      .anyRequest()
	      .authenticated()
	      .and()
	      .sessionManagement()
	      .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	      http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
	   }

}
