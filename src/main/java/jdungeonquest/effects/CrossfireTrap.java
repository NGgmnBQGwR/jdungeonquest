package jdungeonquest.effects;

import jdungeonquest.Game;
import jdungeonquest.network.ChatMessage;

public class CrossfireTrap implements Effect{

    @Override
    public void doAction(Game g) {
        g.effectCrossfireTrap();
    }
    
}
