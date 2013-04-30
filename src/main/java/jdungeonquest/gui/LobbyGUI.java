package jdungeonquest.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

class LobbyGUI extends JPanel{
    GUI parent;
    
    JList messageList;
    JTextField textField;
    JButton sendButton;
    JButton addPlayerButton;
    List<HeroPanel> heroPanels = new ArrayList();
    JPanel heroHolder = new JPanel();
    
    LobbyGUI(GUI parent){
        this.parent = parent;
        
        initGUI();
    }

    void addPlayer(String name) {
//        ((DefaultListModel) playerList.getModel()).add(((DefaultListModel) playerList.getModel()).size(), name);
    }    

    private void initGUI() {
        MigLayout layout = new MigLayout("fill", "[grow 25][grow 75]", "[grow 0][grow 50]");
        this.setLayout(layout);

        textField = new JTextField("");
        
        JButton goBackButton = new JButton("Back");
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(JOptionPane.showConfirmDialog(null, "You are going to be disconnected from the server. Return to main menu?") == 0){
                    parent.getClient().stop();
                    parent.showMainMenu();
                }
            }
        });
        
        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                textField.setText("");
                parent.getClient().sendChatMessage(text);
            }
        });
        
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendButton.doClick();
            }
        });
        
        messageList = new JList();
        messageList.setModel(new DefaultListModel());
        messageList.ensureIndexIsVisible(((DefaultListModel) messageList.getModel()).size() - 1);
        
        JLabel playersLabel = new JLabel("Player list");
        
        add(textField, "grow, push");
        add(sendButton, "w 70!, wrap");
        
//        add(heroPanels[0]);
        add(playersLabel);
        add(new JScrollPane(messageList), "grow, spany 2, wrap");
        heroHolder.setLayout(new MigLayout("", "[]"));
        heroHolder.add(new HeroPanel(this),"wrap");
        heroHolder.add(new HeroPanel(this),"wrap");
        heroHolder.add(new HeroPanel(this),"wrap");
        heroHolder.add(new HeroPanel(this),"wrap");
        add(heroHolder);
        
        for(HeroPanel p: heroPanels){
            add(p);
        }
        
//        add(new JScrollPane(playerList), "grow");
        add(goBackButton, "newline, grow, span");
        
    }

    void addChatMessage(String msg, String author) {
        String time = new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
        String text = time + author + ":" + msg;
        ((DefaultListModel)messageList.getModel()).add( ((DefaultListModel)messageList.getModel()).getSize(), text);
    }

    private static class HeroPanel extends JPanel {

        JLabel nameLabel = new JLabel("");
        JTextField nameField = new JTextField("Hero");
        JComboBox heroComboBox = new JComboBox(new String[]{"A","B","C","D"});
        JButton addHero = new JButton("Add hero");
        JButton removeHero = new JButton("Remove hero");
        LobbyGUI parent;
        
        public HeroPanel(LobbyGUI parent) {
            this.parent = parent;

            initGUI();            
        }
        
        private void initGUI(){
            MigLayout layout = new MigLayout("fill", "[][][]", "[grow 0]");
            setLayout(layout);
            
            addHero.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setHero();
                }
            });
            
            removeHero.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeHero();
                }
            });
            
            add(nameField);
            add(heroComboBox);
            add(addHero);
        }
        
        private void setHero(){
            remove(nameField);
            remove(heroComboBox);
            remove(addHero);
            
            nameLabel.setText(nameField.getText());
            
            add(nameLabel, "spanx 1");
            add(removeHero);
            revalidate();
            repaint();
        }
        
        private void removeHero(){
            remove(nameLabel);
            remove(removeHero);
            
            add(nameField);
            add(heroComboBox);
            add(addHero);
            revalidate();
            repaint();
        }
    }
}
