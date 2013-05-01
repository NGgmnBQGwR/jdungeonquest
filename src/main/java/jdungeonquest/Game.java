package jdungeonquest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jdungeonquest.enums.PlayerAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

    List<Player> players = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(Game.class);
    Map<String, Boolean> playerClasses = new HashMap();
    Map<String, Boolean> playerReadyStatus = new HashMap();

    public Game() {
        playerClasses.put("A", false);
        playerClasses.put("B", false);
        playerClasses.put("C", false);
        playerClasses.put("D", false);
    }

    public void broadCast(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This internal method broadcasts command to change gold to all connected
     * GUIs.
     *
     */
    public void changePlayerAttribute(Player player, PlayerAttributes attribute, int amount) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int GetPlayerAttribute(Player currentPlayer, PlayerAttributes playerAttributes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // SUBJECT TO CHANGE
    // <code>getCurrentPlayer</code> --- возвращает, нарпимер, текстовое имя игрока или некий внутренний id
    public Player getCurrentPlayer() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //return null;
    }

    public boolean toggleReadyPlayer(String playerName) {
        boolean value = !(playerReadyStatus.get(playerName));
        playerReadyStatus.put(playerName, value);
        logger.debug("Player " + playerName + " ready status:" + value);
        if (false) {
            return true;
        }
        return false;
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
}
