package com.kelompok13.frontend.observers.ui;

import com.kelompok13.frontend.observers.Observer;
import com.kelompok13.frontend.observers.Event;
import com.kelompok13.frontend.observers.EventType;
import com.kelompok13.frontend.observers.payload.ShopPayLoad;

public class ShopUIObserver implements Observer {
    @Override
    public void update(Event event) {
        if (event.type == EventType.SHOP_UPDATE) {
            ShopPayLoad data = event.getDataAs(ShopPayLoad.class);
            System.out.println("UI Update: Item baru dibeli -> " + data.item.getName());
        }
    }
}
