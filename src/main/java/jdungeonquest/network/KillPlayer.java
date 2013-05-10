package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class KillPlayer extends Message{
    public String player;
    
    public KillPlayer(){
        player = "none";
        msgType = NetworkMessageType.KillPlayer;
    }
    
    public KillPlayer(String player){
        this.player = player;
        msgType = NetworkMessageType.KillPlayer;
    }    
}
