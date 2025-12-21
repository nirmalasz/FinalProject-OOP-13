package com.kelompok13.frontend.models;

import com.kelompok13.frontend.card.CardRank;
import com.kelompok13.frontend.card.CardSuit;
import com.kelompok13.frontend.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;

//data model for deck of cards
public class Deck {
    List<PlayingCard> cardList = new ArrayList<>();

    public Deck() {
        initializeDeck();
    }

    private void initializeDeck(){
        for(CardSuit suit: CardSuit.values()){
            for (CardRank rank: CardRank.values()){
                cardList.add(new PlayingCard(suit, rank));
            }
        }
    }

    public List<PlayingCard> getCardList() {
        return cardList;
    }

    private void disposeDeck(List<PlayingCard> cards){
        cards.clear();
    }
}

