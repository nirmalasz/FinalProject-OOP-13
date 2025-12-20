package com.kelompok13.frontend.factories;

import com.kelompok13.frontend.factories.item.Item1Creator;
import com.kelompok13.frontend.factories.item.Item2Creator;
import com.kelompok13.frontend.factories.item.Item3Creator;
import com.kelompok13.frontend.item.BaseItem;

public class ItemFactory {

    public static BaseItem createItem(int itemId) {

        switch (itemId) {
            case 1:
                return new Item1Creator().createItem();
            case 2:
                return new Item2Creator().createItem();
            case 3:
                return new Item3Creator().createItem();
            default:
                throw new IllegalArgumentException("No item id");
        }
    }
}