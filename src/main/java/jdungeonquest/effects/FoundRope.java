package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.network.ChatMessage;

public class FoundRope implements Effect{
    @Override
    public void doAction(Game g) {
        g.addMessage(new ChatMessage("You've found a useless torn rope.", "Game"));
    }    
}
