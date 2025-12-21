package com.kelompok13.frontend.observers.ui;

import com.kelompok13.frontend.observers.Observer;
import com.kelompok13.frontend.observers.Event;
import com.kelompok13.frontend.observers.EventType;
import com.kelompok13.frontend.observers.payload.HandDiscardPayLoad;

public class DiscardObserver implements Observer {
    @Override
    public void update(Event event) {
        if (event.type == EventType.DISCARD_COUNT) {
            HandDiscardPayLoad data = event.getDataAs(HandDiscardPayLoad.class);
            System.out.println("Sisa Discard Player " + " : Discard=" + data.remainingDiscard +
                ", Play=" + data.remainingPlay);
        }
    }
}
