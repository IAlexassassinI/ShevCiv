package Processing.MainPackage;

import static Processing.FileHandler.SaveLoadInterface.*;

import Processing.Player.Player;
import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import Processing.Units.Ability.ConstructSomethingOnTile;
import Processing.Units.Ability.SpecialAbility;
import Processing.Utilits.Point;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //6
        //15
        //LinkedHashMap or HashMap
        /*
        double A = 1/0D;
        System.out.println("Creating Map");
        GameMap GM = new GameMap(10, 10);
        GM.generateRandomResource(50,50);
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
        Player Alex = new Player(GMD);
        //Object ARR = WhereCanSpawn.AllWhereCanSpawn.get(12);
        System.out.println("Got Tile");

         */
        //TODO add Tech
        //TODO proceed special resources
        //TODO maybe make true/false list for buying units and buildings
        //TODO paralel add types of elements
    }
}
