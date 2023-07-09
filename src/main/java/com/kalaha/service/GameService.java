package com.kalaha.service;

import org.springframework.stereotype.Component;

import com.kalaha.model.Game;
import com.kalaha.model.GameInput;

@Component
public interface GameService {

	Game initGame();

	Game playMove(GameInput gameInput) throws Exception;
}
