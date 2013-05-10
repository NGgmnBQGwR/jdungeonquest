package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class GuessNumber extends Message{
    public int value;
    
    public GuessNumber(){
        value = 0;
        msgType = NetworkMessageType.GuessNumber;
    }
    
    public GuessNumber(int value){
        this.value = value;
        msgType = NetworkMessageType.GuessNumber;
    }
}
