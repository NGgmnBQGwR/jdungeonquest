package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class MovePlayer extends Message {
    int x;
    int y;
    String player;
    
    public MovePlayer(){
        x = 0;
        y = 0;
        player = "none";
        msgType = NetworkMessageType.MovePlayer;
    }
    
    public MovePlayer(int x, int y, String player){
        this.x = x;
        this.y = y;
        this.player = player;
    }
        
}
