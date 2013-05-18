package jdungeonquest.effects;

import jdungeonquest.Game;

public class TorchGoesOut implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectTorchGoesOut();
    }    
}
