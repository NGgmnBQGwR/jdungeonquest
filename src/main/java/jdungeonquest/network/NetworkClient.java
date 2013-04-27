package jdungeonquest.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.io.IOException;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkClient implements Runnable {

    private Client client;
    private int serverPort;
    private String serverIP;
    private String clientName;
    public boolean isRegistered = false;
    Logger logger = LoggerFactory.getLogger(NetworkClient.class);

    public NetworkClient() {
        client = new Client();
        Network.registerClasses(client);

        this.serverIP = "127.0.0.1";
        this.serverPort = 3334;
        this.clientName = "GenericClient";
    }

    public NetworkClient(String name) {
        this();
        this.clientName = name;
    }

    public NetworkClient(String name, String ip) {
        this();
        this.clientName = name;
        this.serverIP = ip;
    }

    public NetworkClient(String name, String ip, int port) {
        this();
        this.clientName = name;
        this.serverIP = ip;
        this.serverPort = port;
    }

    private void connectToServer() {
        try {
            client.connect(5000, this.serverIP, this.serverPort);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registerOnServer() {
        this.connectToServer();
        RegistrationRequest message = new RegistrationRequest(clientName);
        int a = this.client.sendTCP(message);
        logger.debug("Sent message with return code " + a);
    }

    @Override
    public void run() {
        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                logger.debug("Connected to " + connection);
            }

            @Override
            public void received(Connection c, Object o) {
                logger.debug("Got " + o);
                if (o instanceof RegistrationRequest) {
                    if (((RegistrationRequest) o).getName().equals(clientName)) {
                        isRegistered = true;
                    }
                }
            }
        });

        client.start();
    }
}
