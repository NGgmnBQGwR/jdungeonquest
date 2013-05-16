package jdungeonquest.effects;

import jdungeonquest.Game;

public class CaveInTile implements Effect {

    public CaveInTile(){
    }

    @Override
    public void doAction(Game game) {
        game.processCaveInTile();
    }

    @Override
    public String toString() {
        return "CaveInTile";
    }
}
