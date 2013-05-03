package jdungeonquest;

import java.util.ArrayList;
import java.util.List;

public class Deck {
 
    List<Card> cardArray = new ArrayList<>();
    
    public Card takeCard(){
        Card c = cardArray.get(0);
        cardArray.remove(0);
        return c;
    }
    
    public void shuffle(){
        
    }
    
    public int size(){
        return cardArray.size();
    }

    void putCard(Card card) {
        cardArray.add(card);
    }
}
