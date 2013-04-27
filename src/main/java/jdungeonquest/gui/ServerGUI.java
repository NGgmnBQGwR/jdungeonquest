package jdungeonquest.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jdungeonquest.network.NetworkServer;
import net.miginfocom.swing.MigLayout;

class ServerGUI extends JPanel {

    GUI parent;
    private boolean serverStarted = false;
    JButton startButton;
    JButton stopButton;
    NetworkServer server;
    JTextField portTextField;
    JLabel infoLabel;

    ServerGUI(GUI parent) {
        this.parent = parent;

        initGUI();
    }

    private void initGUI() {
        MigLayout layout = new MigLayout("fill", "[][]", "[][]");
        this.setLayout(layout);

        infoLabel = new JLabel("No server running");

        JLabel portLabel = new JLabel("Server port:");
        portTextField = new JTextField("4445");

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);

        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showMainMenu();
            }
        });
        add(infoLabel, "span");
        add(portLabel);
        add(portTextField, "wrap");

        add(startButton);
        add(stopButton, "wrap");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int port = Integer.valueOf(portTextField.getText());
                server = new NetworkServer();
                server.start();
                infoLabel.setText("Server started on port " + port);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stop();
                infoLabel.setText("No server running");
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
            }
        });

        add(goBackButton, "grow, wrap, span");
    }
}
