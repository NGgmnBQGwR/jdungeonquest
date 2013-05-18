package jdungeonquest.effects;

import jdungeonquest.Game;

public class VampireBats implements Effect{

    @Override
    public void doAction(Game g) {
        g.hurtPlayer(g.currentPlayer, g.diceRoll(1, 6, -2), "A group of vampires bats attacks!");    }    
}
