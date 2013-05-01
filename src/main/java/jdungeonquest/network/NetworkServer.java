package jdungeonquest.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jdungeonquest.Game;
import static jdungeonquest.enums.NetworkMessageType.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkServer implements Runnable {

    private Game game = new Game();
    private Server server = null;
    Logger logger = LoggerFactory.getLogger(NetworkServer.class);
    private int serverPort = 3334;
    public static final int DEFAULT_PORT = 4446;

    private Map<Integer, List<String>> clientPlayersMap = new HashMap<>();
    
    public NetworkServer() {
        this(DEFAULT_PORT);
        Log.set(Log.LEVEL_DEBUG);
    }

    public NetworkServer(int port) {
        server = new Server();
        Network.registerClasses(server);
        this.serverPort = port;
    }

    @Override
    public void run() {
        server.addListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                logger.debug("Client '" + connection + "' " + connection.getID() + " connected");
            }

            @Override
            public void disconnected(Connection connection) {
                logger.debug("Client '" + connection + "' " + connection.getID() + " disconnected");
                broadcast("Client " + connection.toString() + " has quit.");
                for(String player : clientPlayersMap.get(connection.getID())){
                    //game.removePlayer(player);
                }
                broadcast(new PlayerList(game.getPlayerList()));
            }

            @Override
            public void received(Connection connection, Object object) {
                logger.debug(connection.getID() + " " + connection.toString() + " Recieved package: " + object);
                if (object instanceof String) {
                    String test = (String) object;
                    if (test.equals("he")) {
                        clientPlayersMap.put(connection.getID(), new ArrayList<String>());
                        connection.sendTCP(new String("lo"));
                        connection.sendTCP(new PlayerList(game.getPlayerList()));
                    }
                }else if (object instanceof Message) {
                    switch (((Message) object).msgType) {
                        //We got a client wanting to register a new player.
                        //Check if it can be done and send him result of it.
                        case RegistrationRequest:
                            RegistrationRequest r = ((RegistrationRequest) object);
                            registerPlayer(connection, r.playerName, r.playerClass);
                            break;

                        case ChatMessage:
                            ChatMessage msg = (ChatMessage)object;
                            logger.debug(msg.author + ":" + msg.message);
                            server.sendToAllExceptTCP(connection.getID(), msg);
                            break;
                            
                        //We recieve this only when client has disconnected
                        //here we need to correct the entry in the clientPlayerMap
                        //according to the players that were registered on that client
                        case PlayerList:
                            //confirm connection with the server if client is valid
                            //connection.sendTCP("lo");
                            break;
                            
                        default:
                            break;
                    }
                } else if (object instanceof com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive){
                } else {
                    logger.info("Recieved unkown package: " + object);
                }
            }
        });
        try {
            server.bind(this.serverPort);
        } catch (IOException ex) {
            logger.error("Exception" + ex);
        }
        server.start();
        logger.debug("Server started on port " + this.serverPort);
    }

    private void registerPlayer(Connection conn, String playerName, String playerClass) {
        boolean result = game.registerPlayer(playerName, playerClass);
        if (result) {
            conn.sendTCP(new RegistrationRequest(playerName, playerClass));
            attachPlayerToClient(conn.getID(), playerName);
            broadcast("Player " + playerName + " joined.");
            broadcast(new PlayerList(game.getPlayerList()));
        } else {
            conn.sendTCP(new RegistrationRequest("", ""));
        }
    } 
    
    private void attachPlayerToClient(int id, String name) {
        if(clientPlayersMap.containsKey(id)){
            List<String> players = clientPlayersMap.get(id);
            players.add(name);
        }else{
            clientPlayersMap.put(id, new ArrayList<>(Arrays.asList(new String[]{name})));
        }
        logger.debug("Added " + name);
        logger.debug("List of players for Client " + id + " : " + clientPlayersMap.get(id));
    }

    private void detachPlayerFromClient(int id, String name) {
        if (clientPlayersMap.containsKey(id)) {
            List<String> players = clientPlayersMap.get(id);
            players.remove(name);
            clientPlayersMap.put(id, players);
            logger.debug("Removed " + name);
        }
        logger.debug("List of players for Client " + id + " : " + clientPlayersMap.get(id));
    }
    
    public void stop() {
        server.stop();
        logger.debug("Server stopped");
    }

    public Game getGame() {
        return game;
    }

    private void broadcast(String text) {
        logger.debug("Broadcasting text:" + text);
        server.sendToAllTCP(new ChatMessage(text, "Server"));
    }

    private void broadcast(PlayerList playerList) {
        logger.debug("Broadcasting playerList:" + playerList);
        server.sendToAllTCP(playerList);
    }    
}
