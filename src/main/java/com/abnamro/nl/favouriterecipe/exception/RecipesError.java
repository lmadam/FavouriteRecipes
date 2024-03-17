package com.abnamro.nl.favouriterecipe.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lakshmimadam
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipesError implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8696395900252392450L;

	
	@Schema(description = "Type of Error TECHNICAL/FUNCTIONAL",example = "TECHNICAL")
	private String type;
	
	
	@Schema(description = "Error code for each specific error",example = "ERROR_TECHNICAL_FAILURE")
	private String code;
	
	
	@Schema(description = "Error mrssage description",example = "Technical Failure")
	private String message;

}
