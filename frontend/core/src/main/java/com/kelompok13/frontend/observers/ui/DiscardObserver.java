package com.kelompok13.frontend.observers.ui;

import com.kelompok13.frontend.observers.Observer;
import com.kelompok13.frontend.observers.Event;
import com.kelompok13.frontend.observers.EventType;
import com.kelompok13.frontend.observers.payload.CountPayLoad;

public class DiscardObserver implements Observer {
    @Override
    public void update(Event event) {
        if (event.type == EventType.DISCARD_COUNT) {
            CountPayLoad data = event.getDataAs(CountPayLoad.class);
            System.out.println("Sisa Discard Player " + data.playerId + " : " + data.count);
        }
    }
}
