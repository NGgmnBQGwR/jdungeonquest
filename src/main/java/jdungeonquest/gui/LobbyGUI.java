package jdungeonquest.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import jdungeonquest.network.NetworkClient;
import net.miginfocom.swing.MigLayout;

class LobbyGUI extends JPanel{
    GUI parent;
    
    NetworkClient client;
    //UserData data;
    //ChatBox chat;
    //GameMap map;
    //JTextArea textArea;
    JList messageList;
    JList playerList;
    ConnectPanel connectPanel;
    JTextField textField;
    
    LobbyGUI(GUI parent, NetworkClient client){
        this.parent = parent;
        this.client = client;
        
        initGUI();
    }

    private void initGUI() {
        MigLayout layout = new MigLayout("fill", "[][]", "[][]");
        this.setLayout(layout);

        connectPanel = new ConnectPanel(this);
        textField = new JTextField("");

        JButton goBackButton = new JButton("Back");
        JButton sendButton = new JButton("Send");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showMainMenu();
            }
        });
        
        sendButton = new JButton("Send");
        sendButton.setEnabled(false);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                getClient().sendChatMessage(text);
            }
        });
        
        messageList = new JList();
        messageList.setModel(new DefaultListModel());
        messageList.ensureIndexIsVisible(((DefaultListModel) messageList.getModel()).size() - 1);
        
        add(connectPanel, "growx, span, wrap");
        add(textField, "growx, push");
        add(sendButton, "w 70!, wrap");
        add(new JScrollPane(messageList), "grow, span");
        add(goBackButton, "growx, span");
    }

    void addChatMessage(String msg, String author) {
        String time = new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
        String text = time + author + ":" + msg;
        ((DefaultListModel)messageList.getModel()).add( ((DefaultListModel)messageList.getModel()).getSize(), text);
    }

    private static class ConnectPanel extends JPanel {

        LobbyGUI parent;
        JTextField nameTextField;
        JTextField portTextField;
        JTextField ipTextField;

        public ConnectPanel(final LobbyGUI p) {
            this.parent = p;

            MigLayout layout = new MigLayout("fill", "[][]", "[][]");
            setLayout(layout);

            JLabel ipLabel = new JLabel("Server address:");
            ipTextField = new JTextField("127.0.0.1");

            JLabel portLabel = new JLabel("Server port:");
            portTextField = new JTextField("4445");

            JLabel nameLabel = new JLabel("Player name:");
            nameTextField = new JTextField("GenericPlayer");

            JLabel infoLabel = new JLabel("Not connected to the server");

            JButton connectButton = new JButton("Connect");
            connectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(parent.client != null){
                        parent.client.stop();
                    }
                    parent.client = new NetworkClient(nameTextField.getText(), ipTextField.getText(), Integer.parseInt(portTextField.getText()), parent.parent);
                    parent.client.run();
                    parent.client.registerOnServer();
                    client.registerOnServer();
                }
            });

            add(infoLabel, "grow, span");
            add(ipLabel);
            add(ipTextField, "wrap");
            add(portLabel);
            add(portTextField, "wrap");
            add(connectButton, "grow, span");
        }
    }
}
