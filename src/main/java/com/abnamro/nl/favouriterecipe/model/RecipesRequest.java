package com.abnamro.nl.favouriterecipe.model;

import com.abnamro.nl.favouriterecipe.constants.RecipesConstants;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;


/**
 * @author lakshmimadam
 *
 */

public record RecipesRequest(
	@NotBlank(message = "type input not specified")
	@Size(min=1,max=50, message = "type length is invalid")
	@Pattern(regexp = RecipesConstants.REGEX_RECIPE,message = "type input is invalid")
	@Schema(description = "Type of the recipes vegetarian/non vegetarian",example = "Vegetarian", required = true)
	 String type,
	
	
	@NotBlank(message = "name input not specified")
	@Size(min=1,max=100, message = "name length is invalid")
	@Pattern(regexp = RecipesConstants.REGEX_RECIPE,message = "name input is invalid")
	@Schema(description = "Name of the recipe",example = "Biryani", required = true)
	 String name,
	
	
	@Positive(message = "servings should be more than Zero")
	@Schema(description = "Number of servings",example = "20", required = true)
	 int servings,
	
	
	@Valid
	@NotNull(message = "ingredients not specified")
	@Schema(description = "List of ingredients",example = "[salt,pepper]", required = true)
	 List<@Size(min=1,max=100, message = "ingredient length is invalid")
	@Pattern(regexp = RecipesConstants.REGEX_RECIPE,message = "ingredient input is invalid") String> ingredients,
	
	
	@Valid
	@NotNull(message = "instructions not specified")
	@Schema(description = "List of instructions",example = "[add water,add salt]", required = true)
	 List<@Size(min=1,max=200, message = "instruction length is invalid")
	@Pattern(regexp = RecipesConstants.REGEX_RECIPE,message = "instruction input is invalid") String> instructions){

}
