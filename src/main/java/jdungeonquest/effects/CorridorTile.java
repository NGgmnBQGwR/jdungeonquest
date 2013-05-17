package jdungeonquest.effects;

import jdungeonquest.Game;

public class CorridorTile implements Effect{

    @Override
    public void doAction(Game g) {
        g.processCorridorTile();
    }
    
}
