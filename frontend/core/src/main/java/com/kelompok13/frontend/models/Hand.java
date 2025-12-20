package com.kelompok13.frontend.models;
//data model for players current hand of cards

import com.kelompok13.frontend.card.BaseCard;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<BaseCard> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public List<BaseCard> getCards() {
        return cards;
    }

    //nambah kartu
    public void addCard(BaseCard card) {
        if (card != null) {
            cards.add(card);
        }
    }

    //remove card
    public void removeCard(BaseCard card) {
        cards.remove(card);
    }

    //ambil tertentu
    public BaseCard getCard(int index) {
        if (index < 0 || index >= cards.size()) {
            return null;
        }
        return cards.get(index);
    }

    //jumlah kartu 
    public int size() {
        return cards.size();
    }

    //clear
    public void clear() {
        cards.clear();
    }


}