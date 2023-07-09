package com.kalaha.service;

import java.util.List;

import com.kalaha.model.Game;
import com.kalaha.model.Pit;

public interface PitService {
	
	List<Pit> initPits(Game game);
	
}
