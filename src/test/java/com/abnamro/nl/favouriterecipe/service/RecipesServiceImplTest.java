package com.abnamro.nl.favouriterecipe.service;

import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.exception.RecipesException;
import com.abnamro.nl.favouriterecipe.mapper.RecipeMapper;
import com.abnamro.nl.favouriterecipe.model.RecipesRequest;
import com.abnamro.nl.favouriterecipe.repository.RecipesCustomRepository;
import com.abnamro.nl.favouriterecipe.repository.RecipesRepository;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author lakshmimadam
 *
 */
@RunWith(MockitoJUnitRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore({"javax.management.*"})
public class RecipesServiceImplTest {

	@InjectMocks
	private RecipesServiceImpl recipesServiceImpl;

	@Mock
	private RecipesRepository recipesRepository;

	@Mock
	private RecipesCustomRepository recipesCustomRepository;

	@Mock
	private RecipeMapper recipeMapper;

	private RecipesRequest recipesRequest;

	private RecipesEntity recipesEntity;

	private String recipeId = null;

	@Before
	public void setup() {
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
	public void addRecipeTest() throws RecipesException{
		
		Optional<RecipesEntity> entity = Optional.of(recipesEntity);
		
		Mockito.when(recipeMapper.toRecipesEntity(recipesRequest)).thenReturn(recipesEntity);
		
		Mockito.when(recipesRepository.save(entity.get())).thenReturn(entity.get());
		
		Assert.assertEquals(recipesServiceImpl.addRecipe(recipesRequest).getName(), recipesRequest.name());
		
	}
	
	@Test
	public void fetchRecipeTest() throws RecipesException{
		
		Optional<RecipesEntity> entity = Optional.of(recipesEntity);
		
		Mockito.when(recipesRepository.findById(recipeId)).thenReturn(entity);
		
		Assert.assertEquals(recipesServiceImpl.fetchRecipe(recipeId).getId(), recipeId);
		
	}
	
	@Test(expected = RecipesException.class)
	public void fetchRecipeExceptionTest() throws RecipesException{
		
		Mockito.when(recipesRepository.findById(recipeId)).thenReturn(Optional.empty());
		
		Assert.assertEquals(recipesServiceImpl.fetchRecipe(recipeId).getId(), recipeId);
		
	}
	
	@Test
	public void searchRecipesTest() throws RecipesException{
		
		List<RecipesEntity> recipes = List.of(recipesEntity);
		
		Mockito.when(recipesRepository.findAll()).thenReturn(recipes);
		
		Mockito.when(recipesCustomRepository.searchRecipes(Mockito.anyMap())).thenReturn(recipes);
		
		Assert.assertEquals(recipesServiceImpl.searchRecipes(null).get(0).getId(), recipeId);
		
		Assert.assertEquals(recipesServiceImpl.searchRecipes(Map.of("type","vegetarian")).get(0).getId(), recipeId);
		
		
	}
	
	@Test(expected = RecipesException.class)
	public void searchRecipesExceptionTest() throws RecipesException{
		
		
		Mockito.when(recipesRepository.findAll()).thenReturn(null);
		
		Assert.assertEquals(recipesServiceImpl.searchRecipes(null).get(0).getId(), recipeId);
		
		Assert.assertEquals(recipesServiceImpl.searchRecipes(Map.of("type","vegetarian")).get(0).getId(), recipeId);
		
		
	}
	
	@Test
	public void updateRecipeTest() throws RecipesException{
		
		recipesEntity.setId(recipeId);
		
		Optional<RecipesEntity> entity = Optional.of(recipesEntity);
		
		Mockito.when(recipeMapper.toRecipesEntity(recipesRequest)).thenReturn(recipesEntity);
		
		Mockito.when(recipesRepository.findById(recipeId)).thenReturn(entity);
		
		Mockito.when(recipesRepository.save(entity.get())).thenReturn(entity.get());
		
		recipesServiceImpl.updateRecipe(recipeId, recipesRequest);	
	}
	
	@Test(expected = RecipesException.class)
	public void updateRecipeExceptionTest() throws RecipesException{
		
		Mockito.when(recipesRepository.findById(recipeId)).thenReturn(Optional.empty());
		
		Assert.assertNotNull(recipeId);
		
		recipesServiceImpl.updateRecipe(recipeId, recipesRequest);	
	}
	
	
	@Test
	public void deleteRecipeTest() throws RecipesException{
		
		Optional<RecipesEntity> entity = Optional.of(recipesEntity);
		
		Mockito.when(recipesRepository.findById(recipeId)).thenReturn(entity);
		
		recipesServiceImpl.removeRecipe(recipeId);

		
	}
	
	@Test(expected = RecipesException.class)
	public void deleteRecipeExceptionTest() throws RecipesException{
		
		Mockito.when(recipesRepository.findById(recipeId)).thenReturn(Optional.empty());
		
		recipesServiceImpl.removeRecipe(recipeId);

		
	}

}
