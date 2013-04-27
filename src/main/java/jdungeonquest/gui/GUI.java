package jdungeonquest.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import jdungeonquest.network.NetworkClient;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GUI extends JFrame {

    JPanel mainMenuPanel;
    JPanel serverPanel;
    Logger logger = LoggerFactory.getLogger(GUI.class);

    NetworkClient client;
    LobbyGUI lobbyGUI = new LobbyGUI(this, client);
    ServerGUI serverGUI = new ServerGUI(this);
    JComponent recentPanel;
    //ClientGUI clientGUI;
    
    public GUI() {
        super("JDungeonQuest Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("GUI created");
    }

    void showClient() {
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
        JButton clientButton = new JButton("Client");

        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showServer();
            }
        });

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showClient();
            }
        });

        mainMenuPanel.add(serverButton, "grow");
        mainMenuPanel.add(clientButton, "grow");

        add(mainMenuPanel);
        pack();
        setVisible(true);
    }
}
