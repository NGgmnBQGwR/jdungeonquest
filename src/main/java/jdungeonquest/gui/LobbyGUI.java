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
    JButton sendButton;
    JButton addPlayerButton;
    JTextField nameTextField;

    LobbyGUI(GUI parent){
        this.parent = parent;
        
        initGUI();
    }

    void addPlayer(String name){
        ((DefaultListModel)playerList.getModel()).add(((DefaultListModel) playerList.getModel()).size(), name);
    }
    
    private void initGUI() {
        MigLayout layout = new MigLayout("fill", "[grow 25][grow 25][grow 50]", "[grow 0][grow 0][grow 100][grow 0]");
        this.setLayout(layout);

        textField = new JTextField("");
        JLabel nameLabel = new JLabel("Player name:");
        nameTextField = new JTextField("GenericPlayer");
        
        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showMainMenu();
            }
        });
        
        addPlayerButton = new JButton("Add Player");
        addPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer(nameTextField.getText());
            }
        });
        
        sendButton = new JButton("Send");
        sendButton.setEnabled(false);
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                parent.getClient().sendChatMessage(text);
            }
        });
        
        messageList = new JList();
        messageList.setModel(new DefaultListModel());
        messageList.ensureIndexIsVisible(((DefaultListModel) messageList.getModel()).size() - 1);
        
        JLabel playersLabel = new JLabel("Player list");
        playerList = new JList();
        playerList.setModel(new DefaultListModel());
        playerList.ensureIndexIsVisible(((DefaultListModel) playerList.getModel()).size() - 1);

        
        add(nameLabel, "grow");
        add(nameTextField, "grow");
        add(addPlayerButton, "grow, wrap");
        
        add(textField, "grow, push");
        add(sendButton, "w 70!, wrap");
        
        add(playersLabel);
        add(new JScrollPane(messageList), "grow, spanx, spany 2, wrap");
        
        add(new JScrollPane(playerList));
        add(goBackButton, "newline, grow, span");
    }

    void addChatMessage(String msg, String author) {
        String time = new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
        String text = time + author + ":" + msg;
        ((DefaultListModel)messageList.getModel()).add( ((DefaultListModel)messageList.getModel()).getSize(), text);
    }
}
