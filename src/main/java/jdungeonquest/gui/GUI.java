package jdungeonquest.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import jdungeonquest.Tile;
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

        MigLayout layout = new MigLayout("fill", "[]", "[fill, grow]");
        mainMenuPanel = new JPanel(layout);
        recentPanel = mainMenuPanel;

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
        
        logger.debug("Placing tile " + tile + " at " + x + ":" + y);
        Tile t = clientGUI.tileHolder.takeSpecificTile(tile);
        t.rotate(placeTile.rotate);
        clientGUI.map.setTile(x, y, t);
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
}
