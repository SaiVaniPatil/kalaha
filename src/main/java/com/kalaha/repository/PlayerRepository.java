package com.kalaha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kalaha.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
