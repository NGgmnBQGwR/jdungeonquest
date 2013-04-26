package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class RegistrationRequest extends Message {

    final String playerName;

    public RegistrationRequest(){
        playerName = "none";
    }
    
    public RegistrationRequest(String p) {
        msgType = NetworkMessageType.RegistrationRequest;
        playerName = p;
    }

    public String getName() {
        return playerName;
    }
}
