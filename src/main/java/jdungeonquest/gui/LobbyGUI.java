package jdungeonquest.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
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
import javax.swing.JToggleButton;
import net.miginfocom.swing.MigLayout;

class LobbyGUI extends JPanel{
    GUI parent;
    
    JList messageList;
    JTextField textField;
    JButton sendButton;
    JToggleButton readyButton;
    JButton addPlayerButton;
    List<HeroPanel> heroPanels = new ArrayList();
    JPanel heroHolder = new JPanel();
    boolean readyFlag = false;

    LobbyGUI(GUI parent){
        this.parent = parent;
        
        initGUI();
    }

    public static void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }
    
    private void initGUI() {
        MigLayout layout = new MigLayout("fill", "[][grow 70][]", "[grow 0][grow][grow 30][grow 0]");
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
        
        readyButton = new JToggleButton("Ready");
        readyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readyFlag = !readyFlag;
                if(readyFlag){
                    enableComponents(heroHolder, false);
                    parent.getClient().toggleReadyStatus();
                }else{
                    enableComponents(heroHolder, true);
                    parent.getClient().toggleReadyStatus();
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
        add(sendButton, "w :70:, grow");
        add(readyButton, "wrap");
        
        add(playersLabel);
        add(new JScrollPane(messageList), "grow, spany 2, spanx, wrap");
        heroHolder.setLayout(new MigLayout("", "[]"));
        heroHolder.add( new HeroPanel(this),"wrap" );
        add(heroHolder);
       
//        add(new JScrollPane(playerList), "grow");
        add(goBackButton, "newline, grow, span");
        
    }

    void addChatMessage(String msg, String author) {
        String time = new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
        final String text = time + author + ": " + msg;
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                ((DefaultListModel) messageList.getModel()).addElement(text);
                messageList.ensureIndexIsVisible(((DefaultListModel) messageList.getModel()).size() -1);
            }
        });        
    }

    void addLocalPlayer(String newPlayer) {
        for(Component c : heroHolder.getComponents()){
            if(c instanceof HeroPanel){
                if( ((HeroPanel)c).nameField.getText().equals(newPlayer)){
                    ((HeroPanel)c).setHero();
                }
            }
        }
        
        HeroPanel h = new HeroPanel(this);
        heroHolder.add(h, "wrap");

        revalidate();
    }

    void removeLocalPlayer(String playerName) {
        for(Component c : heroHolder.getComponents()){
            if(c instanceof HeroPanel){
                if( ((HeroPanel)c).nameLabel.getText().equals(playerName)){
                    heroHolder.remove(c);
                }
            }
        }        
        revalidate();
    }
    
    void addRemotePlayer(String newPlayer) {
        JLabel l = new JLabel(newPlayer);
        heroHolder.add(l, "wrap");

        revalidate();
    }

    void removeRemotePlayers() {
        for(Component c : heroHolder.getComponents()){
            if(c instanceof JLabel){
                heroHolder.remove(c);
            }
        }
        revalidate();
    }

    /**
     * Panel used for adding and removing local players
     */
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
                    if(nameField.getText().equals("")){
                        return;
                    }
                    parent.parent.getClient().addPlayer(nameField.getText(), (String)heroComboBox.getSelectedItem());
                }
            });
            
            removeHero.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parent.parent.getClient().removePlayer(nameField.getText());
                }
            });
            
            add(nameField, "w :100:");
//            add(heroComboBox);
            add(addHero);
        }
        
        private void setHero(){
            remove(nameField);
//            remove(heroComboBox);
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
            
            add(nameField, "w :100:");
//            add(heroComboBox);
            add(addHero);
            revalidate();
            repaint();
        }
    }
}
