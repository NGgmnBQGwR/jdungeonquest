package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.network.ChatMessage;

public class FoundPotion implements Effect{

    @Override
    public void doAction(Game g) {
        g.addMessage(new ChatMessage("You've found an empty potion bottle.", "Game"));
    }    
}
