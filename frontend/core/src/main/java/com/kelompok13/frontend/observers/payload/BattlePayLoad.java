package com.kelompok13.frontend.observers.payload;

import com.kelompok13.frontend.characters.Enemy;

import java.util.UUID;

public class BattlePayLoad {
    public UUID playerId;
    public String enemyName;
    public int multiplier;
    public int finalScore;
    public boolean playerWon;
    public int rewardMoney;

    public BattlePayLoad(UUID playerId, Enemy enemyName, int enemyHp,
        int playerHp, int multiplier) {
        this.playerId = playerId;
        this.enemyName = enemyName.getName();
        this.multiplier = multiplier;
    }
}
