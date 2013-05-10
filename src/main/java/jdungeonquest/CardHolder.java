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
                    doorDeck.putCard(card);
                    break;                          
                case Corpse:
                    corpseDeck.putCard(card);
                    break;                          
                case Crypt:
                    cryptDeck.putCard(card);
                    break;                          
                case Trap:
                    trapDeck.putCard(card);
                    break;                          
                case Search:
                    searchDeck.putCard(card);
                    break;                          
                case Room:
                    roomDeck.putCard(card);
                    break;                    
                case Treasure:
                    treasureDeck.putCard(card);
                    break;                          
                case Monster:
                    monsterDeck.putCard(card);
                    break;                          
                default: break;
            }
        }
        dragonDeck.shuffle();
        doorDeck.shuffle();
        corpseDeck.shuffle();
        cryptDeck.shuffle();
        trapDeck.shuffle();
        searchDeck.shuffle();
        roomDeck.shuffle();
        treasureDeck.shuffle();
        monsterDeck.shuffle();
    }
}
