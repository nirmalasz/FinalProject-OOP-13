package com.kelompok13.frontend.models;

import com.kelompok13.frontend.card.JokerCard;
import com.kelompok13.frontend.item.BaseItem;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<BaseItem> items;
    private List<JokerCard> jokerCards;
    private int maxItemsSlot;
    private int maxJokerSlots;

    public Inventory(int maxItemsSlot, int maxJokerSlots) {
        this.items = new ArrayList<>();
        this.jokerCards = new ArrayList<>();
        this.maxItemsSlot = maxItemsSlot;
        this.maxJokerSlots = maxJokerSlots;
    }

    public boolean addItem(BaseItem item) {
        if (items.size() < maxItemsSlot) {
            items.add(item);
            return true;
        }
        return false; // Inventory full
    }

    public boolean addJoker(JokerCard jokerCard) {
        if (jokerCards.size() < maxJokerSlots) {
            jokerCards.add(jokerCard);
            return true;
        }
        return false; // Inventory full
    }

    public void removeItem(BaseItem item) {
        System.out.println("Removing item: " ); //item.getItemName());
        items.remove(item);
    }

    public void removeJoker(JokerCard jokerCard) {
        System.out.println("Removing joker card: " + jokerCard.getName());
        jokerCards.remove(jokerCard);
    }

    public List<BaseItem> getItems() {
        return items;
    }
    public List<JokerCard> getJokerCards() {
        return jokerCards;
    }
    public boolean isItemSlotFull() {
        return items.size() >= maxItemsSlot;
    }

    public boolean isJokerSlotFull() {
        return jokerCards.size() >= maxJokerSlots;
    }
}
