package jdungeonquest.gui;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jdungeonquest.Tile;
import jdungeonquest.network.BattleAction;
import jdungeonquest.network.ChangePlayerAttribute;
import jdungeonquest.network.EndBattle;
import jdungeonquest.network.EndGame;
import jdungeonquest.network.KillPlayer;
import jdungeonquest.network.MovePlayer;
import jdungeonquest.network.NetworkClient;
import jdungeonquest.network.PlaceTile;
import jdungeonquest.network.PlayerList;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GUI extends JFrame {
    
    JPanel mainMenuPanel;
    JPanel serverPanel;
    Logger logger = LoggerFactory.getLogger(GUI.class);

    NetworkClient client;
    ConnectGUI connectGUI = new ConnectGUI(this);
    LobbyGUI lobbyGUI = new LobbyGUI(this);
    ServerGUI serverGUI = new ServerGUI(this);
    ClientGUI clientGUI = new ClientGUI(this);
    JComponent recentPanel;
    
    public GUI() {
        super("JDungeonQuest Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("GUI created");
    }

     NetworkClient getClient() {
        return client;
    }    
    
    void showConnect() {
        remove(recentPanel);
        recentPanel = connectGUI;
        add(connectGUI);
        pack();
    }

    public void showLobby() {
        remove(recentPanel);
        recentPanel = lobbyGUI;
        add(lobbyGUI);
        pack();
    }
    
    void showServer() {
        remove(recentPanel);
        recentPanel = serverGUI;
        add(serverGUI);
        pack();
    }
    
    public void showClient() {
        remove(recentPanel);
        recentPanel = clientGUI;
        add(clientGUI);
        pack();
    }

    public void showMainMenu() {
        if(recentPanel != null){
            remove(recentPanel);
        }

        MigLayout layout = new MigLayout("fill", "[][]", "[][fill, grow]");
        mainMenuPanel = new JPanel(layout);
        recentPanel = mainMenuPanel;

        JLabel titleLabel = new JLabel("jDungeonQuest");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
        JButton serverButton = new JButton("Server");
        JButton lobbyButton = new JButton("Client");

        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showServer();
            }
        });

        lobbyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showConnect();
            }
        });

        mainMenuPanel.add(titleLabel, "center, spanx, wrap");
        mainMenuPanel.add(serverButton, "grow");
        mainMenuPanel.add(lobbyButton, "grow");

//        add(clientGUI);
        add(mainMenuPanel);
        pack();
        setVisible(true);
    }

    
     /**
      * Updates the player list to show current players.
      * Distinguishing between local/remote players is done here.
      * @param p 
      */
    public void updatePlayerList(PlayerList p) {
//            ((DefaultListModel) lobbyGUI.playerList.getModel()).clear();
        lobbyGUI.removeRemotePlayers();
        for (String player : p.players) {
            if(!getClient().havePlayer(player)){ //ignore local players
                playerRegistered(false, player);
            }
        }
    }   
     
    public void playerRegistered(boolean localPlayer, String newPlayer) {
        logger.debug("playerRegistered " + (localPlayer?"local ":"remote ") + newPlayer);
        if(localPlayer){
            lobbyGUI.addLocalPlayer(newPlayer);
        }else{
            lobbyGUI.addRemotePlayer(newPlayer);
        }
    }


    public void playerUnregistered(String playerName) {
        lobbyGUI.removeLocalPlayer(playerName);
    }    
    
    public void addChatMessage(String text, String author) {
        if(recentPanel == lobbyGUI){
            lobbyGUI.addChatMessage(text, author);
        }else if(recentPanel == clientGUI){
            clientGUI.addChatMessage(text, author);
        }else{
            logger.debug("Tried to call a not implemented yet addChatMessage()");
        }
    }

    public void placeTile(PlaceTile placeTile) {
        final int tile = placeTile.tile;
        final int x = placeTile.x;
        final int y = placeTile.y;
        
        Tile t = clientGUI.tileHolder.takeSpecificTile(tile);
        t.rotate(placeTile.rotate);
        logger.debug("Placing tile " + tile + " " + t + " at " + x + ":" + y);
        clientGUI.map.setTile(x, y, t);
        int tile_x = (x-2)*150;
        int tile_y = (y-2)*150;
//        clientGUI.mapScrollPane.scrollRectToVisible(new Rectangle(800, 800, 1, 1);
        clientGUI.mapScrollPane.getVerticalScrollBar().setValue(tile_y);
        clientGUI.mapScrollPane.getHorizontalScrollBar().setValue(tile_x);
        clientGUI.repaint();
    }
    
    public void movePlayer(MovePlayer movePlayer) {
        clientGUI.playerPosition.put(movePlayer.getPlayer(), new int[]{ movePlayer.getX(), movePlayer.getY() });
        logger.debug("Current player positions: " + clientGUI.playerPosition);
        clientGUI.repaint();
    }

    public void setCurrentPlayer(String player, boolean localPlayer) {
        clientGUI.selectPlayer(player, localPlayer);
    }

    public void initPlayers(List<String> players) {
        clientGUI.initPlayers(players);
    }

    public void changeAttribute(ChangePlayerAttribute changeAtt) {
        clientGUI.changeAttribute(changeAtt);
    }

    public int askForNumber() {
        return clientGUI.askForNumber();
    }

    public void killPlayer(KillPlayer killPlayer) {
        clientGUI.killPlayer(killPlayer);
    }

    public void endGame(EndGame endGame) {
        clientGUI.endGame(endGame);
    }

    public int askForStartBattleChoice() {
        return clientGUI.askForStartBattleChoice();
    }

    public void processBattleAction(BattleAction battleAction) {
        if(battleAction.action == 0){
            clientGUI.showBattleDialog();
        }
    }

    public void processEndBattle(EndBattle endBattle) {
        clientGUI.hideBattleDialog();
    }
}
