package jdungeonquest.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
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
                broadcast("Player " + connection.toString() + " has quit.");
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
                    }
                }else if (object instanceof Message) {
                    switch (((Message) object).msgType) {
                        case RegistrationRequest:
                            String name = ((RegistrationRequest) object).playerName;
                            logger.debug("Registering player " + name);
                            boolean result = game.registerPlayer(name);
                            if(result){
                                connection.sendTCP(object);
                                broadcast("Player " + name + " joined.");
                                broadcast(new PlayerList(game.getPlayerList()));
                            }else{
                                connection.sendTCP(new RegistrationRequest(""));
                            }
                            break;

                        case ChatMessage:
                            ChatMessage msg = (ChatMessage)object;
                            logger.debug(msg.author + ":" + msg.message);
                            server.sendToAllExceptTCP(connection.getID(), msg);
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

    public void stop() {
        server.stop();
        logger.debug("Server stopped");
    }

    public Game getGame() {
        return game;
    }

    private void broadcast(String text) {
        server.sendToAllTCP(new ChatMessage(text, "Server"));
    }

    private void broadcast(PlayerList playerList) {
        server.sendToAllTCP(playerList);
    }    
}
