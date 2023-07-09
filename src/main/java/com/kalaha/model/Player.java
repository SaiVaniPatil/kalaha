package com.kalaha.model;

import java.util.Objects;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private int startPitNum;

	private int endPitNum;

	private int sumOfStones;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "game_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Game game;

	private boolean hasWon;

	public boolean isHasWon() {
		return hasWon;
	}

	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Player() {

	}

	public Player(String name) {
		this.name = name;
	}

	public Player(String name, int startPitNum, int endPitNum, Game game) {

		this.name = name;
		this.startPitNum = startPitNum;
		this.endPitNum = endPitNum;
		this.game = game;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartPitNum() {
		return startPitNum;
	}

	public void setStartPitNum(int startPitNum) {
		this.startPitNum = startPitNum;
	}

	public int getEndPitNum() {
		return endPitNum;
	}

	public void setEndPitNum(int endPitNum) {
		this.endPitNum = endPitNum;
	}

	public int getSumOfStones() {
		return sumOfStones;
	}

	public void setSumOfStones(int sumOfStones) {
		this.sumOfStones = sumOfStones;
	}

	public Long getId() {
		return id;
	}

	public boolean ownsPit(int pitNum) {

		return pitNum >= startPitNum && pitNum <= endPitNum;

	}

	@Override
	public int hashCode() {
		return Objects.hash(endPitNum, id, name, startPitNum, sumOfStones);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return endPitNum == other.endPitNum && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& startPitNum == other.startPitNum && sumOfStones == other.sumOfStones;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", startPitNum=" + startPitNum + ", endPitNum=" + endPitNum
				+ ", sumOfStones=" + sumOfStones + "]";
	}
}
