package jdungeonquest.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import jdungeonquest.enums.NetworkMessageType;

public class Network {

    static public void registerClasses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        
        kryo.register(Message.class);
        kryo.register(ChatMessage.class);
        kryo.register(PlayerList.class);
        
        kryo.register(NetworkMessageType.class);
        kryo.register(RegistrationRequest.class);
        kryo.register(UnregisterRequest.class);
        kryo.register(PlaceTile.class);
        kryo.register(MovePlayer.class);
        
        kryo.register(String.class);
        kryo.register(String[].class);
    }
}
