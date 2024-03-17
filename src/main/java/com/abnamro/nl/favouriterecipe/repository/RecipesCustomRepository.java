package com.abnamro.nl.favouriterecipe.repository;

import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;

import java.util.List;
import java.util.Map;

/**
 * @author lakshmimadam
 *
 */
public interface RecipesCustomRepository {
	
	List<RecipesEntity> searchRecipes(Map<String, String> queryParams) throws RecipesException;

}
