package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class RegistrationRequest extends Message {

    final String playerName;
    final String playerClass;

    public RegistrationRequest(){
        playerName = "none";
        playerClass = "class_none";
    }
    
    public RegistrationRequest(String name, String classname) {
        msgType = NetworkMessageType.RegistrationRequest;
        playerName = name;
        playerClass = classname;
    }

    public String getName() {
        return playerName;
    }
    
    public String getClassName(){
        return playerClass;
    }    
}
