package jdungeonquest.effects;

import jdungeonquest.Game;

public class Snakes implements Effect{

    @Override
    public void doAction(Game g) {
        g.hurtPlayer(g.currentPlayer, g.diceRoll(1, 6, 0), "A snake was hiding there! It bit you!");
    }
    
}
