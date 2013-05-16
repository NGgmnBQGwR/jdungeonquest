package jdungeonquest.effects;

import jdungeonquest.Game;

public class TrapTile implements Effect{

    @Override
    public void doAction(Game g) {
        g.processDrawTrapCard();
    }
    
}
