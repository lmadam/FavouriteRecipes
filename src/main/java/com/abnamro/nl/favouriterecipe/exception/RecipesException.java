package com.abnamro.nl.favouriterecipe.exception;

import com.abnamro.nl.favouriterecipe.enums.RecipesErrorEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * @author lakshmimadam
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class RecipesException extends Exception{

	private static final long serialVersionUID = 8452822141247965381L;
	
	private String type;
	
	private String code;
	
	private String message;
	
	private HttpStatus status;
	
	public RecipesException(RecipesErrorEnum errorEnum) {
		super();
		this.code = errorEnum.getCode();
		this.type = errorEnum.getType();
		this.message = errorEnum.getMessage();
	}
	
	public RecipesException(RecipesErrorEnum errorEnum, HttpStatus status) {
		super();
		this.code = errorEnum.getCode();
		this.type = errorEnum.getType();
		this.message = errorEnum.getMessage();
		this.status = status;
	}
	
	public RecipesException(RecipesErrorEnum errorEnum, String parameter) {
		super();
		this.code = errorEnum.getCode();
		this.type = errorEnum.getType();
		this.message = errorEnum.getMessage().replace("[param]", parameter);
	}

}
