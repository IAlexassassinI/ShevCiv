package Processing.TileMap;

import Processing.Game.Game;
import Processing.TileMap.TileUtils.*;
import Processing.Utilits.Point;

import java.io.Serializable;
import java.util.LinkedList;

public class GameMap implements Serializable {
    static final long serialVersionUID = 0L;

    final static private int HEIGHT_NUM = 0;
    final static private int TYPE_OF_FLORA_NUM = 1;
    final static private int TYPE_OF_LAND_NUM = 2;

    Tile Map[][];
    int height;
    int width;

    public GameMap(int height, int width){
        this.Map = CreateBlankGameMap(height, width);
        this.height = height;
        this.width = width;
    }

    private Tile[][] CreateBlankGameMap(int height, int width){
        Tile Map[][] = new Tile[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Map[i][j] = new Tile(new Point(j,i), this);
            }
        }
        return Map;
    }

    //TODO Set params from file maybe Not
    private void LoadMapLayers(){

    }

    public Tile getTile(int x, int y){
        if(x < 0){
            x = width - ((-1*x) % width);
        }
        else{
            x = x % width;
        }

        if(y < 0 || y > height){
            return null;
        }

        return Map[y][x];
    }

    public Tile getTile(Point coordinates){
        if(coordinates == null){
            return null;
        }
        int x = coordinates.x;
        int y = coordinates.y;

        return getTile(x,y);

    }

    //Not optimized yet
    Tile[][] getScreenMap(Point topLeft, Point bottomRight){
        return getScreenMap(topLeft, bottomRight.y-topLeft.y, bottomRight.x-topLeft.x);
    }

    //Not optimized yet
    Tile[][] getScreenMap(Point topLeft, int height, int width){
        Tile[][] ScreenMap = new Tile[height][width];
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                ScreenMap[y][x] = getTile(topLeft.x+x,topLeft.y+y);
            }
        }
        return ScreenMap;
    }

    //Can process for a long time
    public void generateRandomResource(int minIfPossible, int maxIfPossible){
        Resource AllRes[] = Resource.AllResource.values().toArray(new Resource[0]);

        //HashMap point|tile
        LinkedList<Tile> Found = new LinkedList<>();

        for(int i = 1; i < AllRes.length; i++){
            Resource TMP_Res = AllRes[i];

            /*
            if(TMP_Res == Resource.none){
                continue;
            }
             */

            WhereCanSpawn TMP_WhereCanSpawn = TMP_Res.whereCanSpawn;

            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    Tile ProcessedTile = Map[y][x];

                    if(ProcessedTile.resource != Resource.none){
                        continue;
                    }

                    if(PositiveCheck_JavaPorevo(ProcessedTile, TYPE_OF_FLORA_NUM, TMP_WhereCanSpawn)){
                        continue;
                    }
                    if(NegativeCheck_JavaPorevo(ProcessedTile, TYPE_OF_FLORA_NUM, TMP_WhereCanSpawn)){
                        continue;
                    }
                    if(PositiveCheck_JavaPorevo(ProcessedTile, HEIGHT_NUM, TMP_WhereCanSpawn)){
                        continue;
                    }
                    if(NegativeCheck_JavaPorevo(ProcessedTile, HEIGHT_NUM, TMP_WhereCanSpawn)){
                        continue;
                    }
                    if(PositiveCheck_JavaPorevo(ProcessedTile, TYPE_OF_LAND_NUM, TMP_WhereCanSpawn)){
                        continue;
                    }
                    if(NegativeCheck_JavaPorevo(ProcessedTile, TYPE_OF_LAND_NUM, TMP_WhereCanSpawn)){
                        continue;
                    }

                    Found.add(ProcessedTile);

                }
            }


            int NumberOfCurrentRes = 0;
            if(minIfPossible < maxIfPossible){
                NumberOfCurrentRes = Game.RandomGen.nextInt(minIfPossible, maxIfPossible);
            }
            else if(maxIfPossible == minIfPossible){
                NumberOfCurrentRes = minIfPossible;
            }

            for(int j = 0; j < NumberOfCurrentRes; j++){
                if(Found.size() > 0) {
                    Tile TileToChangeRes = Found.get(Game.RandomGen.nextInt(Found.size()));
                    TileToChangeRes.resource = TMP_Res;
                    Found.remove(TileToChangeRes);
                }
                else{
                    break;
                }
            }



        }


    }

    /*
    static private boolean PositiveCheck(TileLayer TileTypeForCheck, HashMap<String,TileLayer> FromWhatGetTypeForCheck, String[] TMP_TypeForCheck){
        if(TMP_TypeForCheck == null || TMP_TypeForCheck.length == 0){
            return false;
        }
        else{
            for(int j = 0; j < TMP_TypeForCheck.length; j++){
                if(TileTypeForCheck == FromWhatGetTypeForCheck.get(TMP_TypeForCheck[j])){
                    return false;
                }
            }
        }
        return true;
    }
     */



    static private boolean PositiveCheck_JavaPorevo(Tile ProcessedTile, int LayerType, WhereCanSpawn TMP_WhereCanSpawn){
        switch(LayerType){
            case HEIGHT_NUM:
                //Height
                if(TMP_WhereCanSpawn.typesOfHeightWhereCanSpawn == null || TMP_WhereCanSpawn.typesOfHeightWhereCanSpawn.containsKey(ProcessedTile.height.elementName)){
                    return false;
                }
                break;
            case TYPE_OF_FLORA_NUM:
                //TypeOfFlora
                if(TMP_WhereCanSpawn.typesOfFloraWhereCanSpawn == null || TMP_WhereCanSpawn.typesOfFloraWhereCanSpawn.containsKey(ProcessedTile.typeOfFlora.elementName)){
                    return false;
                }
                break;
            case TYPE_OF_LAND_NUM:
                //TypeOfLand
                if(TMP_WhereCanSpawn.typesOfLandWhereCanSpawn == null || TMP_WhereCanSpawn.typesOfLandWhereCanSpawn.containsKey(ProcessedTile.typeOfLand.elementName)){
                    return false;
                }
                break;
            default:
                break;
        }
        return true;
    }

    static private boolean NegativeCheck_JavaPorevo(Tile ProcessedTile, int LayerType, WhereCanSpawn TMP_WhereCanSpawn){
        switch(LayerType){
            case HEIGHT_NUM:
                //Height
                if(TMP_WhereCanSpawn.typesOfHeightWhereDontSpawn == null || TMP_WhereCanSpawn.typesOfHeightWhereDontSpawn.containsKey(ProcessedTile.height.elementName)){
                    return true;
                }
                break;
            case TYPE_OF_FLORA_NUM:
                //TypeOfFlora
                if(TMP_WhereCanSpawn.typesOfFloraWhereDontSpawn == null || TMP_WhereCanSpawn.typesOfFloraWhereDontSpawn.containsKey(ProcessedTile.height.elementName)){
                    return true;
                }
                break;
            case TYPE_OF_LAND_NUM:
                //TypeOfLand
                if(TMP_WhereCanSpawn.typesOfLandWhereDontSpawn == null || TMP_WhereCanSpawn.typesOfFloraWhereDontSpawn.containsKey(ProcessedTile.height.elementName)){
                    return true;
                }
                break;
            default:
                break;
        }
        return false;
    }

    /*
    static private boolean NegativeCheck(TileLayer TileTypeForCheck, HashMap<String,TileLayer> FromWhatGetTypeForCheck, String[] TMP_TypeForCheck){
        if(TMP_TypeForCheck != null){
            for(int j = 0; j < TMP_TypeForCheck.length; j++){
                if(TileTypeForCheck == FromWhatGetTypeForCheck.get(TMP_TypeForCheck[j])){
                    return true;
                }
            }
        }
        return false;
    }
     */

}
