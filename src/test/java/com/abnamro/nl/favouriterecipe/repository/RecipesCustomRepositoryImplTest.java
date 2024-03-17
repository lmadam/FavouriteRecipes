package com.abnamro.nl.favouriterecipe.repository;

import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore({ "javax.management.*" })
public class RecipesCustomRepositoryImplTest {
	
	@InjectMocks
	private RecipesCustomRepositoryImpl recipesRepositoryImpl;
	
	@Mock
	private MongoTemplate mongoTemplate;
	
	private RecipesEntity recipesEntity;
	
	@Before
	public void setUp() {
		
	    recipesEntity = new RecipesEntity();
		recipesEntity.setId("1234567890");
		recipesEntity.setName("Payasam");
		recipesEntity.setType("vegetarian");
		recipesEntity.setServings(10);
		recipesEntity.setInstructions(List.of("add 2 spoons ghee"));
		recipesEntity.setIngredients(List.of("milk"));
		
	}
	
	@Test
	public void searchRecipesTest() throws RecipesException { 
		String recipeId = "1234567890";
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("type", "vegetarian");
		queryMap.put("name", "Payasam");
		queryMap.put("servings", "10");
		queryMap.put("ingredient", "milk");
		queryMap.put("instruction", "add 2 spoons ghee");
		queryMap.put("includeIngredient", "Y");
		Mockito.when(mongoTemplate.find(Mockito.any(Query.class), Mockito.any())).thenReturn(List.of(recipesEntity));
		Assert.assertEquals(recipesRepositoryImpl.searchRecipes(queryMap).get(0).getId(),recipeId);
		
	}
	
	@Test
	public void searchRecipesCase1Test() throws RecipesException { 
		String recipeId = "1234567890";
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("type", "vegetarian");
		queryMap.put("name", "Payasam");
		queryMap.put("servings", "10");
		queryMap.put("ingredient", "milk");
		queryMap.put("instruction", "add 2 spoons ghee");
		queryMap.put("includeIngredient", "N");
		Mockito.when(mongoTemplate.find(Mockito.any(Query.class), Mockito.any())).thenReturn(List.of(recipesEntity));
		Assert.assertEquals(recipesRepositoryImpl.searchRecipes(queryMap).get(0).getId(),recipeId);
		
	}
	
	@Test(expected = RecipesException.class)
	public void searchRecipesExceptionTest() throws RecipesException{
		String recipeId = "1234567890";
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("ingredient", "veg");
		queryMap.put("instruction", "");
		queryMap.put("includeIngredient", "");
		queryMap.put("servings", "A");
		Assert.assertEquals(recipesRepositoryImpl.searchRecipes(queryMap).get(0).getId(),recipeId);
		
	}
	
	@Test(expected = Exception.class)
	public void searchRecipesExceptionCase1Test() throws RecipesException{
		String recipeId = "1234567890";
		Map<String, String> queryMap = new HashMap<String, String>();
		Assert.assertEquals(recipesRepositoryImpl.searchRecipes(queryMap).get(0).getId(),recipeId);
		
	}
	
}
