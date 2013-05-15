package jdungeonquest.effects;

import jdungeonquest.Game;

public class DoorJams implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectDoorJams();
    }
    
}
