package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class UnregisterRequest extends Message {

    final String playerName;

    public UnregisterRequest(){
        playerName = "none";
    }
    
    public UnregisterRequest(String name) {
        msgType = NetworkMessageType.UnregisterRequest;
        playerName = name;
    }

    public String getName() {
        return playerName;
    }
}
