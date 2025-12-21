package com.kelompok13.frontend.observers.payload;

import java.util.UUID;

public class ScorePayLoad {
    public int score;
    public String stage;
    public ScorePayLoad(int score, String stage) {
        this.score = score;
        this.stage = stage;
    }
}
