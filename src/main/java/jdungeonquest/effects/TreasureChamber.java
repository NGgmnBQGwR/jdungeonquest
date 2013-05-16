package jdungeonquest.effects;

import jdungeonquest.Game;

public class TreasureChamber implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectTreasureChamber();
    }
    
}
