package com.kelompok13.frontend.observers.payload;

import java.util.UUID;

public class HandDiscardPayLoad {
    public int remainingDiscard;
    public int remainingPlay;

    // for both discard and play count
    public UUID playerId;

    public HandDiscardPayLoad(UUID playerId, int remainingDiscard, int remainingPlay) {
        this.playerId = playerId;
        this.remainingDiscard = remainingDiscard;
        this.remainingPlay = remainingPlay;
    }
}
