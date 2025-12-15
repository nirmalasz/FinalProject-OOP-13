package com.kelompok13.frontend.observers.payload;

public class CardPayLoad {
    public final int cardIndex;
    public final String state; //selected, played, discarded, in deck
    public CardPayLoad(int cardIndex, String state) {
        this.cardIndex = cardIndex;
        this.state = state;
    }
}
