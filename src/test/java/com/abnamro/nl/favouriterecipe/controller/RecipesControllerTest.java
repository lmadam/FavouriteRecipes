package com.abnamro.nl.favouriterecipe.controller;

import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import com.abnamro.nl.favouriterecipe.model.RecipesRequest;
import com.abnamro.nl.favouriterecipe.service.RecipesService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * @author lakshmimadam
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore({"javax.management.*"})
public class RecipesControllerTest {
	
	@InjectMocks
	private RecipesController recipesController;
	
	@Mock
	private RecipesService recipesService;
	
	private RecipesRequest recipesRequest;
	
	private RecipesEntity recipesEntity;
	
	private String recipeId = null;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		recipesRequest = new RecipesRequest("vegetarian","Payasam",10,List.of("add 2 spoons ghee"),List.of("milk"));
		recipesEntity = new RecipesEntity();
		recipesEntity.setId("1234567890");
		recipesEntity.setName(recipesRequest.name());
		recipesEntity.setType(recipesRequest.type());
		recipesEntity.setServings(recipesRequest.servings());
		recipesEntity.setInstructions(recipesRequest.instructions());
		recipesEntity.setIngredients(recipesRequest.ingredients());
		recipeId = "1234567890";
	}
	
	@Test
	public void testAddRecipe() throws RecipesException{
		Mockito.when(recipesService.addRecipe(Mockito.any())).thenReturn(recipesEntity);
		Assert.assertEquals(recipesController.addRecipe(recipesRequest).getStatusCode(), HttpStatus.CREATED);
		
	}
	
	@Test
	public void testFetchRecipe() throws RecipesException{
		Mockito.when(recipesService.fetchRecipe(recipeId)).thenReturn(recipesEntity);
		Assert.assertEquals(recipesController.fetchRecipe(recipeId).getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void testSearchRecipes() throws RecipesException{
		Mockito.when(recipesService.searchRecipes(Mockito.anyMap())).thenReturn(List.of(recipesEntity));
		Assert.assertEquals(recipesController.searchRecipes(Map.of("type","vegetarian")).getStatusCode(), HttpStatus.OK);
	}
	
	
	@Test
	public void testUpdateRecipe() throws RecipesException{
		Assert.assertEquals(recipesController.updateRecipe(recipeId, recipesRequest).getStatusCode(), HttpStatus.NO_CONTENT);
	}
	
	
	@Test
	public void testRemoveRecipe() throws RecipesException{
		Assert.assertEquals(recipesController.removeRecipe(recipeId).getStatusCode(), HttpStatus.NO_CONTENT);
	}
	

}
