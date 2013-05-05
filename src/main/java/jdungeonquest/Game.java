package jdungeonquest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdungeonquest.enums.PlayerAttributes;
import jdungeonquest.network.ChatMessage;
import jdungeonquest.network.Message;
import jdungeonquest.network.MovePlayer;
import jdungeonquest.network.PlaceTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

    List<Player> players = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(Game.class);
    Map<String, Boolean> playerClasses = new HashMap();
    Map<String, Boolean> playerReadyStatus = new HashMap();
    Player currentPlayer;
    GameMap map;

    TileHolder tileHolder;
    CardHolder cardHolder;
    
    private int turn = 0;
    
    public List<Message> messageQueue;
    
    public Game() {
        playerClasses.put("A", false);
        playerClasses.put("B", false);
        playerClasses.put("C", false);
        playerClasses.put("D", false);
        
        tileHolder = new TileHolder();
        cardHolder = new CardHolder();
        
        messageQueue = new ArrayList<>();
        map = new GameMap();
    }

    public void changePlayerAttribute(Player player, PlayerAttributes attribute, int amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int GetPlayerAttribute(Player currentPlayer, PlayerAttributes playerAttributes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // SUBJECT TO CHANGE
    // <code>getCurrentPlayer</code> --- возвращает, нарпимер, текстовое имя игрока или некий внутренний id
    public String getCurrentPlayer() {
        return currentPlayer.getName();
    }

    public boolean toggleReadyPlayer(String playerName) {
        boolean value = !(playerReadyStatus.get(playerName));
        playerReadyStatus.put(playerName, value);
        logger.debug("Player " + playerName + " ready status:" + value);
//        if ( isEveryoneReady() ) {
//            return true;
//        }
//        return false;
        return true;
    }

    public boolean registerPlayer(String playerName, String playerClass) {
        if(playerName.equals("")){
            return false;
        }
        if(isPlayerRegistered(playerName)){
            return false;
        }
//        if(playerClasses.get(playerClass)){
//            return false;
//        }
        
        Player newPlayer = new Player();
        newPlayer.setName(playerName);
        newPlayer.setClassname(playerClass);
        
        players.add(newPlayer);
        playerClasses.put(playerClass, true);

        playerReadyStatus.put(playerName, false);

        logger.debug("Game registered player " + playerName + " with class " + playerClass);
        return true;
    }
    
    public boolean unregisterPlayer(String playerName) {
        if(playerName.equals("")){
            return false;
        }
        if(!isPlayerRegistered(playerName)){
            return false;
        }
        Iterator<Player> iter = players.iterator();
        while(iter.hasNext()){
            Player p = iter.next();
            if(p.getName().equals(playerName)){
                iter.remove();                
                playerReadyStatus.remove(playerName);
            }
        }
        logger.debug("Game unregistered player " + playerName);
        return true;
    }

    public boolean isPlayerRegistered(String playerName) {
        for (Player p : players) {
            if (playerName.equals(p.getName())) {
                return true;
            }
        }
        return false;
    }

    public String[] getPlayerList() {
        String[] playerList = new String[players.size()];
        for (int i = 0; i < players.size(); i++) {
            playerList[i] = players.get(i).getName();
        }
        return playerList;
    }

    public boolean isEveryoneReady() {
        assert (playerReadyStatus.size() == players.size());
        
        if(playerReadyStatus.isEmpty()){
            return false;
        }
        for (Boolean status : playerReadyStatus.values()) {
            if (!status) {
                return false;
            }
        }
        return true;
    }

    public void startGame() {
        setUp();
    
        addMessage(new ChatMessage("Turn: " + turn, "Game"));
        for (Player player : players) {
            setCurrentPlayer(player);
        }

        turn++;
    }

    private void setUp() {
        switch(players.size()){
            case 0: endGame(); break;
            case 4: placeTile(GameMap.MAX_X-1, GameMap.MAX_Y-1, tileHolder.startingTile, 0);
            case 3: placeTile(0, GameMap.MAX_Y-1, tileHolder.startingTile, 0);
            case 2: placeTile(GameMap.MAX_X-1, 0, tileHolder.startingTile, 0);
            case 1: placeTile(0, 0, tileHolder.startingTile, 0);
                    movePlayer(0, 0, players.get(0)); break;
            default: break; //only 4 players are supported right now
        }
    }

    private void placeTile(int x, int y, Tile tile, int rotation) {
        int tileNumber = tileHolder.getTileNumber(tile);
        map.setTile(x, y, tile);
        addMessage( new PlaceTile(x, y, tileNumber, rotation));
    }

    private void movePlayer(int x, int y, Player player) {
        player.setPosition( new Position(x,y));
        addMessage( new MovePlayer(x, y, player.getName()));
    }

    private void addMessage(Message m) {
        logger.debug("Adding message to queue: " + m);
        messageQueue.add(m);
    }

    private void endGame() {
        System.exit(1);
    }

    private void setCurrentPlayer(Player player) {
        logger.debug("Current player: " + player.getName());
//        addMessage(new );
    }

    public void processPlayerMove(PlaceTile placeTile, String playerName) {
        Player player = null;
        for(Player p : players){
            if(playerName.equals(p.getName())){
                player = p;
                break;
            }
        }
        if(player == null){
            return;
        }
        //this is a hack until movement/turn sequence is complete
        Tile tile = tileHolder.takeSpecificTile(0);
        int tileNumber = tileHolder.getTileNumber(tile);
        
        Position to = new Position(placeTile.x, placeTile.y);
        logger.debug("Processing move of " + playerName + " from " + player.getPosition() + " to " + to);
        //check that it is this player's turn
        //disabled because currentPlayer cannot be relied upon yet
//        if(currentPlayer.getName() != playerName){
//            return;
//        }
        //check that he haven't moved this turn yet
        //check that there is nothing on that tile yet
        if(!map.isFree(placeTile.x, placeTile.y)){
            return;
        }
        //check that there is no one in that tile
        //check that tile is adjacent
        if(!map.isAdjacent(new Position(placeTile.x, placeTile.y), to)){
            return;
        }
        //check that you can enter that tile from current one
        if(!map.canMoveTo(new Position(placeTile.x, placeTile.y), to)){
            return;
        }
        //actually place tile on the map
        int tileRotation = map.placeTile(new Position(placeTile.x, placeTile.y), to, tile);
       
        addMessage(new PlaceTile(placeTile.x, placeTile.y, tileNumber, tileRotation));
        addMessage(new MovePlayer(placeTile.x, placeTile.y, playerName));
        player.setPosition(to);
    }

}
