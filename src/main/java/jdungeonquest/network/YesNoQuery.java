package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;

public class YesNoQuery extends Message{
    public int answer = 0;
    
    public YesNoQuery(){
        msgType = NetworkMessageType.YesNoQuery;
    }
    
    public YesNoQuery(int a){
        answer = a;
        msgType = NetworkMessageType.YesNoQuery;
    }
}
