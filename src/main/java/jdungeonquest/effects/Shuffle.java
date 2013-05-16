package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.enums.DeckType;

public class Shuffle implements Effect{

    public DeckType type;
    
    public Shuffle(){
        type = DeckType.Dragon;
    }

    public Shuffle(DeckType type){
        this.type = type;
    }

    @Override
    public void doAction(Game g) {
        g.ShuffleDeck(type);
    }
    
}
