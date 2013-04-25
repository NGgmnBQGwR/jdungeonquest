package jdungeonquest;

import java.util.ArrayList;
import java.util.List;
import jdungeonquest.effects.Effect;
import jdungeonquest.enums.DeckType;

public class Card {

    private String description;
    private DeckType deckType; // enum type
    private List<Effect> effects = new ArrayList<Effect>();

    public Card(String path) {

    }

    public void activate(Game game) {
        for (Effect e : effects) {
            e.doAction(game);
        }
    }

    public DeckType getDeckType() {
        return deckType;
    }
}
