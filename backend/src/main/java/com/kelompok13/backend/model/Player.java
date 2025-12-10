package com.kelompok13.backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "players")
public class Player {
    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "high_score")
    private Integer highScore = 0;

    @Column(name = "total_coins")
    private Integer totalCoins = 0;

    @Column(name = "latest_stage")
    private String stage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    //default constructor
    public Player(){}
    //constructor with username
    public Player(String username){
        this.username = username;
    }

    //setter getter
    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getHighScore() {
        return highScore;
    }

    public void setHighScore(Integer highScore) {
        this.highScore = highScore;
    }

    public Integer getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(Integer totalCoins) {
        this.totalCoins = totalCoins;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    //business methods
    public void updateHighScore(Integer newScore){
        if(this.highScore < newScore){
            this.highScore = newScore;
        }
    }

    public void updateStage(String newStage){
        if (!this.stage.equals(newStage)){
            this.stage = newStage;
        }
    }

    public void addCoins(Integer addedCoins){
        this.totalCoins += addedCoins;
    }

}
