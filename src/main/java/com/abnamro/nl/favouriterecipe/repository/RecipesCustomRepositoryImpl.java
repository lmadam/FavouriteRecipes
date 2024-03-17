package com.abnamro.nl.favouriterecipe.repository;

import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.enums.RecipesErrorEnum;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author lakshmimadam
 *
 */

@Repository
@Slf4j
public class RecipesCustomRepositoryImpl implements RecipesCustomRepository{
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	
	@Override
	public List<RecipesEntity> searchRecipes(Map<String, String> queryParams) throws RecipesException{
		
		List<RecipesEntity>  recipes = null;
		
		try {
			
			Query query = new Query();
			
			if(StringUtils.isNotBlank(queryParams.get("name"))) {
				query.addCriteria(Criteria.where("name").is(queryParams.get("name")));
			}
			
			if(StringUtils.isNotBlank(queryParams.get("type"))) {
				query.addCriteria(Criteria.where("type").is(queryParams.get("type")));
			}
			
			if(StringUtils.isNotBlank(queryParams.get("ingredient"))) {
				query.addCriteria(StringUtils.isNotBlank(queryParams.get("includeIngredient")) 
						&& "N".equalsIgnoreCase(queryParams.get("includeIngredient")) ? Criteria.where("ingredients").nin(queryParams.get("ingredient")) 
								:  Criteria.where("ingredients").in(queryParams.get("ingredient")) );
			}
			
			if(StringUtils.isNotBlank(queryParams.get("instruction"))) {
				query.addCriteria(Criteria.where("instructions").regex(queryParams.get("instruction")) );
			}
			
			if(StringUtils.isNotBlank(queryParams.get("servings"))) {
				query.addCriteria(Criteria.where("servings").is(Integer.valueOf(queryParams.get("servings"))));
			}
			
			recipes = mongoTemplate.find(query, RecipesEntity.class);
			
		} catch (Exception exception) {
			log.error("Error while searching the recipes based on query params :: {}",queryParams);
			throw new RecipesException(RecipesErrorEnum.ERROR_NO_RECIPE_RESULTS);
		}
		
		return recipes;
		
	}

}
