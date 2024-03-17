package com.abnamro.nl.favouriterecipe.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lakshmimadam
 *
 */

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().components(new Components())
				.info(new Info().title("Favourite Recipes Application").description(
						"FavouriteRecipes is a microservice application designed to manage the favourite recipes of the user. with the help of this microservice we have exposed REST API's which will allow the user to add, update, fetch, delete and also search the recipes based on mutiple search parameters."));
	}

}
