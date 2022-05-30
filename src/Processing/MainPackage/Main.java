package Processing.MainPackage;

import static Processing.FileHandler.SaveLoadInterface.*;
import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import Processing.TileMap.TileUtils.Height;
import Processing.TileMap.TileUtils.Resource;
import Processing.Utilits.Point;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //15
        System.out.println("Creating Map");
        GameMap GM = new GameMap(10, 10);
        System.out.println("Created Map");
        System.out.println("Serializing Map");
        serializeObjectToFile(GM, "Maps\\Map1.map");
        System.out.println("Serialized Map");
        System.out.println("DeSerializing Map");
        GameMap GMD = (GameMap)deserializeFromFile("Maps\\Map1.map");
        System.out.println("DeSerialized Map");
        System.out.println("Getting Tile");
        Tile TMP_Tile = GMD.getTile(new Point(-1,-5));
        System.out.println("Got Tile");

    }
}
