package com.kalaha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kalaha.model.Game;
import com.kalaha.model.GameInput;
import com.kalaha.service.GameService;


@CrossOrigin(origins = "http://localhost:8090")
@RestController
@RequestMapping("/v1/kalaha")
public class GameController {

	private final GameService gameService;

	@Autowired
	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@PostMapping
	public Game initKalahaGame() {

		return gameService.initGame();

	}

	@PatchMapping("/play")
	public Game playMove(@RequestBody GameInput gameInput) throws Exception {

		return gameService.playMove(gameInput);
	}

}
