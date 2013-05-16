package jdungeonquest.effects;

import jdungeonquest.Game;

public class Trapdoor implements Effect{

    @Override
    public void doAction(Game g) {
        g.processTrapdoor();
    }    
}
