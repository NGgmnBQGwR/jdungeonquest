package jdungeonquest.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import jdungeonquest.enums.NetworkMessageType;

public class Network {

    static public void registerClasses(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        
        kryo.register(Message.class);
        kryo.register(NetworkMessageType.class);
        kryo.register(RegistrationRequest.class);
        kryo.register(String.class);
    }
}
