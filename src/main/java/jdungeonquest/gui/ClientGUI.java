package jdungeonquest.gui;

import java.awt.Color;
import java.awt.Dimension;
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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import jdungeonquest.GameMap;
import jdungeonquest.Tile;
import jdungeonquest.TileHolder;
import net.miginfocom.swing.MigLayout;

public class ClientGUI extends JPanel{
    GUI parent;
    
    ChatPanel chatPanel;
//    SunPanel sunPanel;
//    PlayerHolderPanel playerHolderPanel;
    MapPanel mapPanel;
    GameMap map;
    TileHolder tileHolder;
    Map<String, int[]> playerPosition;
    JButton endTurnButton;
    public static BufferedImage blankTileImage;

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
        MigLayout layout = new MigLayout("fill", "[grow][grow]", "[grow][grow]");
        this.setLayout(layout);
        
        endTurnButton = new JButton("End turn");
        endTurnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parent.getClient().endTurn();
                }
            });
        
        chatPanel = new ChatPanel(this);
        mapPanel = new MapPanel(map);
        mapPanel.setPreferredSize(new Dimension(2000, 2600));
        mapPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                processMouseClick(evt.getPoint().x, evt.getPoint().y);
            }
        });        
        
        add(new JScrollPane(mapPanel), "w 200:600:1000, h 200:600:1000, grow 60");
        add(chatPanel);
        add(endTurnButton);
    }

    void processMouseClick(int x, int y){
        int tx = x / 200;
        int ty = y / 200;
        parent.getClient().moveTo(tx, ty);
    }
    
    void addChatMessage(String msg, String author) {
        String time = new SimpleDateFormat("[HH:mm:ss] ").format(new Date());
        String text = time + author + ":" + msg;
        ((DefaultListModel) chatPanel.messageList.getModel()).add(((DefaultListModel) chatPanel.messageList.getModel()).getSize(), text);
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
