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
import jdungeonquest.network.NetworkClient;
import net.miginfocom.swing.MigLayout;

class LobbyGUI extends JPanel{
    GUI parent;
    
    NetworkClient client;
    //UserData data;
    
    MigLayout layout;
    //ChatBox chat;
    //GameMap map;
    //JTextArea textArea;
    
    LobbyGUI(GUI parent, NetworkClient client){
        this.parent = parent;
        this.client = client;
        
        initGUI();
    }

    private void initGUI() {
        layout = new MigLayout("fill", "[][]", "[fill, grow][fill, grow]");
        this.setLayout(layout);

        JLabel clientTest = new JLabel("client test");
        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.showMainMenu();
            }
        });
        add(clientTest);
        add(goBackButton, "grow, wrap");
        
        String time = new SimpleDateFormat("[HH:mm:ss]").format(new Date());
        JList messageList;
        add(new JScrollPane(messageList = new JList()), "grow, span");
        messageList.setModel(new DefaultListModel());
        ((DefaultListModel)messageList.getModel()).addElement(time + ": " + "test message");
        messageList.ensureIndexIsVisible(((DefaultListModel)messageList.getModel()).size() - 1);

        
    }
}
