package jdungeonquest.effects;

import jdungeonquest.Game;

public class DragonSleeps implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectDragonSleeps();
    }
}
