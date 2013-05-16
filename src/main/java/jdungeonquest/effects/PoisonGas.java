package jdungeonquest.effects;

import jdungeonquest.Game;

public class PoisonGas implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectPoisonGas();
    }
}
