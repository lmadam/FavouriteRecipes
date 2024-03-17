package com.abnamro.nl.favouriterecipe.controller;

import com.abnamro.nl.favouriterecipe.exception.RecipesError;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import com.abnamro.nl.favouriterecipe.jwt.JwtTokenUtility;
import com.abnamro.nl.favouriterecipe.jwt.JwtUserDetailsServiceImpl;
import com.abnamro.nl.favouriterecipe.model.TokenRequest;
import com.abnamro.nl.favouriterecipe.model.TokenResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author lakshmimadam
 *
 */

@RestController
@RequestMapping("/favourites/v1")
@Tag(name = "Token", description = "Token API endpoints")
@Slf4j
public class TokenController {

	@Autowired
	private JwtTokenUtility tokenUtility;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsServiceImpl userDetailsService;

	@PostMapping("/authenticate")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Token generation successful", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
			@ApiResponse(responseCode = "400", description = "Token generation failed", content = @Content(schema = @Schema(implementation = RecipesError.class))) })
	public ResponseEntity<TokenResponse> authenticate(@Valid @RequestBody TokenRequest request)
			throws RecipesException {
		log.info("JWT token generation initiated for user :: {}", request.username());
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
		var userdetails = userDetailsService.loadUserByUsername(request.username());
		return new ResponseEntity<>(new TokenResponse(tokenUtility.generateToken(userdetails)),
				HttpStatus.OK);
	}

}
