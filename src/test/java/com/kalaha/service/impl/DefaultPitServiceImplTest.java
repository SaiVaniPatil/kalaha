package com.kalaha.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
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
import com.kalaha.model.Pit;
import com.kalaha.repository.PitRepository;

class DefaultPitServiceImplTest {

	private Game game;
	private List<Pit> expectedPits;

	@Mock
	private PitRepository pitRepository;

	@InjectMocks
	private DefaultPitServiceImpl pitService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		game = new Game();
		expectedPits = new ArrayList<>();

		for (int pitNum = 1; pitNum <= GameUtil.TOTAL_NO_OF_PITS; pitNum++) {
			int stonesPerPit = GameUtil.NO_OF_STONES_PER_PIT;
			boolean isBigPit = pitNum % GameUtil.NO_OF_BIG_AND_SMALL_PITS_PER_PLAYER == 0;
			if (isBigPit) {
				stonesPerPit = 0;
			}
			Pit pit = new Pit(pitNum, stonesPerPit, isBigPit, game);
			expectedPits.add(pit);
		}
	}

	@AfterEach
	void teardown() {
		expectedPits.clear();
	}

	@Test
	void initPits_ValidatePitsInitialization_Success() {		
		
        when(pitRepository.saveAll(anyList())).thenReturn(expectedPits);

		List<Pit> pits = pitService.initPits(game);

		assertNotNull(pits);
		
		assertEquals(GameUtil.TOTAL_NO_OF_PITS, pits.size());
		
		 verify(pitRepository).saveAll(expectedPits);
		 
		 assertEquals(expectedPits, pits);
	}

	@Test
	public void initPits_ValidatePitsInitialization_Failure() {
	

		when(pitRepository.saveAll(anyList())).thenThrow(new RuntimeException("Failed to save players"));
	
		assertThrows(RuntimeException.class, () -> pitService.initPits(game));
		
		verify(pitRepository).saveAll(expectedPits);
	}

}
