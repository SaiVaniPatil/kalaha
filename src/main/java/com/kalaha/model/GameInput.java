package com.kalaha.model;

public class GameInput {

	private Long gameId;
	private int pitNum;
	
	public GameInput()
	{
		
	}

	public GameInput(long gameId, int pitNum) {
		this.gameId = gameId;
		this.pitNum = pitNum;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public int getPitNum() {
		return pitNum;
	}

	public void setPitNum(int pitNum) {
		this.pitNum = pitNum;
	}

}
