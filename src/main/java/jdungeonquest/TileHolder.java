package jdungeonquest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.yaml.snakeyaml.Yaml;

public class TileHolder {

    List<Tile> allTiles = new ArrayList<>();
    List<Tile> usedTiles = new ArrayList<>();
    Map<Integer, Tile> tileMap = new HashMap<Integer, Tile>();

    Tile startingTile;
    
    final static String startingTileYaml =
            "!!jdungeonquest.Tile\n"
            + "entryDirection: RIGHT\n"
            + "imagePath: /tiles/start.png\n"
            + "isSearchable: false\n"
            + "walls: [WALL, WALL, WALL, WALL]\n";
    
    public TileHolder(){
        initializeTiles();
    }

    public Tile takeTile() {
        Tile t = allTiles.get(0);
        usedTiles.add(t);
        allTiles.remove(0);
        return t;
    }

    public void shuffle() {
        for (Tile tile : usedTiles) {
            allTiles.add(tile);
        }
        usedTiles.clear();
        Collections.shuffle(allTiles);
    }

    public Tile takeSpecificTile(int a){
        return tileMap.get(a);
    }
    
    private void initializeTiles() {
        Yaml yaml = new Yaml();
        allTiles = (ArrayList<Tile>) yaml.load(TileHolder.class.getResourceAsStream("/Tiles.yaml"));

        startingTile = (Tile) yaml.load(startingTileYaml);
        tileMap.put(-1, startingTile);
        
        int currentTile = 0;
//        for (Tile tile : allTiles) {
//            tileMap.put(currentTile, tile);
//            currentTile++;
//        }
    }

    int getTileNumber(Tile tile) {
        for (Entry<Integer, Tile> entry : tileMap.entrySet()) {
            if (tile.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return -1;
    }
}
