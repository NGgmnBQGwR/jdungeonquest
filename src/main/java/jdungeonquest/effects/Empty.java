package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.network.ChatMessage;

public class Empty implements Effect {
    public Empty(){
        
    }
    
    @Override
    public void doAction(Game game) {
        game.addMessage(new ChatMessage("There's nothing in this room.", "Game"));
    }
    
    @Override
    public String toString() {
        return "Empty";
    }    
}
