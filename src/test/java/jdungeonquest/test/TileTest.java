
package jdungeonquest.test;

import jdungeonquest.TileHolder;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TileTest {
    
    @Test
    public void TileHolderLoading(){
        TileHolder tileHolder = new TileHolder();
        tileHolder.shuffle();
    }
    
}
