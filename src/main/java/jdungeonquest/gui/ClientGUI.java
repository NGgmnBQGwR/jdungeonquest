package jdungeonquest.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import jdungeonquest.GameMap;
import jdungeonquest.Tile;
import jdungeonquest.TileHolder;
import jdungeonquest.network.ChangePlayerAttribute;
import jdungeonquest.network.EndGame;
import jdungeonquest.network.KillPlayer;
import net.miginfocom.swing.MigLayout;

public class ClientGUI extends JPanel{
    GUI parent;
    
    ChatPanel chatPanel;
//    SunPanel sunPanel;
    PlayerHolder[] playerHolders;
    JPanel playerHolderPanel;
    MapPanel mapPanel;
    GameMap map;
    TileHolder tileHolder;
    Map<String, int[]> playerPosition;
    JButton endTurnButton;
    JButton searchButton;
    public static BufferedImage blankTileImage;
    JScrollPane mapScrollPane;

    ClientGUI(GUI parent){
        this.parent = parent;
        map = new GameMap();
        tileHolder = new TileHolder();
        playerPosition = new HashMap<>();
        
        try {
            blankTileImage = ImageIO.read(getClass().getResourceAsStream("/tiles/empty.png"));
        } catch (IOException ex) {
            Logger.getLogger(ClientGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initGUI();
    }

    private void initGUI() {
        MigLayout layout = new MigLayout("fill", "[][]", "[][]");
        this.setLayout(layout);
        
        playerHolderPanel = new JPanel();
        playerHolderPanel.setLayout(new FlowLayout());

        endTurnButton = new JButton("End turn");
        endTurnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parent.getClient().endTurn();
                }
            });
        
        searchButton = new JButton("Search tile");
        searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parent.getClient().searchTile();
                }
            });
        
        chatPanel = new ChatPanel(this);
        mapPanel = new MapPanel(map);
        mapPanel.setPreferredSize(new Dimension(1500, 1950));
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(isEnabled()){
                    processMouseClick(evt.getPoint().x, evt.getPoint().y);
                }
            }
        });        
        
        mapScrollPane = new JScrollPane(mapPanel);
        add(mapScrollPane, "w 200:600:1000, h 200:600:1000, grow, spany");
        add(playerHolderPanel, "wrap");
        add(chatPanel, "spanx, grow, wrap");
        add(endTurnButton);
        add(searchButton);
    }

    void processMouseClick(int x, int y){
        int tx = x / 150;
        int ty = y / 150;
        parent.getClient().moveTo(tx, ty);
    }
    
    void addChatMessage(String msg, String author) {
        String time = new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
        final String text = time + author + ": " + msg;
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                ((DefaultListModel) chatPanel.messageList.getModel()).addElement(text);
                chatPanel.messageList.ensureIndexIsVisible(((DefaultListModel) chatPanel.messageList.getModel()).size() - 1);
            }
        });
    }

    void selectPlayer(String player, boolean localPlayer) {
        for(PlayerHolder holder : playerHolders){
            holder.unselect();
            if(holder.getPlayerName().equals(player)){
                holder.select();
            }
        }
        
        if(localPlayer){
            endTurnButton.setEnabled(true);
            searchButton.setEnabled(true);
            LobbyGUI.enableComponents(mapPanel, true);
        }else{
            endTurnButton.setEnabled(false);
            searchButton.setEnabled(false);
            LobbyGUI.enableComponents(mapPanel, false);
        }        
    }

    void changeAttribute(ChangePlayerAttribute changeAtt) {
        for(PlayerHolder ph : playerHolders){
            if(ph.getPlayerName().equals(changeAtt.player)){
                switch(changeAtt.attribute){
                    case Gold:
                        ph.setGold(changeAtt.amount);
                        break;
                    case HP:
                        ph.setHp(changeAtt.amount);
                        break;
                    default:
                        break;
                }
                break;
            }
        }
    }

    int askForNumber() {
        int value = 0;
        while(value < 1 || value > 6){
            String result = JOptionPane.showInputDialog("Enter number from 1 to 6:", "1");
            try{
                value = Integer.parseInt(result);
            }catch(java.lang.NumberFormatException a){
                value = -1;
            }
        }
        return value;
    }

    private class MapPanel extends JPanel {

        GameMap map;
        BufferedImage testImage;
        public MapPanel(GameMap map) {
            this.map = map;            
        }
        
        private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) throws IOException {
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, width, height, null);
            g.dispose();
            return resizedImage;
        }

        public BufferedImage scaleImage(BufferedImage img, int width, int height, Color background) {
            int imgWidth = img.getWidth();
            int imgHeight = img.getHeight();
            if (imgWidth * height < imgHeight * width) {
                width = imgWidth * height / imgHeight;
            } else {
                height = imgHeight * width / imgWidth;
            }
            BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = newImage.createGraphics();
            try {
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g.setBackground(background);
                g.clearRect(0, 0, width, height);
                g.drawImage(img, 0, 0, width, height, null);
            } finally {
                g.dispose();
            }
            return newImage;
        }
        
        @Override
        public void paintComponent(Graphics g1) {
//            super.paint(g1);
            int w = this.getWidth();
            int h = this.getHeight();
            int step_x = w / 10;
            int step_y = h / 13;

            Graphics2D g = (Graphics2D) g1;

            for (int x = 0; x < GameMap.MAX_X; x++) {
                for (int y = 0; y < GameMap.MAX_Y; y++) {
//                    BufferedImage tileImage = null;
                    Tile tile = map.getTile(x, y);
                    BufferedImage image = null;
                    if(tile == null){
                        image = blankTileImage;
                    }
                    else{
                        image = tile.getImage();
                    }
                    g.drawImage(image, x * step_x, y * step_y, this);
                }
            }
            
            for(String player : playerPosition.keySet()){
                final int player_x = playerPosition.get(player)[0];
                final int player_y = playerPosition.get(player)[1];
                
                g.drawString(player, player_x * step_x + 50, player_y * step_y + 50);
            }
            
            g.dispose();
        }
    }
    
    private class ChatPanel extends JPanel{

        JList messageList;
        ClientGUI parent;
        JButton sendButton;
        JTextField textField;
        
        public ChatPanel(ClientGUI p) {
            this.parent = p;
            MigLayout layout = new MigLayout("", "[][]", "[][]");
            setLayout(layout);
            
            messageList = new JList();
            messageList.setModel(new DefaultListModel());
            messageList.ensureIndexIsVisible(((DefaultListModel) messageList.getModel()).size() - 1);

            textField = new JTextField("");
            textField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sendButton.doClick();
                }
            });

            sendButton = new JButton("Send");
            sendButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = textField.getText();
                    textField.setText("");
                    parent.parent.getClient().sendChatMessage(text);
                }
            });  
            
            add(textField, "pushx, growx");
            add(sendButton, "wrap");
            add(new JScrollPane(messageList), "grow, pushy, spanx");
        }
    }

    void initPlayers(List<String> players) {
        playerHolders = new PlayerHolder[players.size()];
        for(int i=0; i<players.size(); i++){
            String player = players.get(i);
            PlayerHolder newPlayer = new PlayerHolder();
            newPlayer.setPlayerName(player);
            playerHolders[i] = newPlayer;
            playerHolderPanel.add(newPlayer);
        }
    }    
    
    public void killPlayer(KillPlayer killPlayer){
        String playerName = killPlayer.player;
        for(PlayerHolder p : playerHolders){
            if(p.getPlayerName().equals(playerName)){
                p.unselect();
                p.setDead(true);
                p.setEnabled(false);
            }
        }
    }

    void endGame(EndGame endGame) {
        endTurnButton.setEnabled(false);
        searchButton.setEnabled(false);
        LobbyGUI.enableComponents(mapPanel, false);        
    }
    
    class PlayerHolder extends JPanel{
        private String playerName = "";
        private int gold = 0;
        private int hp = 0;
        private boolean dead = false;
        
        JLabel nameLabel;
        JLabel goldLabel;
        JLabel hpLabel;
        JLabel deadLabel;
        
        public PlayerHolder(){
            nameLabel = new JLabel();
            hpLabel = new JLabel();
            goldLabel = new JLabel();
            deadLabel = new JLabel("DEAD");
            deadLabel.setForeground(Color.RED);
            deadLabel.setVisible(false);
            
            hpLabel.setText(new Integer(hp).toString());
            goldLabel.setText(new Integer(gold).toString());
            nameLabel.setText(playerName);
            
            this.setLayout(new MigLayout("", "[][]"));
            add(new JLabel("Name: "));
            add(nameLabel, "wrap");
            add(deadLabel, "wrap");
            add(new JLabel("HP: "));
            add(hpLabel, "wrap");
            add(new JLabel("Gold: "));
            add(goldLabel, "wrap");
            
            unselect();
        }

        public final void unselect(){
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(192, 192, 192)));
        }

        public final void select(){
            setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.red));
        }
        
        /**
         * @return the name
         */
        public String getPlayerName() {
            return playerName;
        }

        /**
         * @return the gold
         */
        public int getGold() {
            return gold;
        }
        
        /**
         * @return the hp
         */
        public int getHp() {
            return hp;
        }

        /**
         * @param name the name to set
         */
        public void setPlayerName(String name) {
            this.playerName = name;
            nameLabel.setText(name);
        }

        /**
         * @param gold the gold to set
         */
        public void setGold(int gold) {
            this.gold = gold;
            goldLabel.setText(new Integer(gold).toString());
        }


        /**
         * @param hp the hp to set
         */
        public void setHp(int hp) {
            this.hp = hp;
            hpLabel.setText(new Integer(hp).toString());
        }

        public void setDead(boolean b) {
            dead = b;
            if(b){
                deadLabel.setVisible(true);
            }else{
                deadLabel.setVisible(false);
            }
            invalidate();
        }
        public boolean getDead() {
            return dead;
        }        
    }
}
