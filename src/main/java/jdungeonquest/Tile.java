package jdungeonquest;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import jdungeonquest.enums.EntryDirection;
import jdungeonquest.enums.RoomWallType;
import jdungeonquest.enums.RoomType;

public class Tile {
//    RoomType roomType;
    private String imagePath; //format: "/tiles/01.png";
    private BufferedImage image;
    private EntryDirection entryDirection;
    private List<RoomWallType> walls;
    private boolean isSearchable;

    public Tile(){
        imagePath = "/tiles/empty.png";
        refreshImage();
        isSearchable = true;
        entryDirection = EntryDirection.UP;
        walls = new ArrayList<>( Arrays.asList( new RoomWallType[]{RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL,RoomWallType.WALL} ) );
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
        if(!this.getWalls().equals(other.getWalls())){
            return false;        
        }
        return true;
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
