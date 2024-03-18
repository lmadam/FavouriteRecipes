package com.abnamro.nl.favouriterecipe.controller;

import com.abnamro.nl.favouriterecipe.entity.RecipesEntity;
import com.abnamro.nl.favouriterecipe.exception.RecipesExceptionHandler;
import com.abnamro.nl.favouriterecipe.model.RecipesRequest;
import com.abnamro.nl.favouriterecipe.service.RecipesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class RecipesControllerIT {

    @InjectMocks
    RecipesController recipesController;
    @Mock
    RecipesService recipeService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recipesController)
                .setControllerAdvice(new RecipesExceptionHandler()).build();
    }

    @Test
    void fetchRecipeTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(getRecipeRequest());
        MockHttpServletResponse response = mockMvc
                .perform(get("/favourites/v1/recipes/63c065d1-3460-470c-a91d-5f7ae5af2fae").contentType(APPLICATION_JSON).content(requestJson))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void addRecipeTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(getRecipeRequest());
       RecipesEntity recipes=Mockito.mock(RecipesEntity.class);
        recipes.setId("12345"); // Set a valid ID
        Mockito.when(recipeService.addRecipe(getRecipeRequest())).thenReturn(recipes);
        MockHttpServletResponse response = mockMvc
                .perform(post("/favourites/v1/recipes").contentType(APPLICATION_JSON).content(requestJson))
                .andReturn().getResponse();
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    void updateRecipeTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(getRecipeRequest());
        MockHttpServletResponse response = mockMvc
                .perform(put("/favourites/v1/recipes/63c065d1-3460-470c-a91d-5f7ae5af2fae").contentType(APPLICATION_JSON).content(requestJson))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void removeRecipeTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(getRecipeRequest());
        MockHttpServletResponse response = mockMvc
                .perform(delete("/favourites/v1/recipes/63c065d1-3460-470c-a91d-5f7ae5af2fae").contentType(APPLICATION_JSON).content(requestJson))
                .andReturn().getResponse();
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
    @Test
    void searchRecipeTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(getRecipeRequest());
        MockHttpServletResponse response = mockMvc
                .perform(get("/favourites/v1/recipes?type=vegetarian").contentType(APPLICATION_JSON).content(requestJson))
                .andReturn().getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private RecipesRequest getRecipeRequest() {
        return new RecipesRequest("vegetarian","Payasam",10,List.of("add 2 spoons ghee"),List.of("milk"));
    }
}