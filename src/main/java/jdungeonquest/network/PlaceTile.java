package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class PlaceTile extends Message {
    public int tile;
    public int rotate;
    public int x;
    public int y;

    public PlaceTile(){
        tile = -1;
        x = 0;
        y = 0;
        rotate = 0;
        msgType = NetworkMessageType.PlaceTile;
    }

    public PlaceTile(int x, int y, int t, int rotate){
        this.tile = t;
        this.x = x;
        this.y = y;
        this.rotate = rotate;
        msgType = NetworkMessageType.PlaceTile;
    }
}
