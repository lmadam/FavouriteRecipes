package com.abnamro.nl.favouriterecipe.mapper;


import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.model.RecipesRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
	
	RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);
	
	RecipesEntity fromRecipesRequest(RecipesRequest recipesRequest);
	RecipesRequest toRecipesRequest(RecipesEntity recipesEntity);

}
