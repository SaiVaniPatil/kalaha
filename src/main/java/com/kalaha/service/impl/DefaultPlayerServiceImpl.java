package com.kalaha.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalaha.constants.GameUtil;
import com.kalaha.model.Game;
import com.kalaha.model.Player;
import com.kalaha.repository.PlayerRepository;
import com.kalaha.service.PlayerService;

@Component
public class DefaultPlayerServiceImpl implements PlayerService {

	@Autowired
	PlayerRepository playerRepository;

	public List<Player> initPlayers(Game game) {

		List<Player> players = new ArrayList<Player>();

		int startPitNum = 1;

		for (int i = 0; i < GameUtil.NO_OF_PLAYERS; i++) {

			int endPitNum = startPitNum + GameUtil.NO_OF_PITS_PER_PLAYER;

			String name = "Player" + (i + 1);

			Player player = new Player(name, startPitNum, endPitNum, game);

			players.add(player);

			startPitNum = endPitNum + 1;

		}

		return playerRepository.saveAll(players);

	}

}
