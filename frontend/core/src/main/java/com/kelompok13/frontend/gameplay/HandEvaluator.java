package com.kelompok13.frontend.gameplay;


import com.kelompok13.frontend.card.*;
import com.kelompok13.frontend.card.effects.JokerEffect;

import java.util.*;

//evaluate the poker hand
//for now will be
//1. high card
//2. pair
//3. two pair
//4. full house
public class HandEvaluator {
       private static final Map<String, Integer> HAND_RANKINGS = Map.ofEntries(
        Map.entry("High Card", 1),
        Map.entry("Pair", 2),
        Map.entry("Two Pair", 3),
        Map.entry("Three of a Kind", 4),
        Map.entry("Straight", 5),
        Map.entry("Flush", 6),
        Map.entry("Full House", 7),
        Map.entry("Four of a Kind", 8),
        Map.entry("Straight Flush", 9),
        Map.entry("Royal Flush", 10)
    );

    //untuk sementara beberapa aja
    Map<String, Integer> handRankings = new HashMap<>(){{
        put("High Card", 1);
        put("Pair", 2);
        put("Two Pair", 3);
        put("Three of a Kind", 4);
        put("Straight", 5);
        put("Flush", 6);
        put("Full House", 7);
        put("Four of a Kind", 8);
        put("Straight Flush", 9);
        put("Royal Flush", 10);
    }};

    private String currentHandType = "No Cards";
    private List<Integer> scoringCards = new ArrayList<>();

    List<Integer> countedValues = new ArrayList<>();
    List<Integer> valuesCounted = new ArrayList<>();

    private int countResult(int multiplier){
        int result = 0;
        for(Integer card: scoringCards){
            result += card;
        }
        result *= multiplier;
        return result;
    }


    public int evaluateHandScore(List<PlayingCard> selectedCard, int handsize){
        int result = 0;
        if (selectedCard.isEmpty()){
            currentHandType = "No Cards";
        }

        if (checkFlush(selectedCard, handsize) && checkStraight(selectedCard)){
            currentHandType = "Straight Flush";
            result =  countResult(handRankings.get("Straight Flush"));
            return result;
        }
        if (checkFlush(selectedCard, handsize)){
            result =  countResult(handRankings.get("Flush"));
        }
        if (checkStraight(selectedCard)){
            result =  countResult(handRankings.get("Straight"));
        }
        if (checkPair(selectedCard)){
            if (currentHandType.equals("Two Pair")){
                result =  countResult(handRankings.get("Two Pair"));
            } else if (currentHandType.equals("Pair")){
                result =  countResult(handRankings.get("Pair"));
            }
        }
        if (checkHighCard(selectedCard)){
            result =  countResult(handRankings.get("High Card"));
        }

        return result;
    }

    private boolean checkFlush(List<PlayingCard> selectedCard, int handsize) {
        Map<CardSuit, Integer> suitCount = new HashMap<>();
        for (PlayingCard card : selectedCard) {
            suitCount.put(card.getSuit(), suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }
        scoringCards.clear();

        for (Map.Entry<CardSuit, Integer> entry : suitCount.entrySet()) {
            if (entry.getValue() >= handsize) {
                CardSuit flushSuit = entry.getKey();

                for (PlayingCard card : selectedCard) {
                    if (card.getSuit() == flushSuit) {
                        scoringCards.add(card.getValue());
                    }
                }
                // sort descending and take only standard 5 highest cards
                Collections.sort(scoringCards, Collections.reverseOrder());
                if (scoringCards.size() > 5) {
                    scoringCards.remove(-1);
                }
                currentHandType = "Flush";
                return true;
            }
        }

        return false;
    }

    private boolean checkStraight(List<PlayingCard> selectedCard) {
        List<Integer> values = new ArrayList<>();
        for (PlayingCard card : selectedCard) {
            values.add(card.getValue());
        }
        Collections.sort(values);

        scoringCards.clear();
        for (int i = 0; i < values.size() - 1; i++) {
            if (values.get(i + 1) - values.get(i) != 1 && i < 3) {
                return  false;
            }
            if (i < 5) scoringCards.add(values.get(i));
        }
        currentHandType = "Straight";
        return true;
    }

    private boolean checkHighCard(List<PlayingCard> selectedCard){
        int maxValue = 0;
        scoringCards.clear();
        for (PlayingCard card: selectedCard){
            if (card.getValue() > maxValue){
                maxValue = card.getValue();
            }
        }
        scoringCards.add(maxValue);
        currentHandType = "High Card";
        return true;
    }

    private boolean checkPair(List<PlayingCard> selectedCard){
        int pairRank = pairCount(selectedCard);
        if (pairRank > 0){
            scoringCards.clear();
            for (PlayingCard card: selectedCard){
                if (countedValues.contains(card.getValue())){
                    scoringCards.add(card.getValue());
                }
            }
            if (pairRank == handRankings.get("Pair")){
                currentHandType = "Pair";
            } else if (pairRank == handRankings.get("Two Pair")){
                currentHandType = "Two Pair";
            }
            return true;
        }
        return false;
    }

    //for one pair and two pair
    private int pairCount(List<PlayingCard> selectedCard){
        List<Integer> valuesCounted = new ArrayList<>();
        for (PlayingCard card: selectedCard){
            valuesCounted.add(card.getValue());
        }
        int pairs = 0;
        scoringCards.clear();

        for (int i: valuesCounted){
            int freq = Collections.frequency(valuesCounted, i);
            if (freq == 2 && !scoringCards.contains(i)){
                pairs++;
                scoringCards.add(i*2);
            }
        }
        return pairs;
    }

//    //three of a kind
//    private int checkThreeOfAKind(List<PlayingCard> selectedCard){
//        List<Integer> valuesCounted = new ArrayList<>();
//        for (PlayingCard card: selectedCard){
//            valuesCounted.add(card.getValue());
//        }
//        for (int i: valuesCounted){
//            int freq = Collections.frequency(valuesCounted, i);
//            if (freq == 3){
//                countedValues.add(i);
//                return handRankings.get("Three of a Kind");
//            }
//        }
//        return 0;
//    }

}
