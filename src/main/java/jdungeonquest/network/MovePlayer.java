package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class MovePlayer extends Message {
    private int x;
    private int y;
    private String player;
    
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
        msgType = NetworkMessageType.MovePlayer;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @return the player
     */
    public String getPlayer() {
        return player;
    }
        
}
