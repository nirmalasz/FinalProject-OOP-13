package com.kelompok13.frontend.observers.payload;

import java.util.UUID;

public class HandDiscardPayLoad {
    public int remainingDiscard;
    public int remainingPlay;


    public HandDiscardPayLoad( int remainingDiscard, int remainingPlay) {
        this.remainingDiscard = remainingDiscard;
        this.remainingPlay = remainingPlay;
    }
}
