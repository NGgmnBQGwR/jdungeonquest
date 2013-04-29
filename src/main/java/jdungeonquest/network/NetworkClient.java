package jdungeonquest.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private GUI gui;
    private ClientState state = ClientState.NOT_CONNECTED;
    //list of players connected to this client
    List<PlayerData> players = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(NetworkClient.class);

    public NetworkClient(String ip, int port, GUI gui) {
        client = new Client();
        Network.registerClasses(client);
        Log.set(Log.LEVEL_DEBUG);
        
        this.serverIP = ip;
        this.serverPort = port;
        this.gui = gui;
        logger.debug("NetworkClient " + this.serverIP + ":" + this.serverPort);
    }

    public void connectToServer() {
        try {
            client.connect(5000, this.serverIP, this.serverPort);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(NetworkClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!client.isConnected()) {
            return;
        }
        sendMessage("he");
    }
    
    public void registerOnServer(String name) {
        sendMessage(new RegistrationRequest(name));
    }

    public void sendMessage(Message msg) {
        int a = this.client.sendTCP(msg);
        logger.debug("Sent message " + msg + ", the size is " + a + " bytes.");
    }
    
    public void sendMessage(String msg) {
        int a = this.client.sendTCP(msg);
        logger.debug("Sent string " + msg + ", the size is " + a + " bytes.");
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
                changeState(ClientState.NOT_CONNECTED);
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            logger.debug("Reconnecting.");
                            client.reconnect();
                            //send name of all players on this client
                            //client.sendTCP(new PlayerList(players));
                        } catch (IOException ex) {
                            logger.debug(ex.toString());
                        }
                    }
                }.start();

                logger.debug("Disconnected");
            }

            @Override
            public void received(Connection c, Object object) {
                logger.debug("Recieved package: " + object);
                if (object instanceof String) {
                    String obj = (String)object;
                    if(obj.equals("lo")){
                        logger.debug("Confirmed connection with a server.");
                        changeState(ClientState.IN_LOBBY);
                        gui.showLobby();
                    }
                }else if (object instanceof Message) {
                    switch (((Message) object).msgType) {
                        
                        case RegistrationRequest:
                            String playerName = ((RegistrationRequest)object).getName();
                            if (havePlayer(playerName)) {
                                gui.playerRegistered(true, playerName);
                            } else {
                                gui.playerRegistered(false, playerName);
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

    private boolean havePlayer(String playerName) {
        for(PlayerData p : players){
            if(p.getName().equals(playerName)){
                return true;
            }
        }
        return false;
    }
    
    public void stop(){
        client.stop();
    }

    public ClientState getClientState() {
        return state;
    }

    public void sendChatMessage(String text) {
        logger.debug("Sending ChatMessage: " + text);
        ChatMessage msg = new ChatMessage(text, "none");
        sendMessage(msg);
        gui.addChatMessage(text, "none");
    }
    
    public void addPlayer(String name){
        sendMessage(new RegistrationRequest(name));
    }

    private static class PlayerData {

        String name;
        
        public PlayerData() {
        }
        
        public void setName(String name){
            this.name = name;
        }
        public String getName(){
            return name;
        }        
    }
}
