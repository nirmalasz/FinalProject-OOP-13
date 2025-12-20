package com.kelompok13.frontend.observers.payload;

import java.util.UUID;

public class ScorePayLoad {
    public UUID playerId;
    public int score;
    public String stage;
    public ScorePayLoad(UUID playerId, int score, String stage) {
        this.playerId = playerId;
        this.score = score;
        this.stage = stage;
    }
}
