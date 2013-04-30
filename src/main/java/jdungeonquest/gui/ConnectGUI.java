package jdungeonquest.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import jdungeonquest.network.NetworkClient;
import net.miginfocom.swing.MigLayout;

public class ConnectGUI extends JPanel {

    GUI parent;
    JTextField portTextField;
    JTextField ipTextField;
    public JLabel infoLabel;

    public ConnectGUI(GUI parent) {
        this.parent = parent;

        initGUI();
    }

    void setClient(NetworkClient client) {
        parent.client = client;
    }
    
    private void initGUI() {
        MigLayout layout = new MigLayout("fill", "", "[][]");
        setLayout(layout);

        JLabel ipLabel = new JLabel("Server address:");
        ipTextField = new JTextField("127.0.0.1");

        JLabel portLabel = new JLabel("Server port:");
        portTextField = new JTextField("4445");

        infoLabel = new JLabel("Enter server data:");

        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (parent.getClient() != null) {
                    parent.getClient().stop();
                }
                NetworkClient client = new NetworkClient(ipTextField.getText(), Integer.parseInt(portTextField.getText()), parent);
                setClient(client);
                client.run();
                client.connectToServer();
            }
        });

        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showMainMenu();
            }
        });
        
        add(infoLabel, "grow, span");
        add(ipLabel);
        add(ipTextField, "wrap");
        add(portLabel);
        add(portTextField, "wrap");
        add(connectButton, "grow, span");
        add(goBackButton, "grow, span");
    }
}
