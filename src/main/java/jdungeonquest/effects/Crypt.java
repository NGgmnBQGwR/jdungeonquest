package jdungeonquest.effects;

import jdungeonquest.Game;

public class Crypt implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectCrypt();
    }    
}
