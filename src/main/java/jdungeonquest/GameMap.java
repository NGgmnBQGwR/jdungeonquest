package jdungeonquest;

import jdungeonquest.enums.RoomWallType;

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
    public boolean isFree(int x, int y) {
        if (tiles[x][y] == null) {
            return true;
        }
        return false;
    }

    public boolean isAdjacent(Position p1, Position p2){
        if(p1 == p2){
            return false;
        }
        if( (p1.getX() == p2.getX()) && (Math.abs( p1.getY() - p2.getY()) == 1)){
            return true;
        }
        if( (p1.getY() == p2.getY()) && (Math.abs( p1.getX() - p2.getX()) == 1)){
            return true;
        }
        return false;
    }
    
    public boolean canMoveTo(Position p1, Position p2){
        //U[0] L[1] D[2] R[3]
        if( p1.getY() == p2.getY()){ //same line
            if(p1.getX() > p2.getX()){ //going right
                if(getTile(p1.getX(), p1.getY()).getWalls().get(3) != RoomWallType.WALL){
                    return true;
                }
            }
            if(p1.getX() < p2.getX()){ //going left
                if(getTile(p1.getX(), p1.getY()).getWalls().get(1) != RoomWallType.WALL){
                    return true;
                }
            }
            return false;
        }
        if( p1.getX() == p2.getX()){ //same column
            if(p1.getY() > p2.getY()){ //going down
                if(getTile(p1.getX(), p1.getY()).getWalls().get(2) != RoomWallType.WALL){
                    return true;
                }
            }
            if(p1.getY() < p2.getY()){ //going up
                if(getTile(p1.getX(), p1.getY()).getWalls().get(0) != RoomWallType.WALL){
                    return true;
                }
            }
            return false;
        }        
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
