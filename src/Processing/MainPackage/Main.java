package Processing.MainPackage;

import static Processing.FileHandler.SaveLoadInterface.*;

import Processing.FileHandler.SaveLoadInterface;
import Processing.Player.Player;
import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import Processing.TileMap.TileUtils.TypeOfFlora;
import Processing.Units.Ability.ConstructSomethingOnTile;
import Processing.Units.Ability.SpecialAbility;
import Processing.Utilits.Point;
import Processing.Utilits.WhereCanBe;

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        //6
        //15
        //LinkedHashMap or HashMap

        /*
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

         */

        GameMap GM = SaveLoadInterface.LoadGameMapFromFile("assets/saved_maps/map.txt");
        //WhereCanBe.initTypes();
        System.out.println(11);
        TypeOfFlora.Forest = GM.GU.AllTypeOfFlora.get(TypeOfFlora.Forest.elementName);
        WhereCanBe wcb = TypeOfFlora.Forest.whereCanExist;
        System.out.println(11);
        System.out.println(11);
        //TODO add Tech
        //TODO proceed special resources
        //TODO maybe make true/false list for buying units and buildings
        //TODO paralel add types of elements
    }
}
