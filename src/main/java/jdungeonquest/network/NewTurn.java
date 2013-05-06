package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class NewTurn extends Message{
    public String player;
    
    public NewTurn(){
        player = "none";
        msgType = NetworkMessageType.NewTurn;
    }
    
    public NewTurn(String player){
        this.player = player;
        msgType = NetworkMessageType.NewTurn;
    }
}
