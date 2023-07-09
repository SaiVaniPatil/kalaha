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
public class Pit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int pitNum; // 1- 14 private String pitIdentifier;

	private int noOfStones; //

	private boolean isBigPit; //

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "game_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Game game;

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public Pit() {

	}

	public Pit(int pitNum, int noOfStones, boolean isBigPit, Game game) {

		this.game = game;
		this.pitNum = pitNum;
		this.isBigPit = isBigPit;
		this.noOfStones = noOfStones;

	}

	public int getPitNum() {
		return pitNum;
	}

	public void setPitNum(int pitNum) {
		this.pitNum = pitNum;
	}

	public int getNoOfStones() {
		return noOfStones;
	}

	public void setNoOfStones(int noOfStones) {
		this.noOfStones = noOfStones;
	}

	public void addStones(int noOfStones) {
		this.noOfStones += noOfStones;
	}

	public boolean isBigPit() {
		return isBigPit;
	}

	public void setBigPit(boolean isBigPit) {
		this.isBigPit = isBigPit;
	}

	public Long getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isBigPit, noOfStones, pitNum);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pit other = (Pit) obj;
		return Objects.equals(id, other.id) && isBigPit == other.isBigPit && noOfStones == other.noOfStones
				&& pitNum == other.pitNum;
	}

	@Override
	public String toString() {
		return "Pit [id=" + id + ", pitNum=" + pitNum + ", noOfStones=" + noOfStones + ", isBigPit=" + isBigPit + "]";
	}

}
