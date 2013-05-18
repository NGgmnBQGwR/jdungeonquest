package jdungeonquest.effects;

import jdungeonquest.Game;

public class DeadAdventurer implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectDeadAdventurer();
    }
}
