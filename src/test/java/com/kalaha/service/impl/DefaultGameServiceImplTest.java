package com.kalaha.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kalaha.constants.GameUtil;
import com.kalaha.exception.InvalidMoveException;
import com.kalaha.exception.ResourceNotFoundException;
import com.kalaha.model.Game;
import com.kalaha.model.GameInput;
import com.kalaha.model.Pit;
import com.kalaha.model.Player;
import com.kalaha.repository.GameRepository;
import com.kalaha.service.PitService;
import com.kalaha.service.PlayerService;

class DefaultGameServiceImplTest {

	Game game;
	private List<Player> players;
	private List<Pit> pits;

	@Mock
	private PlayerService playerService;

	@Mock
	private PitService pitService;

	@Mock
	private GameRepository gameRepository;

	@InjectMocks
	private DefaultGameServiceImpl gameService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		game = new Game();

		players = new ArrayList<>();
		int startPitNum = 1;
		for (int i = 0; i < GameUtil.NO_OF_PLAYERS; i++) {
			int endPitNum = startPitNum + GameUtil.NO_OF_PITS_PER_PLAYER;
			String name = "Player" + (i + 1);
			Player player = new Player(name, startPitNum, endPitNum, game);
			players.add(player);
			startPitNum = endPitNum + 1;
		}

		pits = new ArrayList<>();

		for (int pitNum = 1; pitNum <= GameUtil.TOTAL_NO_OF_PITS; pitNum++) {
			int stonesPerPit = GameUtil.NO_OF_STONES_PER_PIT;
			boolean isBigPit = pitNum % GameUtil.NO_OF_BIG_AND_SMALL_PITS_PER_PLAYER == 0;
			if (isBigPit) {
				stonesPerPit = 0;
			}
			Pit pit = new Pit(pitNum, stonesPerPit, isBigPit, game);
			pits.add(pit);
		}

		game.setPlayers(players);
		game.setPits(pits);
	}

	@Test
	public void initGame_ValidateGameCreation_Success() {
		
		
		when(gameRepository.save(any(Game.class))).thenReturn(game);
		when(pitService.initPits(any(Game.class))).thenReturn(pits);
		when(playerService.initPlayers(any(Game.class))).thenReturn(players);

		
		Game result = gameService.initGame();
		
		verify(gameRepository).save(any(Game.class));
		verify(pitService).initPits(any(Game.class));
		verify(playerService).initPlayers(any(Game.class));
		assertEquals(game, result);
	}

	@Test
	public void getGame_GameIdExists_Success() {

		Long gameId = game.getId();

		when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));

		Game result = gameService.getGame(gameId);

		verify(gameRepository).findById(gameId);
		assertEquals(game, result);
	}

	@Test
	public void getGame_GameIdDoesNotExist_Success() {

		Long gameId = 10L;
		when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> gameService.getGame(gameId));

		verify(gameRepository).findById(gameId);
	}

	@Test
	public void playMove_ValidMove_Success() throws Exception {

		GameInput gameInput = new GameInput(1L, 5);

		when(gameRepository.findById(gameInput.getGameId())).thenReturn(Optional.of(game));

		Game result = gameService.playMove(gameInput);

		verify(gameRepository).findById(gameInput.getGameId());

		assertEquals(result.getCurrPlayerIndex(), 1);
		assertEquals(result.getPits().get(4).getNoOfStones(), 0);
		assertEquals(result.getPits().get(6).getNoOfStones(), 1);
		assertEquals(result.getPits().get(10).getNoOfStones(), 7);
		assertEquals(result.isGameEnded(), false);

	}

	@Test
	public void playMove_ValidMove_LastStoneInBigPit_Success() throws Exception {

		GameInput gameInput = new GameInput(1L, 1);

		when(gameRepository.findById(gameInput.getGameId())).thenReturn(Optional.of(game));

		Game result = gameService.playMove(gameInput);

		verify(gameRepository).findById(gameInput.getGameId());

		assertEquals(result.getCurrPlayerIndex(), 0);
		assertEquals(result.getPits().get(0).getNoOfStones(), 0);
		assertEquals(result.getPits().get(1).getNoOfStones(), 7);
		assertEquals(result.getPits().get(6).getNoOfStones(), 1);
		assertEquals(result.getPits().get(7).getNoOfStones(), 6);
		assertEquals(result.isGameEnded(), false);

	}

	@Test
	public void playMove_InvalidMove_CannotChooseBigPit_Failure() throws Exception {

		GameInput gameInput = new GameInput(1L, 7);

		when(gameRepository.findById(gameInput.getGameId())).thenReturn(Optional.of(game));

		assertThrows(InvalidMoveException.class, () -> gameService.playMove(gameInput));

	}

	@Test
	public void playMove_InvalidMove_CannotChooseEmptyPit_Failure() throws Exception {

		GameInput gameInput = new GameInput(1L, 1);

		when(gameRepository.findById(gameInput.getGameId())).thenReturn(Optional.of(game));

		gameService.playMove(gameInput);

		GameInput newGameInput = new GameInput(1L, 1);

		assertThrows(InvalidMoveException.class, () -> gameService.playMove(newGameInput));

	}

	@Test
	public void playMove_InvalidMove_CannotChooseOthersPit_Failure() throws Exception {

		GameInput gameInput = new GameInput(1L, 8);

		when(gameRepository.findById(gameInput.getGameId())).thenReturn(Optional.of(game));

		assertThrows(InvalidMoveException.class, () -> gameService.playMove(gameInput));

	}

	@Test
	public void playMove_ValidMove_CaptureStones_Success() throws Exception {

		GameInput gameInput = new GameInput(1L, 1);

		game.getPits().get(0).setNoOfStones(3);
		game.getPits().get(3).setNoOfStones(0);

		when(gameRepository.findById(gameInput.getGameId())).thenReturn(Optional.of(game));

		Game result = gameService.playMove(gameInput);

		verify(gameRepository).findById(gameInput.getGameId());

		assertEquals(result.getCurrPlayerIndex(), 1);
		assertEquals(result.getPits().get(0).getNoOfStones(), 0);
		assertEquals(result.getPits().get(3).getNoOfStones(), 0);
		assertEquals(result.getPits().get(6).getNoOfStones(), 7);
		assertEquals(result.getPits().get(9).getNoOfStones(), 0);
		assertEquals(result.isGameEnded(), false);

	}

}
