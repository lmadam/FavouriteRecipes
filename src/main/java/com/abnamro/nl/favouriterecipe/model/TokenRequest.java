package com.abnamro.nl.favouriterecipe.model;

import com.abnamro.nl.favouriterecipe.constants.RecipesConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author lakshmimadam
 *
 */


public record TokenRequest (
	@NotBlank(message = "username not specified")
	@Size(min=1,max=50, message = "username length is invalid")
	@Pattern(regexp = RecipesConstants.REGEX_AUTH,message = "username is invalid")
	@Schema(description = "username of user",example = "recipesadmin", required = true)
	 String username,
	
	@NotBlank(message = "password not specified")
	@Size(min=1,max=50, message = "password length is invalid")
	@Pattern(regexp = RecipesConstants.REGEX_AUTH,message = "password is invalid")
	@Schema(description = "password of user",example = "recipesadmin123", required = true)
	 String password){ }
