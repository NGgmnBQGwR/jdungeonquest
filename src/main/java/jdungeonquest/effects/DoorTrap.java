package jdungeonquest.effects;

import jdungeonquest.Game;

public class DoorTrap implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectDoorTrap();
    }
}
