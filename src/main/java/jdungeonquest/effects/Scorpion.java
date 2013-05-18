package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.network.ChatMessage;

public class Scorpion implements Effect{

    @Override
    public void doAction(Game g) {
        g.addMessage(new ChatMessage("A scorpion was hiding there!", "Game"));
        g.hurtPlayer(g.currentPlayer, g.diceRoll(1, 6, 0), "It stings you!");
    }
}
