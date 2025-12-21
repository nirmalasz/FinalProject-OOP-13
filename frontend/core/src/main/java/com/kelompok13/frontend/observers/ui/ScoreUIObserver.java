package com.kelompok13.frontend.observers.ui;

import com.kelompok13.frontend.observers.Observer;
import com.kelompok13.frontend.observers.Event;
import com.kelompok13.frontend.observers.EventType;
import com.kelompok13.frontend.observers.payload.ScorePayLoad;

public class ScoreUIObserver implements Observer {
    @Override
    public void update(Event event) {
        if (event.type == EventType.SCORE_UPDATE) {
            ScorePayLoad data = event.getDataAs(ScorePayLoad.class);
            System.out.println("Player Score: " + data.score + " on Stage: " + data.stage);
            // Nanti di sini panggil fungsi render LibGDX
        }
    }
}
