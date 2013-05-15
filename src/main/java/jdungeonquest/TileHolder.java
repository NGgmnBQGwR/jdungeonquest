package jdungeonquest;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import org.yaml.snakeyaml.Yaml;

public class TileHolder {

    List<Tile> allTiles = new ArrayList<>();
    List<Tile> usedTiles = new ArrayList<>();
    Map<Integer, Tile> tileMap = new HashMap<>();

    Tile startingTile;
    Tile dragonTileLeft;
    Tile dragonTileRight;
    
    final static String startingTileYaml =
            "!!jdungeonquest.Tile\n"
            + "entryDirection: RIGHT\n"
            + "isSearchable: false\n"
            + "walls: [EXIT, EXIT, EXIT, EXIT]\n";
    
    final static String dragonTileLeftYaml =
            "!!jdungeonquest.Tile\n"
            + "entryDirection: RIGHT\n"
            + "isSearchable: false\n"
            + "walls: [EXIT, EXIT, EXIT, EXIT]\n";    

    final static String dragonTileRightYaml =
            "!!jdungeonquest.Tile\n"
            + "entryDirection: LEFT\n"
            + "isSearchable: false\n"
            + "walls: [EXIT, EXIT, EXIT, EXIT]\n";    
    
    public TileHolder(){
        initializeTiles();
    }

    public Tile takeTile() {
        if(allTiles.isEmpty()){
            shuffle();
        }
        Tile t = allTiles.get(0);
        usedTiles.add(t);
        allTiles.remove(0);
        return new Tile(t);
    }

    public void shuffle() {
        for (Tile tile : usedTiles) {
            allTiles.add(tile);
        }
        usedTiles.clear();
        Collections.shuffle(allTiles);
    }

    public Tile takeSpecificTile(int a){
        return new Tile(tileMap.get(a));
    }
    
    private void initializeTiles() {
        Yaml yaml = new Yaml();
        allTiles = (ArrayList<Tile>) yaml.load(TileHolder.class.getResourceAsStream("/Tiles.yaml"));

        startingTile = (Tile) yaml.load(startingTileYaml);
        dragonTileLeft = (Tile) yaml.load(dragonTileLeftYaml);
        dragonTileRight = (Tile) yaml.load(dragonTileRightYaml);
        
        int currentTile = 0;
        for (Tile tile : allTiles) {
            tileMap.put(currentTile, tile);
            currentTile++;
        }
        tileMap.put(currentTile++, startingTile);
        tileMap.put(currentTile++, dragonTileLeft);
        tileMap.put(currentTile++, dragonTileRight);
    }

    int getTileNumber(Tile tile) {
        for (Entry<Integer, Tile> entry : tileMap.entrySet()) {
            if (tile.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException();
    }

    public int getSize() {
        return allTiles.size();
    }

    public int getTotalSize() {
        return allTiles.size() + usedTiles.size();
    }

}
