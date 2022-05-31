package Processing.TileMap.TileUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class WhereCanSpawn implements Serializable {
    static final long serialVersionUID = 14L;

    public WhereCanSpawn(String nameOfWhereCanSpawn, Height height[], TypeOfLand land[], TypeOfFlora flora[], Height noHeight[], TypeOfLand noLand[], TypeOfFlora noFlora[]){
        this.nameOfWhereCanSpawn = nameOfWhereCanSpawn;

        for(int i = 0; i < height.length; i++){
            this.typesOfHeightWhereCanSpawn.put(height[i].elementName, height[i]);
        }
        for(int i = 0; i < land.length; i++){
            this.typesOfLandWhereCanSpawn.put(land[i].elementName, land[i]);
        }
        for(int i = 0; i < flora.length; i++){
            this.typesOfFloraWhereCanSpawn.put(flora[i].elementName, flora[i]);
        }

        for(int i = 0; i < noHeight.length; i++){
            this.typesOfHeightWhereDontSpawn.put(noHeight[i].elementName, noHeight[i]);
        }
        for(int i = 0; i < noLand.length; i++){
            this.typesOfLandWhereDontSpawn.put(noLand[i].elementName, noLand[i]);
        }
        for(int i = 0; i < noFlora.length; i++){
            this.typesOfFloraWhereDontSpawn.put(noFlora[i].elementName, noFlora[i]);
        }


        AllWhereCanSpawn.put(this.nameOfWhereCanSpawn, this);
    }
    public static LinkedHashMap<String, WhereCanSpawn> AllWhereCanSpawn = new LinkedHashMap<>();

    //if null then don`t count
    public static final WhereCanSpawn noPreference = new WhereCanSpawn(
            "noPreference",
            null,
            null,
            null,
            null,
            null,
            null);
    public static final WhereCanSpawn noMountain = new WhereCanSpawn(
            "noMountain",
            null,
            null,
            null,
            new Height[]{Height.Mountains},
            null,
            null);
    public static final WhereCanSpawn onForest = new WhereCanSpawn(
            "onForest",
            null,
            null,
            null,
            new Height[]{Height.Mountains, Height.Shores, Height.DeepOcean},
            null,
            new TypeOfFlora[]{TypeOfFlora.Forest});
    public static final WhereCanSpawn onLandNoMountain = new WhereCanSpawn(
            "onLandNoMountain",
            null,
            null,
            null,
            new Height[]{Height.Shores, Height.DeepOcean, Height.Mountains},
            null,
            null
    );

    public String nameOfWhereCanSpawn;

    //Maybe change to Hashmap
    public HashMap<String,Height> typesOfHeightWhereCanSpawn;
    public HashMap<String,TypeOfLand> typesOfLandWhereCanSpawn;
    public HashMap<String,TypeOfFlora> typesOfFloraWhereCanSpawn;

    public HashMap<String,Height> typesOfHeightWhereDontSpawn;
    public HashMap<String,TypeOfLand> typesOfLandWhereDontSpawn;
    public HashMap<String,TypeOfFlora> typesOfFloraWhereDontSpawn;

}
