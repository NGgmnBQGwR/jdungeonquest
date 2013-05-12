package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class BattleAction extends Message{
    
    public int action;
    
    public BattleAction(){
        msgType = NetworkMessageType.BattleAction;
        action = 0;
    }
    
    public BattleAction(int a){
        msgType = NetworkMessageType.BattleAction;
        action = a;
    }    
}
