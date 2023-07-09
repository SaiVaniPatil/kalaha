package com.kalaha.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalaha.constants.GameUtil;
import com.kalaha.model.Game;
import com.kalaha.model.Pit;
import com.kalaha.repository.PitRepository;
import com.kalaha.service.PitService;

@Component
public class DefaultPitServiceImpl implements PitService {

	@Autowired
	PitRepository pitRepository;

	public List<Pit> initPits(Game game) {

		List<Pit> pits = new ArrayList<>();

		int pitNum = 1;

		int noOfPits = GameUtil.TOTAL_NO_OF_PITS;

		while (pitNum <= noOfPits) {
			
			int stonesPerPit = GameUtil.NO_OF_STONES_PER_PIT;

			boolean isBigPit = pitNum % GameUtil.NO_OF_BIG_AND_SMALL_PITS_PER_PLAYER == 0;
			
			if(isBigPit) stonesPerPit  =0;

			Pit pit = new Pit(pitNum, stonesPerPit, isBigPit, game);

			pits.add(pit);

			pitNum++;
		}

		return pitRepository.saveAll(pits);

	}

}
