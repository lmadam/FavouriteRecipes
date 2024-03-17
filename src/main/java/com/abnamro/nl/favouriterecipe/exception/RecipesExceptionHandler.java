package com.abnamro.nl.favouriterecipe.exception;

import com.abnamro.nl.favouriterecipe.enums.RecipesErrorEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

/**
 * @author lakshmimadam
 *
 */

@ControllerAdvice
public class RecipesExceptionHandler {

	/**
	 * This method is used to frame the generic error
	 * 
	 * @param exception
	 * @param status
	 * @return
	 */
	private ResponseEntity<RecipesError> frameGenericError(Exception exception, HttpStatus status) {
		var error = RecipesErrorEnum.TECHNICAL_ERROR;
		var recipesError = new RecipesError(error.getType(), error.getCode(), exception.getMessage());
		return ResponseEntity.status(status).body(recipesError);
	}

	/**
	 * This method is used to frame the application related exceptions
	 * 
	 * @param exception
	 * @param status
	 * @return
	 */
	private ResponseEntity<RecipesError> frameApplicationError(RecipesException exception, HttpStatus status) {
		var error = new RecipesError(exception.getType(), exception.getCode(), exception.getMessage());
		return ResponseEntity.status(status).body(error);
	}

	/**
	 * This method is used to frame the application related exceptions
	 * 
	 * @param exception
	 * @param status
	 * @return
	 */
	private ResponseEntity<RecipesError> frameRuntimeApplicationError(RecipesRuntimeException exception,
			HttpStatus status) {
		var error = new RecipesError(exception.getType(), exception.getCode(), exception.getMessage());
		return ResponseEntity.status(status).body(error);
	}

	@ExceptionHandler(RecipesException.class)
	public ResponseEntity<RecipesError> handleRecipesException(RecipesException exception) {
		return frameApplicationError(exception,
				Objects.nonNull(exception.getStatus()) ? exception.getStatus() : HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RecipesRuntimeException.class)
	public ResponseEntity<RecipesError> handleRecipesRuntimeException(RecipesRuntimeException exception) {
		return frameRuntimeApplicationError(exception,
				Objects.nonNull(exception.getStatus()) ? exception.getStatus() : HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleRecipesException(MethodArgumentNotValidException exception) {
		var errorEnum = RecipesErrorEnum.ERROR_INPUT_VALIDATION;
		var error = new RecipesError(errorEnum.getType(), errorEnum.getCode(),
				exception.getBindingResult().getFieldError().getDefaultMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RecipesError> handleGenericException(Exception exception) {
		return frameGenericError(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
