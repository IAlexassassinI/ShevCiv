package MainPackage;

import static FileHandler.SaveLoadInterface.*;
import TileMap.GameMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        System.out.println("Creating Map");
        GameMap GM = new GameMap(10, 10);
        System.out.println("Created Map");
        System.out.println("Serializing Map");
        serializeObjectToFile(GM, "Maps\\Map1.map");
        System.out.println("Serialized Map");
        System.out.println("DeSerializing Map");
        GameMap GMD = (GameMap)deserializeFromFile("Maps\\Map1.map");
        System.out.println("DeSerialized Map");
    }
}
