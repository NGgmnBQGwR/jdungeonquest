package jdungeonquest.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import jdungeonquest.network.NetworkClient;
import jdungeonquest.network.PlayerList;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GUI extends JFrame {

     NetworkClient getClient() {
        return client;
    }
    
    public void playerRegistered(boolean b) {
//        logger.debug("playerRegistered " + b);
//        if(b){
//            connectGUI.infoLabel.setText("Registered on server.");
//            lobbyGUI.sendButton.setEnabled(true);
//            lobbyGUI.addPlayer(client.getClientName());
//        }else{
//            connectGUI.infoLabel.setText("Not connected.");
//            lobbyGUI.sendButton.setEnabled(false);
//        }
    }

    JPanel mainMenuPanel;
    JPanel serverPanel;
    Logger logger = LoggerFactory.getLogger(GUI.class);

    NetworkClient client;
    ConnectGUI connectGUI = new ConnectGUI(this);
    LobbyGUI lobbyGUI = new LobbyGUI(this);
    ServerGUI serverGUI = new ServerGUI(this);
    JComponent recentPanel;
    //ClientGUI clientGUI;
    
    public GUI() {
        super("JDungeonQuest Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("GUI created");
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

        add(mainMenuPanel);
        pack();
        setVisible(true);
    }

    public void addChatMessage(String text, String author) {
        if(recentPanel == lobbyGUI){
            lobbyGUI.addChatMessage(text, author);
        }else{
            logger.debug("Tried to call a not implemented yet addChatMessage()");
        }
    }

    public void updatePlayerList(PlayerList p) {
        if (recentPanel == lobbyGUI) {
            ((DefaultListModel) lobbyGUI.playerList.getModel()).clear();
            for (String player : p.players) {
                lobbyGUI.addPlayer(player);
            }
        } else {
        }
    }
}
