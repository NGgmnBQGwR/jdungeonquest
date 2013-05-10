
package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class EndGame extends Message{
    public EndGame(){
        msgType = NetworkMessageType.EndGame;
    }
}
