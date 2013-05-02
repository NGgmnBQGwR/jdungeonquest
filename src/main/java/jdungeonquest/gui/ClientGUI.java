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
import net.miginfocom.swing.MigLayout;

public class ClientGUI extends JPanel{
    GUI parent;
    
    ChatPanel chatPanel;
//    SunPanel sunPanel;
//    PlayerHolderPanel playerHolderPanel;
//    MapPanel mapPanel;
    
    ClientGUI(GUI parent){
        this.parent = parent;
        
        initGUI();
    }

    private void initGUI() {
        chatPanel = new ChatPanel(this);
        
        add(chatPanel);
    }

    void addChatMessage(String msg, String author) {
        String time = new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
        String text = time + author + ":" + msg;
        ((DefaultListModel) chatPanel.messageList.getModel()).add(((DefaultListModel) chatPanel.messageList.getModel()).getSize(), text);
    }
    
    private class ChatPanel extends JPanel{

        JList messageList;
        ClientGUI parent;
        JButton sendButton;
        JTextField textField;
        
        public ChatPanel(ClientGUI p) {
            this.parent = p;
            MigLayout layout = new MigLayout("", "[grow 0][grow 0]", "[grow 0]");
            setLayout(layout);
            
            messageList = new JList();
            messageList.setModel(new DefaultListModel());
            messageList.ensureIndexIsVisible(((DefaultListModel) messageList.getModel()).size() - 1);

            textField = new JTextField("");

            sendButton = new JButton("Send");
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = textField.getText();
                    textField.setText("");
                    parent.parent.getClient().sendChatMessage(text);
                }
            });  
            
            add(textField, "grow, push");
            add(sendButton, "wrap");
            add(new JScrollPane(messageList), "grow");
        }
    }
}
