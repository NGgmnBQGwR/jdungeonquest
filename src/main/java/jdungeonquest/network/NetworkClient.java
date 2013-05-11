package jdungeonquest.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import jdungeonquest.enums.ClientState;
import jdungeonquest.enums.NetworkMessageType;
import static jdungeonquest.enums.NetworkMessageType.MovePlayer;
import static jdungeonquest.enums.NetworkMessageType.RegistrationRequest;
import static jdungeonquest.enums.NetworkMessageType.UnregisterRequest;
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
    List<String> allPlayersNames = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(NetworkClient.class);
    private String currentPlayer;

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
    
    public void registerOnServer(String name, String classname) {
        sendMessage(new RegistrationRequest(name, classname));
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

    public List<String> getAllPlayerNames() {
        return allPlayersNames;
    }   
    
    public List<String> getPlayerNames(){
        List<String> names = new ArrayList<>();
        for(PlayerData pd : players){
            names.add(pd.getName());
        }
        return names;
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

                            String[] playerList = new String[players.size()];
                            for (int i = 0; i < players.size(); i++) {
                                playerList[i] = players.get(i).getName();
                            }
                            sendMessage(new PlayerList(playerList));
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
                        
                        //Received registration request answer from the server
                        case RegistrationRequest:
                            String playerName = ((RegistrationRequest)object).getName();
                            if (!playerName.equals("")) {
                                //Server added this player to the Game, notify the GUI and add him to local players
                                PlayerData p = new PlayerData();
                                p.setName(playerName);
                                players.add(p);
                                gui.playerRegistered(true, playerName);
                            } else {
                                //Notify GUI that player registration failed
                            }
                            break;
                            
                        //Received confirmation of removing local player
                        case UnregisterRequest:
                            String playerNameU = ((UnregisterRequest)object).getName();
                            if (!playerNameU.equals("")) {
                                //Server removed this player from the game, notify the GUI and remove him from local players
                                
                                Iterator<PlayerData> iter = players.iterator();
                                while (iter.hasNext()) {
                                    PlayerData pdi = iter.next();
                                    if (pdi.getName().equals(playerNameU)) {
                                        iter.remove();
                                    }
                                }

                                gui.playerUnregistered(playerNameU);
                            } else {
                                //Notify GUI that player removal failed
                            }                            
                            break;
                            
                        //Received chat message from the server
                        case ChatMessage:
                            ChatMessage msg = (ChatMessage)object;
                            gui.addChatMessage(msg.message, msg.author);
                            break;
                            
                        //Received player list with all players (including local) from the server
                        case PlayerList:
                            PlayerList p = (PlayerList)object;
                            allPlayersNames = new ArrayList<>(Arrays.asList(p.players));
                            gui.updatePlayerList(p);
                            break;
                            
                        //Every player is ready, and server declared game start
                        case StartGame:
                            gui.initPlayers(getAllPlayerNames());
                            gui.showClient();
                            changeState(ClientState.IN_GAME);
                            break;
                            
                        case PlaceTile:
                            PlaceTile placeTile = (PlaceTile)object;
                            gui.placeTile(placeTile);
                            break;
                            
                        case MovePlayer:
                            MovePlayer movePlayer = (MovePlayer)object;
                            gui.movePlayer(movePlayer);
                            break;                            
                            
                        case NewTurn:
                            NewTurn newTurn = (NewTurn)object;
                            processNewTurn(newTurn);
                            break;
                        
                        case ChangeAttribute:
                            ChangePlayerAttribute changeAtt = (ChangePlayerAttribute)object;
                            gui.changeAttribute(changeAtt);
                            break;
                        
                        case GuessNumber:
                            java.awt.EventQueue.invokeLater(new Runnable() {
                                @Override
                                public void run () {
                                    int number = gui.askForNumber();
                                    sendMessage(new GuessNumber(number));
                                }});
                            break;
                            
                        case KillPlayer:
                            gui.killPlayer((KillPlayer)object);
                            break;
                            
                        case EndGame:
                            gui.endGame((EndGame)object);
                            state = ClientState.GAME_END;
                            break;
                    }
                } else if (object instanceof com.esotericsoftware.kryonet.FrameworkMessage.KeepAlive) {
                }
            }
        });

        client.start();
    }

    private void processNewTurn(NewTurn newTurn) {
        currentPlayer = newTurn.player;
        logger.debug("Current player: " + newTurn.player);
        if(havePlayer(newTurn.player)){
            gui.setCurrentPlayer(newTurn.player, true);
        }else{
            gui.setCurrentPlayer(newTurn.player, false);
        }
    }

    public boolean havePlayer(String playerName) {
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
        StringBuilder sb = new StringBuilder();
        
        String author = "";
        if(players.isEmpty()){
            author = new Integer(client.getID()).toString();
        }else{
            for (PlayerData pd : players) {
                sb.append(pd.getName()).append(", ");
            }
            sb.setLength(sb.length() - ", ".length());
            author = sb.toString();
        }
        logger.debug("Sending ChatMessage: " + text + " from " + author);
        ChatMessage msg = new ChatMessage(text, author);
        sendMessage(msg);
        gui.addChatMessage(text, author);
    }
    
    public void addPlayer(String name, String classname){
        sendMessage(new RegistrationRequest(name, classname));
    }

    public void removePlayer(String name) {
        sendMessage(new UnregisterRequest(name));
    }

    public void toggleReadyStatus() {
        sendMessage(new Message(NetworkMessageType.ClientReady));
    }

    public void endTurn() {
        sendMessage( new NewTurn(currentPlayer) );
    }

    public void moveTo(int x, int y) {
        //Client sends 0 as rotation because he doesn't know which tile
        //is going to be placed, nor its number
        sendMessage(new MovePlayer(x, y, currentPlayer));
    }

    public void searchTile() {
        logger.debug("Searching tile. NOT YET IMPLEMENTED.");
    }

    class PlayerData {

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
