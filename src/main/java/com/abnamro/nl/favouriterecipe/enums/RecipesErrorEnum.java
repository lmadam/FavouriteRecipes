package com.abnamro.nl.favouriterecipe.enums;

/**
 * @author lakshmimadam
 *
 */
public enum RecipesErrorEnum {
	ERROR_ID_NOT_SPECIFIED("TECHNICAL","ERROR_ID_NOT_SPECIFIED","id not specified"),
	ERROR_RECIPE_NOT_FOUND("FUNCTIONAL","ERROR_RECIPE_NOT_FOUND","Recipe not found"),
	ERROR_NO_RECIPE_RESULTS("FUNCTIONAL","ERROR_NO_RECIPE_RESULTS","No matching recipes found"),
	ERROR_INPUT_VALIDATION("TECHNICAL","ERROR_INPUT_VALIDATION","Input validation"),
	ERROR_INVALID_QUERY_PARAM("TECHNICAL","ERROR_INVALID_QUERY_PARAM","[param] query paramter is invalid"),
	ERROR_INVALID_AUTH("TECHNICAL","ERROR_INVALID_AUTH","Invalid Authorization Token"),
	TECHNICAL_ERROR("TECHNICAL","TECHNICAL_ERROR","Technical Failure");
	
	String type;
	
	String code;
	
	String message;
	
	
	private RecipesErrorEnum(String type, String code, String message) {
		this.type = type;
		this.code = code;
		this.message = message;
	}


	public String getType() {
		return type;
	}


	public String getCode() {
		return code;
	}


	public String getMessage() {
		return message;
	}
	
	

}
