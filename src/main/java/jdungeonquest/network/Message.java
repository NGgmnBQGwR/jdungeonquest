package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class Message {
    NetworkMessageType msgType;
    
    public Message(){
        
    }
    
    public Message(NetworkMessageType type){
        msgType = type;
    }
}
