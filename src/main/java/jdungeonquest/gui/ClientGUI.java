package jdungeonquest.gui;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ClientGUI extends JPanel{
    GUI parent;
    
    ClientGUI(GUI parent){
        this.parent = parent;
        
        initGUI();
    }

    private void initGUI() {
        JLabel test = new JLabel("clientGUI test");
        add(test);
    }
}
