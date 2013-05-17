package jdungeonquest.effects;

import jdungeonquest.Game;

public class BottomlessPit implements Effect{

    @Override
    public void doAction(Game g) {
        g.processBottomlessPitTile();
    }
}
