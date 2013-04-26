package jdungeonquest;

import jdungeonquest.network.NetworkClient;
import jdungeonquest.network.NetworkServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GUI extends JFrame {

    Game game;
    JPanel mainMenuPanel;
    JPanel serverPanel;
    JPanel clientPanel;

    Logger logger = LoggerFactory.getLogger(GUI.class);

    
    GUI(Game g) {
        super();
        game = g;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logger.info("GUI created");
    }

    void showClient() {
        MigLayout layout = new MigLayout("fill", "[]", "[fill, grow][fill, grow]");
        clientPanel = new JPanel(layout);

        JLabel clientTest = new JLabel("client test");
        
        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(clientPanel);
                showMainMenu();
            }
        });

        clientPanel.add(clientTest);
        clientPanel.add(goBackButton);
        add(clientPanel);
        pack();
    }

    void showServer() {
        MigLayout layout = new MigLayout("fill", "[]", "[fill, grow][fill, grow]");
        serverPanel = new JPanel(layout);

        JLabel serverTest = new JLabel("server test");

        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(serverPanel);
                showMainMenu();
            }
        });

        serverPanel.add(serverTest);
        serverPanel.add(goBackButton);
        add(serverPanel);
        pack();
    }

    void showMainMenu() {

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
