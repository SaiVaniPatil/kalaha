package com.kalaha.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Game {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "game")
	private List<Pit> pits;

	@OneToMany(mappedBy = "game")
	private List<Player> players;

	private int currPlayerIndex;

	private boolean gameEnded;

	private Timestamp creationTime;

	public Game() {

		this.creationTime = new Timestamp(System.currentTimeMillis());

	}

	public Game(List<Pit> pits, List<Player> players) {
		super();
		this.pits = pits;
		this.players = players;
		this.currPlayerIndex = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Pit> getPits() {
		return pits;
	}

	public void setPits(List<Pit> pits) {
		this.pits = pits;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public int getCurrPlayerIndex() {
		return currPlayerIndex;
	}

	public void setCurrPlayerIndex(int currPlayerIndex) {
		this.currPlayerIndex = currPlayerIndex;
	}

	public boolean isGameEnded() {
		return gameEnded;
	}

	public void setGameEnded(boolean gameEnded) {
		this.gameEnded = gameEnded;
	}

	public Timestamp getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Timestamp creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getWinnerNames() {
		return this.getPlayers().stream().filter(player -> player.isHasWon()).map(player -> player.getName())
				.collect(Collectors.joining(", "));
	}

	@Override
	public int hashCode() {
		return Objects.hash(creationTime, currPlayerIndex, gameEnded, id, pits, players);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		return Objects.equals(creationTime, other.creationTime) && currPlayerIndex == other.currPlayerIndex
				&& gameEnded == other.gameEnded && Objects.equals(id, other.id) && Objects.equals(pits, other.pits)
				&& Objects.equals(players, other.players);
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", pits=" + pits + ", players=" + players + ", currPlayerIndex=" + currPlayerIndex
				+ ", gameEnded=" + gameEnded + ", creationTime=" + creationTime + "]";
	}
	
	

}
