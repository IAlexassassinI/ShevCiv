package Processing.Utilits;

import Processing.TileMap.Tile;
import Processing.TileMap.TileUtils.Resource;
import Processing.TileMap.TileUtils.TypeOfFlora;
import Processing.TileMap.TileUtils.TypeOfLand;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class WhereCanBe implements Serializable {
    static final long serialVersionUID = 14L;

    final static public int TYPE_OF_FLORA_NUM = 0;
    final static public int TYPE_OF_LAND_NUM = 1;
    final static public int TYPE_OF_RESOURCE_NUM = 2;

    public WhereCanBe(String nameOfWhereCanSpawn, TypeOfLand land[], TypeOfFlora flora[], Resource resource[], TypeOfLand noLand[], TypeOfFlora noFlora[], Resource noResource[]){
        this.nameOfWhereCanSpawn = nameOfWhereCanSpawn;


        for(int i = 0; i < land.length; i++){
            this.typesOfLandWhereCanSpawn.put(land[i].elementName, land[i]);
        }
        for(int i = 0; i < flora.length; i++){
            this.typesOfFloraWhereCanSpawn.put(flora[i].elementName, flora[i]);
        }
        for(int i = 0; i < resource.length; i++){
            this.typesOfResourceWhereCanSpawn.put(resource[i].elementName, resource[i]);
        }


        for(int i = 0; i < noLand.length; i++){
            this.typesOfLandWhereDontSpawn.put(noLand[i].elementName, noLand[i]);
        }
        for(int i = 0; i < noFlora.length; i++){
            this.typesOfFloraWhereDontSpawn.put(noFlora[i].elementName, noFlora[i]);
        }
        for(int i = 0; i < noResource.length; i++){
            this.typesOfResourceWhereDontSpawn.put(noResource[i].elementName, noResource[i]);
        }




        AllWhereCanSpawn.put(this.nameOfWhereCanSpawn, this);
    }
    public static LinkedHashMap<String, WhereCanBe> AllWhereCanSpawn = new LinkedHashMap<>();

    //if null then don`t count
    public static final WhereCanBe noPreference = new WhereCanBe(
            "noPreference",
            new TypeOfLand[]{},
            new TypeOfFlora[]{},
            new Resource[]{},
            new TypeOfLand[]{},
            new TypeOfFlora[]{},
            new Resource[]{});
    public static final WhereCanBe noMountain = new WhereCanBe(
            "noMountain",
            new TypeOfLand[]{},
            new TypeOfFlora[]{},
            new Resource[]{},
            new TypeOfLand[]{TypeOfLand.Mountains},
            new TypeOfFlora[]{},
            new Resource[]{});
    public static final WhereCanBe onForest = new WhereCanBe(
            "onForest",
            new TypeOfLand[]{},
            new TypeOfFlora[]{TypeOfFlora.Forest},
            new Resource[]{},
            new TypeOfLand[]{TypeOfLand.Mountains, TypeOfLand.Shores, TypeOfLand.DeepOcean},
            new TypeOfFlora[]{},
            new Resource[]{});
    public static final WhereCanBe onLandNoMountain = new WhereCanBe(
            "onLandNoMountain",
            new TypeOfLand[]{},
            new TypeOfFlora[]{},
            new Resource[]{},
            new TypeOfLand[]{TypeOfLand.Mountains, TypeOfLand.Shores, TypeOfLand.DeepOcean},
            new TypeOfFlora[]{},
            new Resource[]{});

    public String nameOfWhereCanSpawn;

    public HashMap<String,TypeOfLand> typesOfLandWhereCanSpawn = new HashMap<>();
    public HashMap<String,TypeOfFlora> typesOfFloraWhereCanSpawn = new HashMap<>();
    public HashMap<String,Resource> typesOfResourceWhereCanSpawn = new HashMap<>();

    public HashMap<String,TypeOfLand> typesOfLandWhereDontSpawn = new HashMap<>();
    public HashMap<String,TypeOfFlora> typesOfFloraWhereDontSpawn = new HashMap<>();
    public HashMap<String,Resource> typesOfResourceWhereDontSpawn = new HashMap<>();





    static public boolean PositiveCheck_JavaPorevo(Tile ProcessedTile, int LayerType, WhereCanBe TMP_WhereCanSpawn){
        switch(LayerType){
            case TYPE_OF_FLORA_NUM:
                //TypeOfFlora
                if(TMP_WhereCanSpawn.typesOfFloraWhereCanSpawn.containsKey(ProcessedTile.typeOfFlora.elementName) || TMP_WhereCanSpawn.typesOfFloraWhereCanSpawn.containsKey(TypeOfFlora.none.elementName)){
                    return false;
                }
                break;
            case TYPE_OF_LAND_NUM:
                //TypeOfLand
                if(TMP_WhereCanSpawn.typesOfLandWhereCanSpawn.containsKey(ProcessedTile.typeOfLand.elementName) || TMP_WhereCanSpawn.typesOfLandWhereCanSpawn.containsKey(TypeOfLand.Void.elementName)){
                    return false;
                }
                break;
            case TYPE_OF_RESOURCE_NUM:
                //TypeOfLand
                if(TMP_WhereCanSpawn.typesOfResourceWhereCanSpawn.containsKey(ProcessedTile.resource.elementName) || TMP_WhereCanSpawn.typesOfResourceWhereCanSpawn.containsKey(Resource.none.elementName)){
                    return false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    static public boolean NegativeCheck_JavaPorevo(Tile ProcessedTile, int LayerType, WhereCanBe TMP_WhereCanSpawn){
        switch(LayerType){
            case TYPE_OF_FLORA_NUM:
                //TypeOfFlora
                if(TMP_WhereCanSpawn.typesOfFloraWhereDontSpawn.containsKey(ProcessedTile.typeOfFlora.elementName) || TMP_WhereCanSpawn.typesOfFloraWhereDontSpawn.containsKey(TypeOfFlora.none.elementName)){
                    return true;
                }
                break;
            case TYPE_OF_LAND_NUM:
                //TypeOfLand
                if(TMP_WhereCanSpawn.typesOfLandWhereDontSpawn.containsKey(ProcessedTile.typeOfLand.elementName) || TMP_WhereCanSpawn.typesOfLandWhereDontSpawn.containsKey(TypeOfLand.Void.elementName)){
                    return true;
                }
                break;
            case TYPE_OF_RESOURCE_NUM:
                //TypeOfLand
                if(TMP_WhereCanSpawn.typesOfResourceWhereDontSpawn.containsKey(ProcessedTile.resource.elementName) || TMP_WhereCanSpawn.typesOfResourceWhereDontSpawn.containsKey(Resource.none.elementName)){
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }


}
