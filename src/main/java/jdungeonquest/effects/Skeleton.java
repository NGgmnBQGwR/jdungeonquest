package jdungeonquest.effects;

import jdungeonquest.Game;

public class Skeleton implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectSkeleton();
    }    
}
