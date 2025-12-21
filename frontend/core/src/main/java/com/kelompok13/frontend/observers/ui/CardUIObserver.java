package com.kelompok13.frontend.observers.ui;

import com.kelompok13.frontend.observers.Observer;
import com.kelompok13.frontend.observers.Event;
import com.kelompok13.frontend.observers.EventType;
import com.kelompok13.frontend.observers.payload.PlayingCardsPayLoad;
import com.kelompok13.frontend.card.PlayingCard;

public class CardUIObserver implements Observer {
    @Override
    public void update(Event event) {
        if (event.type == EventType.CARD_UPDATE) {
            PlayingCardsPayLoad data = event.getDataAs(PlayingCardsPayLoad.class);

            System.out.println("Updating tampilan kartu untuk Player: " + data.playerId);

            for (PlayingCard card : data.cards) {
                System.out.println("- Menampilkan Kartu: " + card.getRank() + " of " + card.getSuit());
            }
            renderCardsToScreen(data);
        }
    }

    private void renderCardsToScreen(PlayingCardsPayLoad data) {
        // Logika render spesifik untuk LibGDX
    }
}
