package jdungeonquest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
 
    List<Card> cardArray = new ArrayList<>();
    List<Card> usedCards = new ArrayList<>();
    
    public Card takeCard(){
        if(cardArray.isEmpty()){
            shuffle();
        }
        Card c = cardArray.get(0);
        usedCards.add(c);
        cardArray.remove(0);
        return c;
    }
    
    public void shuffle(){
        for(Card card : usedCards){
            cardArray.add(card);
        }
        usedCards.clear();
        Collections.shuffle(cardArray);
    }
    
    public int size(){
        return cardArray.size();
    }

    void putCard(Card card) {
        cardArray.add(card);
    }
}
