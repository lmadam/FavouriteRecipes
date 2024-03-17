package com.abnamro.nl.favouriterecipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


/**
 * @author lakshmimadam
 *
 */
@SpringBootApplication
@EnableMongoRepositories
public class RecipesApplication{

	public static void main(String[] args) {
		SpringApplication.run(RecipesApplication.class, args);
	}
	
}
