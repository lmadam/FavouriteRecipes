package com.abnamro.nl.favouriterecipe.controller;

import com.abnamro.nl.favouriterecipe.constants.RecipesConstants;
import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.exception.RecipesError;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import com.abnamro.nl.favouriterecipe.model.RecipesRequest;
import com.abnamro.nl.favouriterecipe.service.RecipesService;
import com.abnamro.nl.favouriterecipe.utility.RecipesUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author lakshmimadam
 *
 */

@RestController
@RequestMapping("/favourites/v1")
@Tag(name = "Recipes", description = "Recipes API endpoints")
public class RecipesController {

	@Autowired
	private RecipesService recipesService;

	@PostMapping(value = "/recipes")
	@Operation(summary = "Create recipe", description = "Create a new recipe based on type, name, servings, ingredients and instructions")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Creation successful"),
			@ApiResponse(responseCode = "400", description = "Creation failed", content = @Content(schema = @Schema(implementation = RecipesError.class))) })
	public ResponseEntity<Object> addRecipe(@Valid @RequestBody RecipesRequest request) {
        var recipe = recipesService.addRecipe(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.header(HttpHeaders.LOCATION, RecipesConstants.RECIPE_LOCATION_HEADER + recipe.getId()).build();
	}

	@PutMapping(value = "/recipes/{id}")
	@Operation(summary = "Update recipe", description = "Update a recipe based on recipes id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Updation successful"),
			@ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content(schema = @Schema(implementation = RecipesError.class))) })
	public ResponseEntity<Void> updateRecipe(
			@PathVariable @Parameter(required = true, example = "bb9fcc3a-4744-46ce-93dd-82ea93189df1", description = "recipe id") String id,
			@Valid @RequestBody RecipesRequest request) throws RecipesException {
		RecipesUtility.validateRecipeId(id);
		recipesService.updateRecipe(id, request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/recipes/{id}")
	@Operation(summary = "Fetch recipe", description = "Fetch a recipe based on recipes id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Fetch successful", content = @Content(schema = @Schema(implementation = RecipesEntity.class))),
			@ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content(schema = @Schema(implementation = RecipesError.class))) })
	public ResponseEntity<RecipesEntity> fetchRecipe(
			@PathVariable @Parameter(required = true, example = "bb9fcc3a-4744-46ce-93dd-82ea93189df1", description = "recipe id") String id)
			throws RecipesException {
		RecipesUtility.validateRecipeId(id);
		var recipe = recipesService.fetchRecipe(id);
		return new ResponseEntity<>(recipe, HttpStatus.OK);
	}

	@DeleteMapping(value = "/recipes/{id}")
	@Operation(summary = "Remove recipe", description = "Remove a recipe based on recipes id")
	@ApiResponses(value = { @ApiResponse(responseCode = "204", description = "Deletion successful"),
			@ApiResponse(responseCode = "404", description = "Recipe not found", content = @Content(schema = @Schema(implementation = RecipesError.class))) })
	public ResponseEntity<Void> removeRecipe(
			@PathVariable @Parameter(required = true, example = "bb9fcc3a-4744-46ce-93dd-82ea93189df1", description = "recipe id") String id)
			throws RecipesException {
		RecipesUtility.validateRecipeId(id);
		recipesService.removeRecipe(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/recipes")
	@Operation(summary = "Search recipes", description = "Search recipes based on type, name, servings, ingredient, instruction and includeIngredient")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Search Successful", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RecipesEntity.class)))),
			@ApiResponse(responseCode = "400", description = "Search failed", content = @Content(schema = @Schema(implementation = RecipesError.class))) })
	public ResponseEntity<List<RecipesEntity>> searchRecipes(
			@RequestParam @Parameter(required = false, example = "type:vegetarian,ingredient:salt,includeIngredient:Y/N", description = "allowed query params type, name, servings, ingredient, includeIngredient, instruction") Map<String, String> queryParams)
			throws RecipesException {
		RecipesUtility.validateQueryParams(queryParams);
		var recipes = recipesService.searchRecipes(queryParams);
		return new ResponseEntity<>(recipes, HttpStatus.OK);
	}

}
