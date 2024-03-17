package com.abnamro.nl.favouriterecipe.controller;

import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import com.abnamro.nl.favouriterecipe.jwt.JwtTokenUtility;
import com.abnamro.nl.favouriterecipe.jwt.JwtUserDetailsServiceImpl;
import com.abnamro.nl.favouriterecipe.model.TokenRequest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * @author lakshmimadam
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore({ "javax.management.*" })
public class TokenControllerTest {

	@InjectMocks
	private TokenController tokenController;

	@Mock
	private JwtTokenUtility tokenUtility;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtUserDetailsServiceImpl userDetailsService;

	private TokenRequest tokenRequest;
	
	private UserDetails userDetails;

	@Before
	public void setUp() throws RecipesException{
		tokenRequest = new TokenRequest("recipesadmin","recipesadmin123");
		userDetails = new User(tokenRequest.username(), tokenRequest.password(), Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(tokenRequest.username(),
				tokenRequest.password());
		Mockito.when(authenticationManager.authenticate(token)).thenReturn(null);
		Mockito.when(userDetailsService.loadUserByUsername(tokenRequest.username()))
				.thenReturn(userDetails);
		Mockito.when(tokenUtility.generateToken(userDetails)).thenReturn("1234567890");

	}
	
	@Test
	public void authenticteTest() throws RecipesException {
		
		Assert.assertEquals(tokenController.authenticate(tokenRequest).getStatusCode(),HttpStatus.OK);

		
	}

}
