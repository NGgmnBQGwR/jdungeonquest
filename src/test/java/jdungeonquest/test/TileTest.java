
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


    public void TileRotate2and2(){
        Tile t1 = new Tile();
        t1.setEntryDirection(EntryDirection.UP);
        t1.setImagePath("/tiles/empty.png");
        t1.setIsSearchable(true);
        List<RoomWallType> t1Walls = new ArrayList(Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL,RoomWallType.EXIT} ) );
        t1.setWalls(t1Walls );
        
        t1.rotate(2);
        
        assertEquals(EntryDirection.DOWN, t1.getEntryDirection());
        assertEquals( Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.EXIT,RoomWallType.WALL,RoomWallType.WALL} ), t1.getWalls());

        t1.rotate(2);
        
        assertEquals(EntryDirection.UP, t1.getEntryDirection());
        assertEquals( t1Walls, t1.getWalls());
        assertEquals(0, t1.getRotate());
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
    public void MapCanMoveTo(){
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

    @Test
    public void MapPlaceTile(){
        GameMap map = new GameMap();
        
        Tile crossroads = new Tile();
        Tile lTurn = new Tile();
        Tile corridor = new Tile();
        Tile deadEnd = new Tile();
        
        crossroads.setEntryDirection(EntryDirection.UP);
        lTurn.setEntryDirection(EntryDirection.RIGHT);
        corridor.setEntryDirection(EntryDirection.DOWN);
        deadEnd.setEntryDirection(EntryDirection.LEFT);
        
        crossroads.setWalls( new ArrayList(Arrays.asList( new RoomWallType[]{RoomWallType.EXIT,RoomWallType.EXIT,RoomWallType.EXIT,RoomWallType.EXIT} ) ));
        lTurn.setWalls( new ArrayList(Arrays.asList( new RoomWallType[]{RoomWallType.EXIT,RoomWallType.WALL,RoomWallType.WALL,RoomWallType.EXIT} ) ));
        corridor.setWalls( new ArrayList(Arrays.asList( new RoomWallType[]{RoomWallType.EXIT,RoomWallType.WALL,RoomWallType.EXIT,RoomWallType.WALL} ) ));
        deadEnd.setWalls( new ArrayList(Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.EXIT,RoomWallType.WALL,RoomWallType.WALL} ) ));

        map.setTile(3, 3, crossroads);
        
        assertEquals(0, map.placeTile( new Position(3,3), new Position(2,3), lTurn));
        assertEquals(3, map.placeTile( new Position(3,3), new Position(4,3), corridor));
        assertEquals(0, map.placeTile( new Position(4,3), new Position(5,3), deadEnd));
    }
}
