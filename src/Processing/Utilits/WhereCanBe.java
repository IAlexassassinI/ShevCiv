package Processing.Utilits;

import Processing.TileMap.Tile;
import Processing.TileMap.TileUtils.Resource;
import Processing.TileMap.TileUtils.TypeOfFlora;
import Processing.TileMap.TileUtils.TypeOfLand;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class WhereCanBe implements Serializable {
    static final long serialVersionUID = 33L;

    final static public int TYPE_OF_FLORA_NUM = 0;
    final static public int TYPE_OF_LAND_NUM = 1;
    final static public int TYPE_OF_RESOURCE_NUM = 2;

    public WhereCanBe(String nameOfWhereCanBe, TypeOfLand land[], TypeOfFlora flora[], Resource resource[], TypeOfLand noLand[], TypeOfFlora noFlora[], Resource noResource[]){
        this.nameOfWhereCanBe = nameOfWhereCanBe;


        for(int i = 0; i < land.length; i++){
            this.typesOfLandWhereCanBe.put(land[i].elementName, land[i]);
        }
        for(int i = 0; i < flora.length; i++){
            this.typesOfFloraWhereCanBe.put(flora[i].elementName, flora[i]);
        }
        for(int i = 0; i < resource.length; i++){
            this.typesOfResourceWhereCanBe.put(resource[i].elementName, resource[i]);
        }


        for(int i = 0; i < noLand.length; i++){
            this.typesOfLandWhereDontBe.put(noLand[i].elementName, noLand[i]);
        }
        for(int i = 0; i < noFlora.length; i++){
            this.typesOfFloraWhereDontBe.put(noFlora[i].elementName, noFlora[i]);
        }
        for(int i = 0; i < noResource.length; i++){
            this.typesOfResourceWhereDontBe.put(noResource[i].elementName, noResource[i]);
        }




        AllWhereCanSpawn.put(this.nameOfWhereCanBe, this);
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

    public String nameOfWhereCanBe;

    public HashMap<String,TypeOfLand> typesOfLandWhereCanBe = new HashMap<>();
    public HashMap<String,TypeOfFlora> typesOfFloraWhereCanBe = new HashMap<>();
    public HashMap<String,Resource> typesOfResourceWhereCanBe = new HashMap<>();

    public HashMap<String,TypeOfLand> typesOfLandWhereDontBe = new HashMap<>();
    public HashMap<String,TypeOfFlora> typesOfFloraWhereDontBe = new HashMap<>();
    public HashMap<String,Resource> typesOfResourceWhereDontBe = new HashMap<>();





    static public boolean PositiveCheck_Specific(Tile ProcessedTile, int LayerType, WhereCanBe whereCanBe){
        switch(LayerType){
            case TYPE_OF_FLORA_NUM:
                //TypeOfFlora
                if(whereCanBe.typesOfFloraWhereCanBe.containsKey(ProcessedTile.typeOfFlora.elementName) || whereCanBe.typesOfFloraWhereCanBe.containsKey(TypeOfFlora.none.elementName)){
                    return true;
                }
                break;
            case TYPE_OF_LAND_NUM:
                //TypeOfLand
                if(whereCanBe.typesOfLandWhereCanBe.containsKey(ProcessedTile.typeOfLand.elementName) || whereCanBe.typesOfLandWhereCanBe.containsKey(TypeOfLand.Void.elementName)){
                    return true;
                }
                break;
            case TYPE_OF_RESOURCE_NUM:
                //TypeOfLand
                if(whereCanBe.typesOfResourceWhereCanBe.containsKey(ProcessedTile.resource.elementName) || whereCanBe.typesOfResourceWhereCanBe.containsKey(Resource.none.elementName)){
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    static public boolean PositiveCheck_Full(Tile ProcessedTile, WhereCanBe whereCanBe){
        if(whereCanBe.typesOfLandWhereCanBe.size() == 0 || WhereCanBe.PositiveCheck_Specific(ProcessedTile, WhereCanBe.TYPE_OF_LAND_NUM, whereCanBe)){
            if(whereCanBe.typesOfResourceWhereCanBe.size() == 0 || WhereCanBe.PositiveCheck_Specific(ProcessedTile, WhereCanBe.TYPE_OF_RESOURCE_NUM, whereCanBe)){
                if(whereCanBe.typesOfFloraWhereCanBe.size() == 0  || WhereCanBe.PositiveCheck_Specific(ProcessedTile, WhereCanBe.TYPE_OF_FLORA_NUM, whereCanBe)){
                    return true;
                }
            }
        }
        return false;
    }

    static public boolean NegativeCheck_Specific(Tile ProcessedTile, int LayerType, WhereCanBe whereCanBe){
        switch(LayerType){
            case TYPE_OF_FLORA_NUM:
                //TypeOfFlora
                if(whereCanBe.typesOfFloraWhereDontBe.containsKey(ProcessedTile.typeOfFlora.elementName) || whereCanBe.typesOfFloraWhereDontBe.containsKey(TypeOfFlora.none.elementName)){
                    return true;
                }
                break;
            case TYPE_OF_LAND_NUM:
                //TypeOfLand
                if(whereCanBe.typesOfLandWhereDontBe.containsKey(ProcessedTile.typeOfLand.elementName) || whereCanBe.typesOfLandWhereDontBe.containsKey(TypeOfLand.Void.elementName)){
                    return true;
                }
                break;
            case TYPE_OF_RESOURCE_NUM:
                //TypeOfLand
                if(whereCanBe.typesOfResourceWhereDontBe.containsKey(ProcessedTile.resource.elementName) || whereCanBe.typesOfResourceWhereDontBe.containsKey(Resource.none.elementName)){
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    static public boolean NegativeCheck_Full(Tile ProcessedTile, WhereCanBe whereCanBe){
        if(WhereCanBe.NegativeCheck_Specific(ProcessedTile, WhereCanBe.TYPE_OF_LAND_NUM, whereCanBe)){
            return true;
        }
        if(WhereCanBe.NegativeCheck_Specific(ProcessedTile, WhereCanBe.TYPE_OF_RESOURCE_NUM, whereCanBe)){
            return true;
        }
        if(WhereCanBe.NegativeCheck_Specific(ProcessedTile, WhereCanBe.TYPE_OF_FLORA_NUM, whereCanBe)){
            return true;
        }
        return false;
    }

    static public boolean FullCheck(Tile ProcessedTile, WhereCanBe whereCanBe){
        if(WhereCanBe.PositiveCheck_Full(ProcessedTile, whereCanBe)){
            if(!WhereCanBe.NegativeCheck_Full(ProcessedTile, whereCanBe)){
                return true;
            }
        }
        return false;
    }


}
