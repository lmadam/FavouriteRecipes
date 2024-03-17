package com.abnamro.nl.favouriterecipe.service;

import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.enums.RecipesErrorEnum;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import com.abnamro.nl.favouriterecipe.mapper.RecipeMapper;
import com.abnamro.nl.favouriterecipe.model.RecipesRequest;
import com.abnamro.nl.favouriterecipe.repository.RecipesCustomRepository;
import com.abnamro.nl.favouriterecipe.repository.RecipesRepository;
import com.abnamro.nl.favouriterecipe.utility.RecipesUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author lakshmimadam
 *
 */

@Service
@Slf4j
public class RecipesServiceImpl implements RecipesService {

	@Autowired
	private RecipesRepository recipesRepository;

	@Autowired
	private RecipesCustomRepository recipesCustomRepository;

	@Autowired
	private RecipeMapper recipeMapper;

	@Override
	public RecipesEntity addRecipe(RecipesRequest request) {
		log.info("Add recipe request initiated for recipe type :: {} and name :: {}", request.type(),
				request.name());
		var entity = recipeMapper.fromRecipesRequest(request);
		entity.setId(RecipesUtility.generateRecipeId());
		entity.setCreatedDate(LocalDateTime.now());
		return recipesRepository.save(entity);
	}

	@Override
	public void updateRecipe(String id, RecipesRequest request) throws RecipesException {
		log.info("Recipe update requested for recipe id :: {}", id);
		var recipe = recipesRepository.findById(id);
		if (recipe.isPresent()) {
			var entity = recipeMapper.fromRecipesRequest(request);
			entity.setId(recipe.get().getId());
			entity.setCreatedDate(recipe.get().getCreatedDate());
			entity.setUpdatedDate(LocalDateTime.now());
			recipesRepository.save(entity);
		} else {
			log.error("Recipe with id :: {} not found in Database", id);
			throw new RecipesException(RecipesErrorEnum.ERROR_RECIPE_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public RecipesEntity fetchRecipe(String id) throws RecipesException {
		log.info("Fetch recipe requested for recipe id :: {}", id);
		var recipe = recipesRepository.findById(id);
		if (recipe.isPresent()) {
			return recipe.get();
		} else {
			log.error("Recipe with id :: {} not found in Database", id);
			throw new RecipesException(RecipesErrorEnum.ERROR_RECIPE_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void removeRecipe(String id) throws RecipesException {
		log.info("Recipe removal requested for recipe id :: {}", id);
		var recipe = recipesRepository.findById(id);
		if (recipe.isPresent()) {
			recipesRepository.deleteById(id);
		} else {
			log.error("Recipe with id :: {} not found in Database", id);
			throw new RecipesException(RecipesErrorEnum.ERROR_RECIPE_NOT_FOUND, HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public List<RecipesEntity> searchRecipes(Map<String, String> queryParams) throws RecipesException {
		log.info("Searching recipes based on query parameters :: {}", queryParams);
		var matchedRecipes = CollectionUtils.isEmpty(queryParams) ? recipesRepository.findAll()
				: recipesCustomRepository.searchRecipes(queryParams);
		if (CollectionUtils.isEmpty(matchedRecipes)) {
			log.error("Recipes search doesn't matched any results");
			throw new RecipesException(RecipesErrorEnum.ERROR_NO_RECIPE_RESULTS, HttpStatus.BAD_REQUEST);
		} else {
			return matchedRecipes;
		}
	}

}
