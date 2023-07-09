package com.kalaha.service;

import java.util.List;

import com.kalaha.model.Game;
import com.kalaha.model.Player;

public interface PlayerService {

	List<Player> initPlayers(Game game);

}
