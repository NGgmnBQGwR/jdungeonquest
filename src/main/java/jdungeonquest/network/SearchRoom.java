package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class SearchRoom extends Message{
    public SearchRoom(){
        msgType = NetworkMessageType.SearchRoom;
    }
}
