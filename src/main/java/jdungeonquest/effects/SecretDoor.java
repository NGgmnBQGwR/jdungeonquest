package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.network.ChatMessage;

public class SecretDoor implements Effect{

    @Override
    public void doAction(Game g) {
        g.addMessage(new ChatMessage(g.getCurrentPlayer() + " has found a secret door! You can move in any direction.", "Game"));
        g.processSecretDoor();
    }
}
