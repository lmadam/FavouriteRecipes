package com.abnamro.nl.favouriterecipe.repository;

import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lakshmimadam
 *
 */

@Repository
public interface RecipesRepository extends MongoRepository<RecipesEntity, String>{
	
}
