package jdungeonquest.test;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdungeonquest.Game;
import jdungeonquest.enums.ClientState;
import jdungeonquest.gui.GUI;
import jdungeonquest.network.Network;
import jdungeonquest.network.NetworkClient;
import jdungeonquest.network.NetworkServer;
import jdungeonquest.network.RegistrationRequest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import static org.mockito.Mockito.*;

/**
 * Tests for NetworkClient and NetworkServer.
 *
 * @author gman-x
 */
@RunWith(JUnit4.class)
public class NetworkTest {

    @Test
    public void NetworkClientConnectionSuccess() throws IOException{
        
        final int port = 3335;
        
        GUI guiMock = mock(GUI.class);
        
        NetworkClient client = new NetworkClient("127.0.0.1", port, guiMock);
        
        Server server = new Server();
        Network.registerClasses(server);
        server.addListener(new Listener(){
        
            @Override
            public void received(Connection c, Object o){
                if(o instanceof String){
                    assertEquals("he", ((String)o));
                    c.sendTCP(new String("lo"));
                }
            }
        });
        server.bind(port);
        server.start();
        
        client.run();
        client.connectToServer();
        while(client.getClientState() != ClientState.IN_LOBBY){}
        
        assertEquals(ClientState.IN_LOBBY, client.getClientState());
        
        client.stop();
        server.stop();
    }
    
    @Test
    public void NetworkRegistrationSuccess() throws InterruptedException{
//        final int port = 3335;
//        
//        GUI guiMock = mock(GUI.class);
//        
//        NetworkClient client = new NetworkClient("127.0.0.1", port, guiMock);
//        NetworkServer server = new NetworkServer(port);
//        
//        server.run();
//        client.run();
//        
//        Thread.sleep(100);
//        
////        assertEquals(true, server.getGame().isPlayerRegistered());
//        
//        client.stop();
//        server.stop();
    }
    
    
    
}
