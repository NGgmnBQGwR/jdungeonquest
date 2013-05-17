package jdungeonquest.effects;

import jdungeonquest.Game;

public class ChamberOfDarkness implements Effect{

    @Override
    public void doAction(Game g) {
        g.processChamberOfDarknessTile();
    }
    
}
