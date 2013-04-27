package jdungeonquest.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.IOException;
import jdungeonquest.Game;
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
    }

    public NetworkServer(int port) {
        server = new Server();
        Network.registerClasses(server);
        this.serverPort = port;
    }

    public void start() {
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
        logger.debug("Server started on port " + this.serverPort);
    }

    public void stop() {
        server.stop();
        logger.debug("Server stopped");
    }

    @Override
    public void run() {
        start();
        logger.debug("Server started via thread on port " + this.serverPort);
    }

    public Game getGame() {
        return game;
    }
}
