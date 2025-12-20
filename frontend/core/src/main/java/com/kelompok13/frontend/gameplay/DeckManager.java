package com.kelompok13.frontend.gameplay;

import com.kelompok13.frontend.card.PlayingCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//draw card from deck
//determine which card are still in the deck
public class DeckManager {
    List<PlayingCard> discardPile;
    List<PlayingCard> deckCards;
    List<PlayingCard> currentHand;
    int handsize = 5;

    //initialize deck manager with full deck. will restart every round
    public DeckManager(List<PlayingCard> fullDeck){
        this.deckCards = new ArrayList<>(fullDeck);
        Collections.shuffle(deckCards);
        this.discardPile = new ArrayList<>();
        this.currentHand = new ArrayList<>();
    }
    //to override the 5 if player have special item; might move it to other class later
    public void setHandsize(int handsize) {
        this.handsize = handsize;
    }

    public List<PlayingCard> drawCards(){
        currentHand.clear();
        for (int i = 0; i < handsize && !deckCards.isEmpty(); i++){
            currentHand.add(deckCards.remove(0));
        }
        return new ArrayList<>(currentHand);
    }

    public List<PlayingCard> getDeckCards() {
        return deckCards;
    }


    public void playCards(List<PlayingCard> selectedCards){
        List<PlayingCard> toPlay = new ArrayList<>(selectedCards);
        int numPlayed = toPlay.size();

        currentHand.removeAll(toPlay);
        discardPile.addAll(toPlay);

        for (int i = 0; i < numPlayed && !deckCards.isEmpty(); i++){
            currentHand.add(deckCards.remove(0));
        }
    }

    public void discardCards(List<PlayingCard> selectedCards){
        List<PlayingCard> toDiscard = new ArrayList<>(selectedCards);
        int numDiscarded = toDiscard.size();

        currentHand.removeAll(toDiscard);
        discardPile.addAll(toDiscard);

        for (int i = 0; i < numDiscarded && !deckCards.isEmpty(); i++){
            currentHand.add(deckCards.remove(0));
        }
    }

    //after round ends
    public void disposeDeck(){
        discardPile.clear();
        deckCards.clear();
        currentHand.clear();
    }

    public List<PlayingCard> getCurrentHand() {
        return currentHand;
    }

    public List<PlayingCard> getDiscardPile() {
        return discardPile;
    }
}
