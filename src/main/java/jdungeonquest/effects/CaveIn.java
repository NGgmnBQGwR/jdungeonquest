package jdungeonquest.effects;

import jdungeonquest.Game;

public class CaveIn implements Effect {

    public CaveIn(){
    }

    @Override
    public void doAction(Game game) {
        game.effectCaveIn();
    }

    @Override
    public String toString() {
        return "CaveIn";
    }
}
