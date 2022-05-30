package Processing.TileMap;

import Processing.TileMap.TileUtils.Resource;
import Processing.TileMap.TileUtils.TypeOfFlora;
import Processing.TileMap.TileUtils.WhereCanSpawn;
import Processing.Utilits.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class GameMap implements Serializable {
    static final long serialVersionUID = 0L;

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

    //TODO AddRandomizedResources generator

    //Can process for a long time
    //TODO IN PROCESS
    private void generateRandomResource(int minIfPossible, int maxIfPossible){
        Resource AllRes[] = (Resource[])Resource.AllResource.values().toArray();

        LinkedList<Tile> Base = new LinkedList<>();
        LinkedList<Tile> Found = new LinkedList<>();

        for(int i = 0; i < AllRes.length; i++){
            Resource TMP_Res = AllRes[i];
            if(TMP_Res.elementName.equals(Resource.none)){
                continue;
            }

            WhereCanSpawn TMP_WerWhereCanSpawn = TMP_Res.whereCanSpawn;

            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; y++){
                    for(int j = 0; j < TMP_WerWhereCanSpawn.typesOfFloraWhereCanSpawn.length; j++){
                        if(Map[y][x].typeOfFlora == TypeOfFlora.AllTypeOfFlora.get(TMP_WerWhereCanSpawn.typesOfFloraWhereCanSpawn[j])){
                            Found.add(Map[y][x]);
                        }
                    }
                }
            }

            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; y++){
                    for(int j = 0; j < TMP_WerWhereCanSpawn.typesOfHeightWhereCanSpawn.length; j++){
                        if(Map[y][x].typeOfFlora == TypeOfFlora.AllTypeOfFlora.get(TMP_WerWhereCanSpawn.typesOfHeightWhereCanSpawn[j])){
                            Found.add(Map[y][x]);
                        }
                    }
                }
            }

        }


    }

}
