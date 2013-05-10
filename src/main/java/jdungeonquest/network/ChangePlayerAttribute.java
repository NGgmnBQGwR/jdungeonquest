package jdungeonquest.network;

import jdungeonquest.enums.NetworkMessageType;
import jdungeonquest.enums.PlayerAttributes;

public class ChangePlayerAttribute extends Message{
    public String player;
    public PlayerAttributes attribute;
    public int amount;

    public ChangePlayerAttribute(){
        this.player = "";
        this.attribute = PlayerAttributes.Gold;
        this.amount = 0;
        msgType = NetworkMessageType.ChangeAttribute;
    }
    
    public ChangePlayerAttribute(String player, PlayerAttributes att, int amount){
        this.player = player;
        this.attribute = att;
        this.amount = amount;
        msgType = NetworkMessageType.ChangeAttribute;
    }
}
