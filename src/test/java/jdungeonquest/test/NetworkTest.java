package jdungeonquest.test;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdungeonquest.Game;
import jdungeonquest.network.Network;
import jdungeonquest.network.NetworkClient;
import jdungeonquest.network.NetworkServer;
import jdungeonquest.network.RegistrationRequest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for NetworkClient and NetworkServer.
 *
 * @author gman-x
 */
@RunWith(JUnit4.class)
public class NetworkTest {

    @Test
    public void NetworkClientConnectionSuccess() throws IOException{
        
        final String clientName = "TestClient1";
        
        NetworkClient client = new NetworkClient(clientName);
        
        Server server = new Server();
        Network.registerClasses(server);
        server.addListener(new Listener(){
        
            @Override
            public void received(Connection c, Object o){
                if(o instanceof RegistrationRequest){
                    assertEquals(clientName, ((RegistrationRequest)o).getName());
                    c.sendTCP(new RegistrationRequest(clientName));
                }
            }
        });
        server.bind(3334);
        server.start();
        
        client.run();
        client.registerOnServer();
        while(!client.isRegistered){}
        boolean resultClient = client.isRegistered;
        assertEquals(true, resultClient);
    }
    
    @Test
    public void NetworkRegistrationSuccess() throws InterruptedException{
        final String clientName = "TestClient1";
        final int port = 3335;
        
        Game game = new Game();
        NetworkClient client = new NetworkClient(clientName, "127.0.0.1", port);
        NetworkServer server = new NetworkServer(game, port);
        
        server.run();
        client.run();
        
        client.registerOnServer();
        
        Thread.sleep(1000);
        
        assertEquals(true, server.getGame().isPlayerRegistered(clientName));
    }
    
    
    
}
