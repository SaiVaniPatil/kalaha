package com.kalaha.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kalaha.constants.GameUtil;
import com.kalaha.model.Game;
import com.kalaha.model.Player;
import com.kalaha.repository.PlayerRepository;
import com.kalaha.service.PitService;
import com.kalaha.service.PlayerService;

class DefaultPlayerServiceImplTest {

	private Game game;
	private List<Player> expectedPlayers;

	@Mock
	private PlayerRepository playerRepository;

	@InjectMocks
	private DefaultPlayerServiceImpl playerService;;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		game = new Game();
		expectedPlayers = new ArrayList<>();
		int startPitNum = 1;
		for (int i = 0; i < GameUtil.NO_OF_PLAYERS; i++) {
			int endPitNum = startPitNum + GameUtil.NO_OF_PITS_PER_PLAYER;
			String name = "Player" + (i + 1);
			Player player = new Player(name, startPitNum, endPitNum, game);
			expectedPlayers.add(player);
			startPitNum = endPitNum + 1;
		}

	}

	@AfterEach
	void teardown()
	{
		expectedPlayers.clear();
	}

	@Test
	void initPlayers_ValidatePlayersInitialization_Success() {
     
        when(playerRepository.saveAll(anyList())).thenReturn(expectedPlayers);

		List<Player> players = playerService.initPlayers(game);

		assertEquals(GameUtil.NO_OF_PLAYERS, players.size());
		
		 verify(playerRepository).saveAll(expectedPlayers);
		
		 assertEquals(expectedPlayers, players);
		
	}

	@Test
	public void initPlayers_ValidatePlayersInitialization_Failure() {
	

		when(playerRepository.saveAll(anyList())).thenThrow(new RuntimeException("Failed to save players"));
	
		assertThrows(RuntimeException.class, () -> playerService.initPlayers(game));
		
		verify(playerRepository).saveAll(expectedPlayers);
	}

}
