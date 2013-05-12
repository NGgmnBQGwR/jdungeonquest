package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class StartBattle extends Message{
    
    public int[] choices;
    
    public StartBattle(){
        msgType = NetworkMessageType.StartBattle;
    }
    
    public StartBattle(int[] a){
        msgType = NetworkMessageType.StartBattle;
        choices = a;
    }    
}
