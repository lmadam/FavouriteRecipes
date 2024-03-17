package com.abnamro.nl.favouriterecipe.service;


import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import com.abnamro.nl.favouriterecipe.model.RecipesRequest;

import java.util.List;
import java.util.Map;

/**
 * @author lakshmimadam
 *
 */
public interface RecipesService {

	/**
	 * This method is used to add a new recipe
	 * @param request
	 * @return
	 */
	RecipesEntity addRecipe(RecipesRequest request);
	
	/**
	 * This method is used to update existing recipe based on id i.e unique identified for each recipe
	 * @param id
	 * @param request
	 * @throws RecipesException
	 */
	void updateRecipe(String id, RecipesRequest request) throws RecipesException;
	
	/**
	 * This method is used to fetch recipe based on id i.e. unique identifier for each recipe
	 * @param id
	 * @return RecipesEntity object
	 * @throws RecipesException
	 */
	RecipesEntity fetchRecipe(String id) throws RecipesException;
	
	/**
	 * This method is used to remove the existing recipe based on id i.e. unique identifier for each recipe
	 * @param id
	 * @throws RecipesException
	 */
	void removeRecipe(String id) throws RecipesException;
	
	
	/**
	 * This method is used to fetch the recipes based on search criteria
	 * @param type
	 * @param noOfServings
	 * @param instruction
	 * @param ingredient
	 * @return
	 * @throws RecipesException 
	 */
	List<RecipesEntity> searchRecipes(Map<String, String> queryParams) throws RecipesException;

}
