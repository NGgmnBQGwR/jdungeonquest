package jdungeonquest.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;
import jdungeonquest.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkServer implements Runnable {

    private Game game = null;
    private Server server = null;
    Logger logger = LoggerFactory.getLogger(NetworkServer.class);
    private int serverPort = 3334;

    public NetworkServer(Game game) {
        server = new Server();
        Network.registerClasses(server);
        this.game = game;
        this.serverPort = 3334;
    }

    public NetworkServer(Game game, int port) {
        this(game);
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
            }

            @Override
            public void received(Connection connection, Object object) {
                logger.debug("Recieved package: ");
                if (object instanceof Message) {
                    switch (((Message) object).msgType) {
                        case RegistrationRequest:
                            logger.debug("Registering client " + connection.getID() + " with name " + ((RegistrationRequest) object).playerName);
                            game.registerPlayer(((RegistrationRequest) object).playerName);
                            break;
                        default:
                            break;
                    }
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
    }

    public Game getGame() {
        return game;
    }
}
