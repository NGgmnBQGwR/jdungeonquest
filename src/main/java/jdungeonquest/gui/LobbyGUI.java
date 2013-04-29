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
    
    //UserData data;
    //ChatBox chat;
    //GameMap map;
    //JTextArea textArea;
    JList messageList;
    JList playerList;
    JTextField textField;
    ConnectPanel connectPanel;
    JButton sendButton;
    
    NetworkClient getClient(){
        return parent.client;
    }
    
    LobbyGUI(GUI parent, NetworkClient client){
        this.parent = parent;
        
        initGUI();
    }

    void addPlayer(String name){
        ((DefaultListModel)playerList.getModel()).add(((DefaultListModel) playerList.getModel()).size(), name);
    }
    
    private void initGUI() {
        MigLayout layout = new MigLayout("fill", "", "[][]");
        this.setLayout(layout);

        connectPanel = new ConnectPanel(this);
        textField = new JTextField("");

        JButton goBackButton = new JButton("Back");
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
        
        JLabel playersLabel = new JLabel("Player list");
        playerList = new JList();
        playerList.setModel(new DefaultListModel());
        playerList.ensureIndexIsVisible(((DefaultListModel) playerList.getModel()).size() - 1);

        
        add(connectPanel, "growx, span, wrap");
        add(textField, "growx, push");
        add(sendButton, "w 70!, wrap");
        add(playersLabel);
        add(new JScrollPane(messageList), "grow, spany 2, wrap");
        add(new JScrollPane(playerList));
        add(goBackButton, "newline, growx, span");
    }

    void addChatMessage(String msg, String author) {
        String time = new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
        String text = time + author + ":" + msg;
        ((DefaultListModel)messageList.getModel()).add( ((DefaultListModel)messageList.getModel()).getSize(), text);
    }

    class ConnectPanel extends JPanel {

        LobbyGUI parent;
        JTextField nameTextField;
        JTextField portTextField;
        JTextField ipTextField;
        public JLabel infoLabel;

        NetworkClient getClient(){
            return parent.parent.client;
        }
        
        void setClient(NetworkClient client){
            parent.parent.client = client;
        }
        
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

            infoLabel = new JLabel("Not connected to the server");

            JButton connectButton = new JButton("Connect");
            connectButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(getClient() != null){
                        getClient().stop();
                    }
                    NetworkClient client = new NetworkClient(nameTextField.getText(), ipTextField.getText(), Integer.parseInt(portTextField.getText()), parent.parent);
                    setClient(client);
                    client.run();
                    client.registerOnServer();
                }
            });

            add(infoLabel, "grow, span");
            add(ipLabel);
            add(ipTextField, "wrap");
            add(portLabel);
            add(portTextField, "wrap");
            add(connectButton, "grow, span");
            add(nameLabel, "grow");
            add(nameTextField, "grow, wrap");
        }
    }
}
