package jdungeonquest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import jdungeonquest.effects.Effect;
import jdungeonquest.enums.GameState;
import jdungeonquest.enums.PlayerAttributes;
import jdungeonquest.enums.PlayerState;
import jdungeonquest.network.ChangePlayerAttribute;
import jdungeonquest.network.ChatMessage;
import jdungeonquest.network.GuessNumber;
import jdungeonquest.network.Message;
import jdungeonquest.network.MovePlayer;
import jdungeonquest.network.NewTurn;
import jdungeonquest.network.PlaceTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

    List<Player> players = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(Game.class);
    Map<String, Boolean> playerClasses = new HashMap();
    Map<String, Boolean> playerReadyStatus = new HashMap();
    Player currentPlayer;
    PlayerState currentPlayerState;
    int currentPlayerContextValue;
    GameMap map;
    private GameState state = GameState.NOT_STARTED;
    private Random random = new Random();

    TileHolder tileHolder;
    CardHolder cardHolder;
    
    private int turn = 1;
    private int sunPosition = 1;

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
        String playerName = player.getName();
        logger.debug("Changing " + playerName + " " + attribute + " to " + amount);
        addMessage(new ChangePlayerAttribute(playerName, attribute, amount));
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
        if(state != GameState.NOT_STARTED){
            return false;
        }
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
        state = GameState.IN_PROGRESS;
        setUp();
        turn = 0;
        addMessage(new ChatMessage("Turn: " + turn, "Game"));
        currentPlayer = players.get(0);
        currentPlayer.resetTurnVariables();
        logger.debug("Current player: " + currentPlayer.getName());
        
        for(Player player : players){
            changePlayerAttribute(player, PlayerAttributes.HP, 15);
            changePlayerAttribute(player, PlayerAttributes.Gold, 0);
        }
        
        addMessage(new ChatMessage("Current player: " + currentPlayer.getName(), "Game"));
        addMessage(new NewTurn(currentPlayer.getName()));
    }

    private void setUp() {
        switch(players.size()){
            case 0: endGame(); break;
            case 4: placeTile(GameMap.MAX_X-1, GameMap.MAX_Y-1, tileHolder.startingTile, 0);
                    movePlayer(GameMap.MAX_X-1, GameMap.MAX_Y-1, players.get(3));
            case 3: placeTile(0, GameMap.MAX_Y-1, tileHolder.startingTile, 0);
                    movePlayer(0, GameMap.MAX_Y-1, players.get(2));
            case 2: placeTile(GameMap.MAX_X-1, 0, tileHolder.startingTile, 0);
                    movePlayer(GameMap.MAX_X-1, 0, players.get(1));
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

    public void addMessage(Message m) {
        logger.debug("Adding message to queue: " + m);
        messageQueue.add(m);
    }

    private void endGame() {
        state = GameState.ENDED;
        System.exit(1);
    }

    public void processPlayerSearch(String playerName) {
        if(currentPlayer.searchInRow == 2){
            logger.debug(playerName + " can't search this room anymore.");
            return;
        }
        currentPlayer.searchInRow++;
        currentPlayer.setSearched(true);
        //processDrawSearchCard();
    }
    
    public void processPlayerMove(MovePlayer movePlayer, String playerName) {
        Player player = null;
        for(Player p : players){
            if(playerName.equals(p.getName())){
                player = p;
                break;
            }
        }
        if(player == null){
            logger.debug("No player " + playerName + "found.");
            return;
        }

        Position from = player.getPosition();
        Position to = new Position(movePlayer.getX(), movePlayer.getY());
        logger.debug("Processing move of " + playerName + " from " + from + " to " + to);
        //check that it is this player's turn
        if(!currentPlayer.getName().equals(playerName)){
            logger.debug("Current player is:" + currentPlayer.getName() + " so " + playerName +" can't do anyting now.");
            return;
        }
        //check that he haven't moved this turn yet
        if(currentPlayer.isMoved()){
            logger.debug("Player already moved this turn. Can't move now.");
            return;
        }
        if(currentPlayer.isSearched()){
            logger.debug("Player used search this turn. Can't move now.");
            return;
        }
        //check that there is no one in that tile
        //add exception for Treasure Chamber, any number of players can fit there
        for (Player p : players) {
            if (p.getPosition().equals(to)) {
                logger.debug("There's " + p.getName() + " on that tile. Can't move.");
                return;
            }
        }
        //check that tile is adjacent
        if(!map.isAdjacent(from, to)){
            logger.debug("Not adjacent. Can't move.");
            return;
        }
        //check that you can enter that tile from current one
        if(!map.canMoveFrom(from, to)){
            logger.debug("Can't leave from " + from + " this way. Can't move.");
            return;
        }
        //check whether there is something on that tile already
        if(map.isFree(to.getX(), to.getY())){
            //if there's nothing there yet, place a tile there
            logger.debug("Tile is empty.");

            Tile tile = tileHolder.takeTile();
            int tileNumber = tileHolder.getTileNumber(tile);
            //actually place tile on the map
            logger.debug("Placed tile " + tile + " on " + to);
            int tileRotation = map.placeTile(from, to, tile);
            currentPlayer.setPlacedTile(true);
            addMessage(new PlaceTile(to.getX(), to.getY(), tileNumber, tileRotation));
        }else if(!map.canMoveTo(from, to)){
            //moving in existing tile
            logger.debug("Can't enter " + to + " this way. Can't move.");
            return;
        }
        currentPlayer.setMoved(true);
        currentPlayer.setPosition(to);
        addMessage(new MovePlayer(to.getX(), to.getY(), playerName));
        
        processCurrentPlayerTile();
    }

    private void processCurrentPlayerTile() {
        //get position of current player 
        Position playerPosition = currentPlayer.getPosition();
        //get tile on this position
        Tile tile = map.getTile(playerPosition.getX(), playerPosition.getY());
        //get effects from this tile
        List<Effect> tileEffects = tile.getEffects();
        logger.debug("Found " + tileEffects.size() + " effects on Tile " + tile + " on " + playerPosition);
        //TODO: actual check
        //if it's not a special tile, grab a Room card
        processDrawRoomCard();
    }    
    
    private void processDrawRoomCard() {
        Card card = cardHolder.roomDeck.takeCard();
        logger.debug("Activating " + card + " card");
        card.activate(this);
    }
    
    public void endTurn(String player) {
        if(!currentPlayer.getName().equals(player)){
            logger.debug("Current player is:" + currentPlayer.getName() + " so " + player +" can't do anyting now.");
            return;
        }
        if( !currentPlayer.isMoved() && !currentPlayer.isSearched()){
            logger.debug("Player " + currentPlayer.getName() + " can't end his turn yet.");
            return;
        }
        
        newTurn();
    }

    public void newTurn(){
        int currentPlayerIndex = players.indexOf(currentPlayer);
        if(currentPlayerIndex == players.size()-1){
            currentPlayerIndex = 0;
        }else{
            currentPlayerIndex++;
        }
        currentPlayer = players.get(currentPlayerIndex);
        
        turn++;
        addMessage(new ChatMessage("Turn: " + turn, "Game"));
        if(currentPlayer == players.get(0)){
            sunPosition++;
            addMessage(new ChatMessage("Current sun position: " + sunPosition, "Game"));
        }
        if(sunPosition == 26){
            endGame();
        }
        
        logger.debug("Current player: " + currentPlayer.getName());
        addMessage(new ChatMessage("Current player: " + currentPlayer.getName(), "Game"));
        addMessage(new NewTurn(currentPlayer.getName()));
        currentPlayer.resetTurnVariables();
    }

    public void effectCaveIn() {
        currentPlayerState = PlayerState.guessCaveInNumber;
        currentPlayerContextValue = random.nextInt(6)+1;
        logger.debug("Cave-In death is set to " + currentPlayerContextValue);
        addMessage(new ChatMessage("A cave-in! Beware the falling rocks!", "Game"));
        addMessage(new GuessNumber());
    }

    public void processGuessNumber(GuessNumber guessNumber) {
        if(currentPlayerState != PlayerState.guessCaveInNumber){
            logger.debug("Recieved unwanted GuessNumber!");
            return;
        }
        logger.debug("Recieved GuessNumber:" + guessNumber);
        if(guessNumber.value == currentPlayerContextValue){
            killPlayer(currentPlayer, "A giant boulder falls on his head!");
        }else{
            hurtPlayer(currentPlayer, 3, "barely escapes death!");
        }
        currentPlayerContextValue = 0;
    }

    private void killPlayer(Player player, String cause) {
        logger.debug("Killing " + player + " because " + cause);
    }

    private void hurtPlayer(Player player, int value, String description) {
        logger.debug("Hurting " + player + " for " + value + " he " + description);
    }
}
