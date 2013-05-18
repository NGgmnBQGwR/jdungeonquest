package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.network.ChatMessage;

public class EmptyCorpse implements Effect{
    
    @Override
    public void doAction(Game g) {
        g.addMessage(new ChatMessage("You found nothing of interest.", "Game"));
    }    
}
