package jdungeonquest;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import jdungeonquest.enums.EntryDirection;
import jdungeonquest.enums.RoomWallType;

public class Tile {
//    RoomType roomType;
    private String imagePath; //format: "/tiles/01.png";
    private BufferedImage image;
    private EntryDirection entryDirection;
    private List<RoomWallType> walls;
    private boolean isSearchable;
    int rotate;

    public Tile(){
        imagePath = "/tiles/empty.png";
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
        this.imagePath = new String(another.imagePath);
        this.walls = new ArrayList(another.walls);
        this.refreshImage();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.imagePath);
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
        if(!this.imagePath.equals(other.getImagePath())){
            return false;
        }
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
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
        refreshImage();
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

    private void refreshImage() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return the image
     */
    public BufferedImage getImage() {
        return image;
    }
}
