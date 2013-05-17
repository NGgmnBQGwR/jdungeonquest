package jdungeonquest.effects;

import jdungeonquest.Game;

public class StartingTile implements Effect{

    @Override
    public void doAction(Game g) {
        g.processStartingTile();
    }
}
