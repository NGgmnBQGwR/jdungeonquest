package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.network.ChatMessage;

public class GiantCentipede implements Effect{

    @Override
    public void doAction(Game g) {
        g.addMessage(new ChatMessage("You are attacked by a Giant Centipede!", "Game"));
        g.hurtPlayer(g.currentPlayer, g.diceRoll(1, 12, 0), "Before you're able to kill it, it bites you!");
    }    
}
