package jdungeonquest.effects;

import jdungeonquest.Game;

public class Chasm implements Effect{

    @Override
    public void doAction(Game g) {
        g.processChasmTile();
    }    
}
