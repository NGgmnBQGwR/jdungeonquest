
package jdungeonquest.test;

import java.util.ArrayList;
import java.util.Arrays;
import jdungeonquest.Tile;
import jdungeonquest.TileHolder;
import jdungeonquest.enums.EntryDirection;
import jdungeonquest.enums.RoomWallType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TileTest {
    
    @Test
    public void TileHolderInitializedSuccessfully(){
        TileHolder holder = new TileHolder();

        assertTrue(holder.getTotalSize() > 0);
        assertTrue(holder.getSize() > 0);
    }    
    
    @Test
    public void TileHolderShuffle(){
        TileHolder tileHolder = new TileHolder();
        tileHolder.shuffle();
    }

    @Test
    public void TileRotate0(){
        Tile t1 = new Tile();
        t1.setEntryDirection(EntryDirection.UP);
        t1.setImagePath("/tiles/empty.png");
        t1.setIsSearchable(true);
        t1.setWalls( new ArrayList(Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL,RoomWallType.EXIT} ) ));
        
        t1.rotate(0);
        
        assertEquals(EntryDirection.UP, t1.getEntryDirection());
        assertEquals( Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL,RoomWallType.EXIT} ) , t1.getWalls());
    }
    
    @Test
    public void TileRotate1(){
        Tile t1 = new Tile();
        t1.setEntryDirection(EntryDirection.UP);
        t1.setImagePath("/tiles/empty.png");
        t1.setIsSearchable(true);
        t1.setWalls( new ArrayList(Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL,RoomWallType.EXIT} ) ));
        
        t1.rotate(1);
        
        assertEquals(EntryDirection.LEFT, t1.getEntryDirection());
        assertEquals( Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.WALL,RoomWallType.EXIT,RoomWallType.WALL} ) , t1.getWalls());
    }

    @Test
    public void TileRotate2(){
        Tile t1 = new Tile();
        t1.setEntryDirection(EntryDirection.UP);
        t1.setImagePath("/tiles/empty.png");
        t1.setIsSearchable(true);
        t1.setWalls( new ArrayList(Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL,RoomWallType.EXIT} ) ));
        
        t1.rotate(2);
        
        assertEquals(EntryDirection.DOWN, t1.getEntryDirection());
        assertEquals( Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.EXIT,RoomWallType.WALL,RoomWallType.WALL} ) , t1.getWalls());
    }    
    
}
