package com.kelompok13.frontend.observers.ui;

import com.kelompok13.frontend.observers.Observer;
import com.kelompok13.frontend.observers.Event;
import com.kelompok13.frontend.observers.EventType;
import com.kelompok13.frontend.observers.payload.HandDiscardPayLoad;

public class PlayUIObserver implements Observer {
    @Override
    public void update(Event event) {
        if (event.type == EventType.PLAY_COUNT) {
            HandDiscardPayLoad data = event.getDataAs(HandDiscardPayLoad.class);
            System.out.println("Kartu dimainkan oleh Player " + data.playerId + ". Count: " + data.count);
        }
    }
}
