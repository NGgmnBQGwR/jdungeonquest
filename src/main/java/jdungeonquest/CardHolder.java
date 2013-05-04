package jdungeonquest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdungeonquest.effects.Effect;
import jdungeonquest.effects.GiveGold;
import org.yaml.snakeyaml.Yaml;

public class CardHolder {
    public Deck dragonDeck = new Deck();
    public Deck doorDeck = new Deck();
    public Deck corpseDeck = new Deck();
    public Deck cryptDeck = new Deck();
    public Deck trapDeck = new Deck();
    public Deck searchDeck = new Deck();
    public Deck roomDeck = new Deck();
    public Deck treasureDeck = new Deck();
    public Deck monsterDeck = new Deck();
    
    Map<Integer, Card> cardsMap = new HashMap<Integer, Card>();
    
    public CardHolder(){
        initializeCards();
    }
    
    private void initializeCards(){
        Yaml yaml = new Yaml();
        List<Card> allCards;
        allCards = (ArrayList<Card>) yaml.load(CardHolder.class.getResourceAsStream("/Cards.yaml"));
        
        int currentCard = 0;
        for(Card card: allCards){
            cardsMap.put(currentCard, card);
            currentCard++;
            
            switch(card.getDeckType()){
                case Dragon:
                    dragonDeck.putCard(card);
                    break;
                case Door:
                case Corpse:
                case Crypt:
                case Trap:
                case Search:
                case Room:
                case Treasure:
                case Monster:
                default: break;
            }
        }
    }
}
