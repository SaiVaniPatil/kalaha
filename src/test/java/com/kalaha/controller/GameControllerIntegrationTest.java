package com.kalaha.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kalaha.constants.GameUtil;
import com.kalaha.model.Game;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;
	
	private Long gameId;
	
	private Game game;
	
	@BeforeEach
    public void initGame() throws Exception {
        // Send a POST request to create a game
        MvcResult result = mockMvc.perform(post("/kalaha")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Extract the game ID from the response
        String responseContent = result.getResponse().getContentAsString();
        
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);        
        
        game = objectMapper.readValue(responseContent, Game.class);
        gameId = game.getId();
    }

	@Test
	public void initKalahaGame_verifydata_success() throws Exception {
		
		assertEquals(game.getPlayers().size(),GameUtil.NO_OF_PLAYERS);
		assertEquals(game.getPlayers().get(0).getName(),"Player1");
		assertEquals(game.getPits().size(),GameUtil.TOTAL_NO_OF_PITS);
		assertEquals(game.getCurrPlayerIndex(),0);
		assertEquals(game.isGameEnded(),false);


	}

	@Test
	public void playMove_validateMove_success() throws Exception {
		
		String gameInput = "{\"gameId\": " + gameId + ", \"pitNum\": 5}";

		mockMvc.perform(post("/kalaha").contentType(MediaType.APPLICATION_JSON));
		
		mockMvc.perform(patch("/kalaha/play").contentType(MediaType.APPLICATION_JSON).content(gameInput))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.currPlayerIndex").value(1))
				.andExpect(jsonPath("$.pits[4].noOfStones").value(0))
				.andExpect(jsonPath("$.pits[10].noOfStones").value(7))
				.andExpect(jsonPath("$.gameEnded").value(false));
				
		
				
		
	}

}
