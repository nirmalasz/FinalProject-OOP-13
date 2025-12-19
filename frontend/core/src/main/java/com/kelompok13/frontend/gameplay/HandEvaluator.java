package com.kelompok13.frontend.gameplay;


import com.kelompok13.frontend.card.*;

import java.util.*;

//evaluate the poker hand
//for now will be
//1. high card
//2. pair
//3. two pair
//4. full house
public class HandEvaluator {

    int result = 0;

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

    List<Integer> countedValues = new ArrayList<>();
    List<Integer> valuesCounted = new ArrayList<>();

    private int countResult(int multiplier){
        for(Integer card: countedValues){
            result += card;
        }
        result *= multiplier;
        return  result;
    }


    public int evaluateHand(List<PlayingCard> selectedCard, List<JokerCard> jokerCards){
        valuesCounted.add(checkFlush(selectedCard));
        valuesCounted.add(checkThreeOfAKind(selectedCard));
        valuesCounted.add(checkHighCard(selectedCard));
        valuesCounted.add(checkStraight(selectedCard));
        valuesCounted.add(pairCount(selectedCard));

        valuesCounted.stream().sorted();
        int highestHand = valuesCounted.get(0);

        result = countResult(highestHand);

        if (!jokerCards.isEmpty()){

        }
        return result;
    }

    private int checkFlush(List<PlayingCard> selectedCard){
        for (PlayingCard card: selectedCard){
            if (card.getSuit() != selectedCard.get(0).getSuit()){
                return 0;
            }
            countedValues.add(card.getValue());
        }
        return handRankings.get("Flush");
    }

    private int checkStraight(List<PlayingCard> selectedCard){
        List<Integer> values = new ArrayList<>();
        for (PlayingCard card: selectedCard){
            values.add(card.getValue());
        }
        Collections.sort(values);
        for (int i = 0; i < values.size() - 1; i++){
            if (values.get(i) + 1 == values.get(i + 1)){
                countedValues.add(values.get(i));
                return handRankings.get("Straight");
            }
        }
        return 0;
    }

    private int checkHighCard(List<PlayingCard> selectedCard){
        int maxValue = 0;
        for (PlayingCard card: selectedCard){
            if (card.getValue() > maxValue){
                maxValue = card.getValue();
            }
        }
        countedValues.add(maxValue);
        return handRankings.get("High Card");
    }

    //for one pair and two pair
    private int pairCount(List<PlayingCard> selectedCard){
        // Map<Integer, Integer> valueCount = new HashMap<>();
        List<Integer> valuesCounted = new ArrayList<>();
        for (PlayingCard card: selectedCard){
            valuesCounted.add(card.getValue());
        }
        int pairs = 0;

        for (int i: valuesCounted){
            int freq = Collections.frequency(valuesCounted, i);
            if (freq == 2 && !countedValues.contains(i)){
                pairs++;
                countedValues.add(i);
            }

        }

        if (pairs == 1){
            return handRankings.get("Pair");
        } else if (pairs == 2){
            return handRankings.get("Two Pair");
        }
        return 0;
    }

    //three of a kind
    private int checkThreeOfAKind(List<PlayingCard> selectedCard){
        List<Integer> valuesCounted = new ArrayList<>();
        for (PlayingCard card: selectedCard){
            valuesCounted.add(card.getValue());
        }
        for (int i: valuesCounted){
            int freq = Collections.frequency(valuesCounted, i);
            if (freq == 3){
                countedValues.add(i);
                return handRankings.get("Three of a Kind");
            }
        }
        return 0;
    }

}
