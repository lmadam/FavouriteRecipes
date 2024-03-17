package com.abnamro.nl.favouriterecipe.utility;

import com.abnamro.nl.favouriterecipe.constants.RecipesConstants;
import com.abnamro.nl.favouriterecipe.enums.RecipesErrorEnum;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lakshmimadam
 *
 */


public class RecipesUtility {
	
	/**
	 * This method is used to generate the unique recipe id for each recipe entry
	 * @return
	 */
	public static String generateRecipeId() {
		return UUID.randomUUID().toString();
	}
	
	public static void validateRecipeId(String id) throws RecipesException {
		if(StringUtils.isBlank(id)) {
			throw new RecipesException(RecipesErrorEnum.ERROR_ID_NOT_SPECIFIED, HttpStatus.BAD_REQUEST);
		}
		Pattern pattern = Pattern.compile(RecipesConstants.REGEX_RECIPE);
		Matcher matcher = pattern.matcher(id);
		
		if(id.length()> 50 || !matcher.matches()) {
			throw new RecipesException(RecipesErrorEnum.ERROR_RECIPE_NOT_FOUND, HttpStatus.NOT_FOUND);			
		}
		
	}
	
	public static void validateQueryParams(Map<String, String> queryParams) throws RecipesException {
		if(!CollectionUtils.isEmpty(queryParams)) {
			List<String> keys = new ArrayList<>(queryParams.keySet());
			List<String> allowedQueryKeys = List.of("type","name","servings","ingredient","instruction","includeIngredient");
			for (String query : keys) {
				if(!allowedQueryKeys.contains(query)) {
					throw new RecipesException(RecipesErrorEnum.ERROR_INVALID_QUERY_PARAM,query);
				}
			}
			
		}
	}
}
