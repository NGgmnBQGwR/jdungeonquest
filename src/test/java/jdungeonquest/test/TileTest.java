
package jdungeonquest.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jdungeonquest.GameMap;
import jdungeonquest.Position;
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
    
    @Test
    public void MapIsEmpty(){
        GameMap map = new GameMap();
        assertEquals(true, map.isFree(0, 0));
        Tile t = new Tile();
        map.setTile(0, 0, t);
        assertEquals(false, map.isFree(0, 0));
    }
    
    @Test
    public void MapIsAdjacent(){
        GameMap map = new GameMap();
        assertEquals(true, map.isAdjacent(new Position(0,0), new Position(0,1)));
        assertEquals(true, map.isAdjacent(new Position(2,5), new Position(2,6)));
        assertEquals(true, map.isAdjacent(new Position(2,4), new Position(3,4)));
        assertEquals(true, map.isAdjacent(new Position(map.MAX_X-1,map.MAX_Y-1), new Position(map.MAX_X-1,map.MAX_Y-2)));
        
        assertEquals(false, map.isAdjacent(new Position(0,11), new Position(10,0)));
        assertEquals(false, map.isAdjacent(new Position(3,5), new Position(5,5)));
        assertEquals(false, map.isAdjacent(new Position(3,5), new Position(4,6)));
        assertEquals(false, map.isAdjacent(new Position(0,0), new Position(0,0)));
        assertEquals(false, map.isAdjacent(new Position(5,6), new Position(6,5)));
        assertEquals(false, map.isAdjacent(new Position(map.MAX_X-1,map.MAX_Y-1), new Position(map.MAX_X-1,map.MAX_Y-1)));
    }
    
    @Test
    public void Map(){
        GameMap map = new GameMap();
        
        Tile crossroads = new Tile();
        Tile noway = new Tile();
        Tile corridor = new Tile();
        
        crossroads.setWalls( Arrays.asList( new RoomWallType[]{RoomWallType.EXIT,RoomWallType.EXIT,RoomWallType.EXIT,RoomWallType.EXIT} ) );
        noway.setWalls( Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL} ) );
        corridor.setWalls( Arrays.asList( new RoomWallType[]{RoomWallType.EXIT,RoomWallType.WALL,RoomWallType.EXIT,RoomWallType.WALL} ) );
        
        map.setTile(1, 1, crossroads);
        map.setTile(3, 3, noway);
        map.setTile(5, 5, corridor);

        assertEquals(true, map.canMoveTo(new Position(1,1), new Position(0,1)));
        assertEquals(true, map.canMoveTo(new Position(1,1), new Position(1,0)));
        assertEquals(true, map.canMoveTo(new Position(1,1), new Position(1,2)));
        assertEquals(true, map.canMoveTo(new Position(1,1), new Position(2,1)));
        
        assertEquals(false, map.canMoveTo(new Position(3,3), new Position(2,3)));
        assertEquals(false, map.canMoveTo(new Position(3,3), new Position(3,2)));
        assertEquals(false, map.canMoveTo(new Position(3,3), new Position(3,4)));
        assertEquals(false, map.canMoveTo(new Position(3,3), new Position(4,3)));

        assertEquals(true, map.canMoveTo(new Position(5,5), new Position(5,4)));
        assertEquals(true, map.canMoveTo(new Position(5,5), new Position(5,6)));
        assertEquals(false, map.canMoveTo(new Position(5,5), new Position(4,5)));
        assertEquals(false, map.canMoveTo(new Position(5,5), new Position(6,5)));
    }
}
