package jdungeonquest;

public class GameMap {
    public final static int MAX_X = 10;
    public final static int MAX_Y = 13;

    private Tile[][] tiles =  new Tile[10][13];
    
    public GameMap(){
        
    }
   
    public void setTile(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public Tile[][] getTiles(){
        return tiles;
    }
    
    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    boolean isFree(int x, int y) {
        if (tiles[x][y] == null) {
            return true;
        }
        return false;
    }

    public boolean isAdjacent(Position p1, Position p2){
        return false;        
    }
    
    public boolean canMoveTo(Position p1, Position p2){
        return false;
    }

    /**
     * Placed tile in tilePos, while rotating it so that it faces playerPos.
     * @param position
     * @param to
     * @param tile
     * @return number of rotations needed to tile
     */
    int placeTile(Position playerPos, Position tilePos, Tile tile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
