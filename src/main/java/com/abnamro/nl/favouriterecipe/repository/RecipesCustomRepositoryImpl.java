package com.abnamro.nl.favouriterecipe.repository;

import com.abnamro.nl.favouriterecipe.constants.RecipesConstants;
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
			
			if(StringUtils.isNotBlank(queryParams.get(RecipesConstants.NAME))) {
				query.addCriteria(Criteria.where(RecipesConstants.NAME).is(queryParams.get(RecipesConstants.NAME)));
			}
			
			if(StringUtils.isNotBlank(queryParams.get(RecipesConstants.TYPE))) {
				query.addCriteria(Criteria.where(RecipesConstants.TYPE).is(queryParams.get(RecipesConstants.TYPE)));
			}
			
			if(StringUtils.isNotBlank(queryParams.get(RecipesConstants.INGREDIENT))) {
				query.addCriteria(StringUtils.isNotBlank(queryParams.get(RecipesConstants.INCLUDE_INGREDIENT))
						&& "N".equalsIgnoreCase(queryParams.get(RecipesConstants.INCLUDE_INGREDIENT)) ? Criteria.where(RecipesConstants.INGREDIENTS).nin(queryParams.get(RecipesConstants.INGREDIENT))
								:  Criteria.where(RecipesConstants.INGREDIENTS).in(queryParams.get(RecipesConstants.INGREDIENT)) );
			}
			
			if(StringUtils.isNotBlank(queryParams.get(RecipesConstants.INSTRUCTION))) {
				query.addCriteria(Criteria.where(RecipesConstants.INSTRUCTIONS).regex(queryParams.get(RecipesConstants.INSTRUCTION)) );
			}
			
			if(StringUtils.isNotBlank(queryParams.get(RecipesConstants.SERVINGS))) {
				query.addCriteria(Criteria.where(RecipesConstants.SERVINGS).is(Integer.valueOf(queryParams.get(RecipesConstants.SERVINGS))));
			}
			
			recipes = mongoTemplate.find(query, RecipesEntity.class);
			
		} catch (Exception exception) {
			log.error("Error while searching the recipes based on query params :: {}",queryParams);
			throw new RecipesException(RecipesErrorEnum.ERROR_NO_RECIPE_RESULTS);
		}
		
		return recipes;
		
	}

}
