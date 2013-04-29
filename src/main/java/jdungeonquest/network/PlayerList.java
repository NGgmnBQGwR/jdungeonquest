package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class PlayerList extends Message{

    public String[] players;
    
    PlayerList(){
    }
    
    PlayerList(String[] players){
        msgType = NetworkMessageType.PlayerList;
        this.players = players;
    }
}
