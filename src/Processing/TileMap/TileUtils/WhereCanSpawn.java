package Processing.TileMap.TileUtils;

import java.io.Serializable;
import java.util.HashMap;

public class WhereCanSpawn implements Serializable {
    static final long serialVersionUID = 14L;

    public WhereCanSpawn(String nameOfWhereCanSpawn, String height[], String land[], String flora[], String noHeight[], String noLand[], String noFlora[]){
        typesOfHeightWhereCanSpawn = height;
        typesOfLandWhereCanSpawn = land;
        typesOfFloraWhereCanSpawn = flora;

        typesOfHeightWhereDontSpawn = noHeight;
        typesOfLandWhereDontSpawn = noLand;
        typesOfFloraWhereDontSpawn = noFlora;
        this.nameOfWhereCanSpawn = nameOfWhereCanSpawn;
        AllWhereCanSpawn.put(this.nameOfWhereCanSpawn, this);
    }
    public static HashMap<String, WhereCanSpawn> AllWhereCanSpawn = new HashMap<>();

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
            new String[]{Height.Mountains.elementName},
            null,
            null);
    public static final WhereCanSpawn onForest = new WhereCanSpawn(
            "onForest",
            null,
            null,
            null,
            new String[]{Height.Mountains.elementName, Height.Shores.elementName, Height.DeepOcean.elementName},
            null,
            new String[]{TypeOfFlora.Forest.elementName});
    public static final WhereCanSpawn onLandNoMountain = new WhereCanSpawn(
            "onLandNoMountain",
            null,
            null,
            null,
            new String[]{Height.Shores.elementName, Height.DeepOcean.elementName, Height.Mountains.elementName},
            null,
            null
    );

    public String nameOfWhereCanSpawn;

    public String typesOfHeightWhereCanSpawn[];
    public String typesOfLandWhereCanSpawn[];
    public String typesOfFloraWhereCanSpawn[];

    public String typesOfHeightWhereDontSpawn[];
    public String typesOfLandWhereDontSpawn[];
    public String typesOfFloraWhereDontSpawn[];

}
