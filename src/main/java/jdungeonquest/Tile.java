package jdungeonquest;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import jdungeonquest.enums.EntryDirection;
import jdungeonquest.enums.RoomWallType;

public class Tile {
    private BufferedImage image;
    private EntryDirection entryDirection;
    private List<RoomWallType> walls;
    private boolean isSearchable;
    int rotate;
    private static BufferedImage doorIcon;
    
    {
        try {
            doorIcon = ImageIO.read(getClass().getResourceAsStream("/doorIcon.png"));
        } catch (IOException ex) {
            Logger.getLogger(Tile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public Tile(){
        isSearchable = true;
        entryDirection = EntryDirection.UP;
        walls = new ArrayList<>( Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL} ) );
        rotate = 0;
        refreshImage();
    }
    
    public Tile(Tile another){
        this.isSearchable = another.isSearchable; //boolean
        this.entryDirection = another.entryDirection; //enum
        this.rotate = another.rotate; //int
        this.walls = new ArrayList(another.walls);
        this.refreshImage();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.entryDirection != null ? this.entryDirection.hashCode() : 0);
        hash = 19 * hash + Objects.hashCode(this.walls);
        hash = 19 * hash + (this.isSearchable ? 1 : 0);
        return hash;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tile other = (Tile)obj;
        if(!this.isSearchable == other.isSearchable){
            return false;
        }
            
        
        if(!this.getWalls().equals(other.getWalls())){
            return false;        
        }
        return true;
    }
    
    private static BufferedImage rotateImage(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        BufferedImage rot = new BufferedImage(h, w, BufferedImage.TYPE_INT_RGB);

        double theta = -Math.PI / 2;
        AffineTransform xform = AffineTransform.getRotateInstance(theta, w / 2, h / 2);
        Graphics2D g = (Graphics2D) rot.createGraphics();
        g.drawImage(img, xform, null);
        g.dispose();

        return rot;
    }
    
    /**
     * Rotate the tile counter-clockwise.
     * Rotates walls and entry point as following: U-L-D-R-U
     * @param turn 
     */
    public void rotate(int turn){
        this.rotate += turn;
        this.rotate = this.rotate % 4;
        
        rotateCounterClockwise(turn);
    }
    
    private void rotateCounterClockwise(int turn){
        if(turn == 0){
            return;
        }
        switch(this.entryDirection){
            case UP: entryDirection = EntryDirection.LEFT; break;
            case LEFT: entryDirection = EntryDirection.DOWN; break;
            case DOWN: entryDirection = EntryDirection.RIGHT; break;
            case RIGHT: entryDirection = EntryDirection.UP; break;
        }
        
        RoomWallType firstWall = walls.get(0);
        walls.remove(0);
        walls.add(firstWall);
        
        image = rotateImage(image);
        
        this.rotateCounterClockwise(turn -1 );
    }
    
    public int getRotate(){
        return rotate;
    }
    
    /**
     * @return the entryDirection
     */
    public EntryDirection getEntryDirection() {
        return entryDirection;
    }

    /**
     * @param entryDirection the entryDirection to set
     */
    public void setEntryDirection(EntryDirection entryDirection) {
        this.entryDirection = entryDirection;
    }

    /**
     * @return the walls
     */
    public List<RoomWallType> getWalls() {
        return walls;
    }

    /**
     * @param walls the walls to set
     */
    public void setWalls(List<RoomWallType> walls) {
        this.walls = walls;
    }

    /**
     * @return the isSearchable
     */
    public boolean isIsSearchable() {
        return isSearchable;
    }

    /**
     * @param isSearchable the isSearchable to set
     */
    public void setIsSearchable(boolean isSearchable) {
        this.isSearchable = isSearchable;
    }

    private void drawEntryTriangle(BufferedImage img){
        Graphics2D g = (Graphics2D) img.createGraphics();

        final int delta = 8;
        
        int nPoints = 3;
        int[] xPoints;
        int[] yPoints;
        
        int h1 = image.getHeight();
        int h2 = image.getHeight()/2;
        
        switch(entryDirection){
            default:
            case UP: xPoints = new int[]{h2-delta, h2+delta, h2}; yPoints = new int[]{0+delta, 0+delta, 0+delta*2}; break;
            case LEFT: xPoints = new int[]{0+delta, 0+delta, 0+delta*2}; yPoints = new int[]{h2-delta, h2+delta, h2}; break;
            case DOWN: xPoints = new int[]{h2-delta, h2+delta, h2}; yPoints = new int[]{h1-delta, h1-delta, h1-delta*2}; break;
            case RIGHT: xPoints = new int[]{h1-delta, h1-delta, h1-delta*2}; yPoints = new int[]{h2-delta, h2+delta, h2}; break;
        }
        g.setColor(Color.white);
        g.fillPolygon(xPoints, yPoints, nPoints);
        g.setColor(Color.black);
        g.setStroke( new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawPolygon(xPoints, yPoints, nPoints);
        g.dispose();        
    }    

    private void drawDoorIcons(BufferedImage img){
        Graphics2D g = (Graphics2D) img.createGraphics();
        if(walls.get(0) == RoomWallType.DOOR) drawDoor(g, EntryDirection.UP);
        if(walls.get(1) == RoomWallType.DOOR) drawDoor(g, EntryDirection.LEFT);
        if(walls.get(2) == RoomWallType.DOOR) drawDoor(g, EntryDirection.DOWN);
        if(walls.get(3) == RoomWallType.DOOR) drawDoor(g, EntryDirection.RIGHT);        
        g.dispose();
    }
    
    private void drawDoor(Graphics2D g, EntryDirection dir){
        int x;
        int y;
        switch(dir){
            case UP: x = 100-20; y = 5; break;
            case DOWN: x = 100-20; y = 200-45; break;
            case LEFT: x = 0; y = 100-20; break;
            case RIGHT: x = 200-40; y = 100-20; break;
            default: return;
        }
        g.drawImage(doorIcon, x, y, null);
    }
    
    private BufferedImage createBasicTileImage(){
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) img.createGraphics();
        
        g.setColor(Color.white);
        g.fillRect(0, 0, 200, 200);
        g.setColor(Color.black);
        g.setStroke( new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        //UP
        if(walls.get(0) != RoomWallType.WALL){
            g.drawLine(100, 100, 100, 0);
        }
        //LEFT
        if(walls.get(1) != RoomWallType.WALL){
            g.drawLine(0, 100, 100, 100);
        }
        //DOWN
        if(walls.get(2) != RoomWallType.WALL){
            g.drawLine(100, 100, 100, 200);
        }
        //RIGHT
        if(walls.get(3) != RoomWallType.WALL){
            g.drawLine(100, 100, 200, 100);
        }
        
        return img;
    }    
    
    private void refreshImage() {
        image = createBasicTileImage();
        drawDoorIcons(image);
        drawEntryTriangle(image);
    }

    /**
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }
}
