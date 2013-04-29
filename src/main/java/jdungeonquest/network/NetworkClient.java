package jdungeonquest.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
import java.util.logging.Level;
import jdungeonquest.enums.ClientState;
import static jdungeonquest.enums.NetworkMessageType.RegistrationRequest;
import jdungeonquest.gui.GUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkClient implements Runnable {

    private Client client;
    private int serverPort;
    private String serverIP;
    private String clientName;
    private GUI gui;
    private ClientState state = ClientState.NOT_REGISTERED;
    Logger logger = LoggerFactory.getLogger(NetworkClient.class);

    public NetworkClient(String name, String ip, int port, GUI gui) {
        client = new Client();
        Network.registerClasses(client);
        Log.set(Log.LEVEL_DEBUG);
        
        this.clientName = name;
        this.serverIP = ip;
        this.serverPort = port;
        this.gui = gui;
        logger.debug("NetworkClient " + this.clientName + " " + this.serverIP + ":" + this.serverPort);
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
        if(!client.isConnected()){
            return;
        }
        sendMessage(new RegistrationRequest(getClientName()));
    }

    public void sendMessage(Message msg) {
        int a = this.client.sendTCP(msg);
        logger.debug("Sent message " + msg + ", the size is " + a + " bytes.");
    }

    private void changeState(ClientState newState) {
        logger.debug("Changing game state from " + state + " to " + newState);
        state = newState;
    }

    @Override
    public void run() {
        client.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                logger.debug("Connected to " + connection);
            }

            @Override
            public void disconnected(Connection connection) {
                gui.playerRegistered(false);
                logger.debug("Disconnected");
            }

            @Override
            public void received(Connection c, Object object) {
                logger.debug("Recieved package: " + object);
                if (object instanceof Message) {
                    switch (((Message) object).msgType) {
                        
                        case RegistrationRequest:
                            if (state == ClientState.NOT_REGISTERED && ((RegistrationRequest) object).getName().equals(getClientName())) {
                                changeState(ClientState.REGISTERED);
                                gui.playerRegistered(true);
                            } else {
                                client.close();
                                changeState(ClientState.NOT_REGISTERED);
                                gui.playerRegistered(false);
                            }
                            break;
                            
                        case ChatMessage:
                            ChatMessage msg = (ChatMessage)object;
                            gui.addChatMessage(msg.message, msg.author);
                            break;
                            
                        case PlayerList:
                            PlayerList p = (PlayerList)object;
                            gui.updatePlayerList(p);
                            break;
                    }
                } else if (object instanceof com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive) {
                }
            }
        });

        client.start();
    }

    public void stop(){
        client.stop();
    }
    
    /**
     * @return the clientName
     */
    public String getClientName() {
        return clientName;
    }

    public ClientState getClientState() {
        return state;
    }

    public void sendChatMessage(String text) {
        logger.debug("Sending ChatMessage: " + text);
        ChatMessage msg = new ChatMessage(text, getClientName());
        sendMessage(msg);
        gui.addChatMessage(text, getClientName());
    }
}
