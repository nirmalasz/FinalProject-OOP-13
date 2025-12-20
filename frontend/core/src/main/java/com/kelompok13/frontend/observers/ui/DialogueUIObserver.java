package com.kelompok13.frontend.observers.ui;

import com.kelompok13.frontend.observers.Observer;
import com.kelompok13.frontend.observers.Event;
import com.kelompok13.frontend.observers.EventType;
import com.kelompok13.frontend.observers.payload.DialoguePayLoad;

public class DialogueUIObserver implements Observer {
    @Override
    public void update(Event event) {
        if (event.type == EventType.DIALOGUE_UPDATE) {
            DialoguePayLoad data = event.getDataAs(DialoguePayLoad.class);
            if(data.show) {
                System.out.println("[" + data.username + "]: " + data.text);
            } else {
                System.out.println("Menutup dialog.");
            }
        }
    }
}
