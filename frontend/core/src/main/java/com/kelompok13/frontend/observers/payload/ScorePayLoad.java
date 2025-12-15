package com.kelompok13.frontend.observers.payload;

public class ScorePayLoad {
    public final int playerId;
    public int score;
    public String stage;
    public ScorePayLoad(int playerId, int score, String stage) {
        this.playerId = playerId;
        this.score = score;
        this.stage = stage;
    }
}
