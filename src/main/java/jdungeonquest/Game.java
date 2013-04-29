package jdungeonquest;

import java.util.ArrayList;
import java.util.List;
import jdungeonquest.enums.PlayerAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Game {

    List<Player> players = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(Game.class);

    public Game(){
        
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

    public boolean registerPlayer(String playerName) {
        for (Player p : players) {
            if (playerName.equals(p.getName())) {
                return false;
            }
        }

        Player newPlayer = new Player(playerName);
        players.add(newPlayer);
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
        String[] playerList = new String[ players.size() ];
        for(int i=0; i<players.size(); i++){
            playerList[i] = players.get(i).getName();
        }
        return playerList;
    }

    private static class PlayerAttributeEnum {
    }
}
