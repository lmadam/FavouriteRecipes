package com.abnamro.nl.favouriterecipe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lakshmimadam
 *
 */

@Document(collection = "FavouriteRecipes")
@Data
@JsonInclude(value = Include.NON_NULL)
public class RecipesEntity {
	
	@Id
	@Schema(description = "Unique recipe id",example = "84898345896796798")
	private String id;
	
	@Schema(description = "Name of the recipe",example = "Biryani")
	private String name;
	
	@Schema(description = "Type of the recipes vegetarian/non vegetarian",example = "Vegetarian")
	private String type;
	
	@Schema(description = "Number of servings",example = "20")
	private int servings;
	
	@Schema(description = "List of ingredients",example = "[salt,pepper]")
	private List<String> ingredients;

	
	@Schema(description = "List of instructions",example = "[add water,add salt]")
	private List<String> instructions;
	
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Schema(description = "created date",example = "2022-07-18T19:36:49.588Z")
	private LocalDateTime createdDate;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Schema(description = "updated date",example = "2022-07-18T19:36:49.588Z")
	private LocalDateTime updatedDate;

}
