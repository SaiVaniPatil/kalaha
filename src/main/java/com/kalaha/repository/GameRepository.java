package com.kalaha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kalaha.model.Game;


public interface GameRepository extends JpaRepository<Game, Long>  {

}
