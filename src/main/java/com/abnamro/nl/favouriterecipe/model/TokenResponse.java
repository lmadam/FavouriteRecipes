package com.abnamro.nl.favouriterecipe.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author lakshmimadam
 *
 */
public record TokenResponse (
	@Schema(description = "Generated JWT",example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyZWNpcGVzYWRtaW4iLCJpYXQiOjE2NTgxNjYxNDksImV4cCI6MTY1ODM0NjE0OX0.K9xOGOzVYSO7myuAPoXOfMQdSKTjz0E_Kwg4-Co8X0k")
	 String token){

}
