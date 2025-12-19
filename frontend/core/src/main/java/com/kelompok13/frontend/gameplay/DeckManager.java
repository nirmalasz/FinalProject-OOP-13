package com.kelompok13.frontend.gameplay;

import com.kelompok13.frontend.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;

//draw card from deck
//determine which card are still in the deck
public class DeckManager {
    List<PlayingCard> playedCards = new ArrayList<>();
    List<PlayingCard> deckCards;
    int handsize = 5;

    //initialize deck manager with full deck. will restart every round
    public DeckManager(List<PlayingCard> fullDeck){
        this.deckCards = fullDeck;
    }
    //to override the 5 if player have special item; might move it to other class later
    public void setHandsize(int handsize) {
        this.handsize = handsize;
    }

    public List<PlayingCard> getDeckCards() {
        return deckCards;
    }


    public void playCards(List<PlayingCard> selectedCards){
        for (PlayingCard card: selectedCards){
            if (selectedCards.contains(card)){
                playedCards.add(card);
                selectedCards.remove(card);
                deckCards.remove(card);
                playedCards.add(card);
            }
        }
    }

    public void discardCards(List<PlayingCard> selectedCards){
        for (PlayingCard card: selectedCards){
            if (selectedCards.contains(card)){
                selectedCards.remove(card);
                deckCards.remove(card);
            }
        }
    }

    //after round ends
    public void disposeDeck(){
        playedCards.clear();
        deckCards.clear();
    }


}
