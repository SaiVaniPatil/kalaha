package com.kalaha.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kalaha.constants.GameUtil;
import com.kalaha.exception.InvalidMoveException;
import com.kalaha.exception.ResourceNotFoundException;
import com.kalaha.model.Game;
import com.kalaha.model.GameInput;
import com.kalaha.model.Pit;
import com.kalaha.model.Player;
import com.kalaha.repository.GameRepository;
import com.kalaha.service.GameService;
import com.kalaha.service.PitService;
import com.kalaha.service.PlayerService;

@Component
public class DefaultGameServiceImpl implements GameService {

	@Autowired
	PlayerService playerService;

	@Autowired
	PitService pitService;

	@Autowired
	GameRepository gameRepository;

	public Game initGame() {

		Game game = gameRepository.save(new Game());

		game.setPits(pitService.initPits(game));

		game.setPlayers(playerService.initPlayers(game));

		return game;

	}

	public Game getGame(Long gameId) {
		return gameRepository.findById(gameId)
				.orElseThrow(() -> new ResourceNotFoundException("Game not exist with id :" + gameId));

	}

	public Game playMove(GameInput gameInput) throws Exception {

		Long gameId = gameInput.getGameId();

		int pitNum = gameInput.getPitNum();

		Game game = getGame(gameId);

		List<Player> players = game.getPlayers();

		Player currPlayer = players.get(game.getCurrPlayerIndex());

		List<Pit> pits = game.getPits();

		Pit pit = pits.get(pitNum - 1);

		validateData(pit, currPlayer);

		int noOfStones = pit.getNoOfStones();

		int totalPits = GameUtil.NO_OF_PLAYERS * GameUtil.NO_OF_BIG_AND_SMALL_PITS_PER_PLAYER;

		pit.setNoOfStones(0);

		while (noOfStones > 0) {

			pitNum = (pitNum) % totalPits;

			pit = pits.get(pitNum);

			if (!pit.isBigPit() || currPlayer.ownsPit(pit.getPitNum())) {

				pit.addStones(1);
				noOfStones--;

			}

			pitNum++;

		}

		if (!pit.isBigPit() && pit.getNoOfStones() == 1 && currPlayer.ownsPit(pit.getPitNum())) {

			Pit currPlayerBigPit = pits.get(currPlayer.getEndPitNum() - 1);

			currPlayerBigPit.addStones(pit.getNoOfStones());

			pit.setNoOfStones(0);

			int currPitNum = pit.getPitNum();

			Pit oppPit = pits.get(totalPits - currPitNum - 1);

			currPlayerBigPit.addStones(oppPit.getNoOfStones());

			oppPit.setNoOfStones(0);

		}

		boolean isGameOver = isGameOver(players, pits);

		if (isGameOver) {
			List<Player> winners = getWinner(players, pits);

			winners.stream().forEach(winner -> winner.setHasWon(true));

			game.setGameEnded(true);
		}

		if (!pit.isBigPit()) {
			int currPlayerIndex = (game.getCurrPlayerIndex() + 1) % GameUtil.NO_OF_PLAYERS;

			game.setCurrPlayerIndex(currPlayerIndex);
		}

		gameRepository.save(game);

		return game;

	}

	private void validateData(Pit pit, Player currPlayer) throws Exception { //

		if (pit.isBigPit()) {
			throw new InvalidMoveException("cannot choose bigpit to sow seeds"); // InvalidMove
		}

		if (pit.getNoOfStones() == 0) {
			throw new InvalidMoveException("cannot choose empty pit to sow seeds");
		}

		long currPitNum = pit.getPitNum();

		int playerStartPitNum = currPlayer.getStartPitNum();

		int playerEndPitNum = currPlayer.getEndPitNum();

		if (currPitNum < playerStartPitNum || currPitNum > playerEndPitNum) {
			throw new InvalidMoveException("cannot choose other players pit to sow seeds, " + "choose between numbers "
					+ playerStartPitNum + " and " + playerEndPitNum);
		}

	}

	private boolean isGameOver(List<Player> players, List<Pit> pits) {

		return players.stream().anyMatch(player -> pits.subList(player.getStartPitNum()-1, player.getEndPitNum() - 1)
				.stream().allMatch(pit -> pit.isBigPit() || pit.getNoOfStones() == 0));
	}

	private List<Player> getWinner(List<Player> players, List<Pit> pits) {

		int maxStonesSum = 0;

		for (Player player : players) {

			int sumOfStones = pits.subList(player.getStartPitNum(), player.getEndPitNum()).stream()
					.mapToInt(Pit::getNoOfStones).sum();			

			if (sumOfStones > maxStonesSum) {
				maxStonesSum = sumOfStones;
			}

			player.setSumOfStones(sumOfStones);
		}

		int maxSum = maxStonesSum;

		List<Player> winners = players.stream().filter(player -> player.getSumOfStones() == maxSum)
				.collect(Collectors.toList());

		return winners;

	}

}
