package com.kelompok13.frontend.observers.payload;

public class CountPayLoad {
    // for both discard and play count
    public final int playerId;
    public int count;
    public CountPayLoad(int playerId, int count) {
        this.playerId = playerId;
        this.count = count;
    }
}
