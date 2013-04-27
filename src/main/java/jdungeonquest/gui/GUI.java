package jdungeonquest.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import jdungeonquest.Game;
import jdungeonquest.network.NetworkClient;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GUI extends JFrame {

    JPanel mainMenuPanel;
    JPanel serverPanel;
    Logger logger = LoggerFactory.getLogger(GUI.class);

    NetworkClient client = new NetworkClient();
    LobbyGUI clientGUI = new LobbyGUI(this, client);
    ServerGUI serverGUI = new ServerGUI(this);
    //LobbyGUI LobbyGUI;
    
    public GUI() {
        super("JDungeonQuest Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("GUI created");
    }

    void showClient() {
        remove(mainMenuPanel);
        add(clientGUI);
        pack();
    }

    void showServer() {
        remove(mainMenuPanel);
        add(serverGUI);
        pack();
    }

    public void showMainMenu() {
        remove(clientGUI);
        remove(serverGUI);

        MigLayout layout = new MigLayout("fill", "[]", "[fill, grow]");
        mainMenuPanel = new JPanel(layout);

        JButton serverButton = new JButton("Server");
        JButton clientButton = new JButton("Client");

        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(mainMenuPanel);
                showServer();
            }
        });

        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(mainMenuPanel);
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
