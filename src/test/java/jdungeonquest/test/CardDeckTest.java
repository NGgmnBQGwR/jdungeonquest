/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdungeonquest.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jdungeonquest.Card;
import jdungeonquest.CardHolder;
import jdungeonquest.Deck;
import jdungeonquest.Tile;
import jdungeonquest.TileHolder;
import jdungeonquest.effects.Effect;
import jdungeonquest.effects.GiveGold;
import jdungeonquest.enums.DeckType;
import jdungeonquest.enums.EntryDirection;
import jdungeonquest.enums.RoomWallType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.yaml.snakeyaml.Yaml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author User
 */
@RunWith(JUnit4.class)
public class CardDeckTest {

    final String dumpEtalon =
            "!!jdungeonquest.Card\n"
            + "deckType: Crypt\n"
            + "description: None\n"
            + "effects:\n"
            + "- !!jdungeonquest.effects.GiveGold {amount: 25}\n";
    
    final String oneCard =
            "!!jdungeonquest.Card\n"
            + "description: Card1\n"
            + "deckType: Door\n"
            + "effects:\n"
            + "- !!jdungeonquest.effects.GiveGold {amount: 25}\n"
            + "- !!jdungeonquest.effects.GiveGold {amount: 75}";
    
    final String threeCards =
            "- !!jdungeonquest.Card\n"
            + "  description: Card1\n"
            + "  deckType: Trap\n"
            + "  effects:\n"
            + "  - !!jdungeonquest.effects.GiveGold {amount: 1}\n"
            + "  - !!jdungeonquest.effects.GiveGold {amount: 2}\n"
            + "\n"
            + "- !!jdungeonquest.Card\n"
            + "  description: Card2\n"
            + "  deckType: Treasure\n"
            + "  effects: []\n"
            + "\n"
            + "- !!jdungeonquest.Card\n"
            + "  description: Card3\n"
            + "  deckType: Monster\n"
            + "  effects:\n"
            + "  - !!jdungeonquest.effects.GiveGold {amount: 125}\n";

    @Test
    public void CardDumpEqualsEtalon(){
        Yaml yaml = new Yaml();
        Card c = new Card();
        List<Effect> el = new ArrayList<>(Arrays.asList( new Effect[]{ new GiveGold(25)} ));
        
        c.setDeckType(DeckType.Crypt);
        c.setDescription("None");
        c.setEffects(el);
        String result = yaml.dump(c);
        System.out.println(result);
        assertEquals(dumpEtalon, result);
    }
    
    @Test
    public void CardLoadedSuccessfully() {
        Yaml yaml = new Yaml();
        Card card = (Card) yaml.load(oneCard);
        assertEquals("Card1", card.getDescription());
        assertEquals(DeckType.Door, card.getDeckType());
        assertEquals(2, card.getEffects().size());
    }
    
    @Test
    public void ThreeCardsLoadedSuccessfully(){
        Yaml yaml = new Yaml();
        List<Card> cards = (ArrayList<Card>) yaml.load(threeCards);
        
        assertEquals("Card1", cards.get(0).getDescription());
        assertEquals("Card2", cards.get(1).getDescription());
        assertEquals("Card3", cards.get(2).getDescription());
        
        assertEquals(DeckType.Trap, cards.get(0).getDeckType());
        assertEquals(DeckType.Treasure, cards.get(1).getDeckType());
        assertEquals(DeckType.Monster, cards.get(2).getDeckType());
        
        assertEquals(2, cards.get(0).getEffects().size());
        assertEquals(0, cards.get(1).getEffects().size());
        assertEquals(1, cards.get(2).getEffects().size());
    }
    
    @Test
    public void CardHolderInitializedSuccessfully(){
        CardHolder holder = new CardHolder();
        
        assertEquals(8, holder.dragonDeck.size());
//        assertEquals(0, holder.doorDeck.size());
//        assertEquals(0, holder.corpseDeck.size());
//        assertEquals(0, holder.cryptDeck.size());
        assertEquals(15, holder.trapDeck.size());
//        assertEquals(0, holder.searchDeck.size());
//        assertEquals(0, holder.roomDeck.size());
        assertEquals(32, holder.treasureDeck.size());
//        assertEquals(0, holder.monsterDeck.size());
    }
}
