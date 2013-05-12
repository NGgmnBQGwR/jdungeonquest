package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class EndBattle extends Message{
    
    public EndBattle(){
        msgType = NetworkMessageType.EndBattle;
    }
}
