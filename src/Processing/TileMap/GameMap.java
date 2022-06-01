package Processing.TileMap;

import Processing.Game.Game;
import Processing.TileMap.TileUtils.*;
import Processing.Utilits.Point;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
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

            WhereCanBe TMP_WhereCanSpawn = TMP_Res.whereCanSpawn;

            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                    Tile ProcessedTile = Map[y][x];

                    if(ProcessedTile.resource != Resource.none){
                        continue;
                    }

                    if(WhereCanBe.PositiveCheck_JavaPorevo(ProcessedTile, WhereCanBe.TYPE_OF_FLORA_NUM, TMP_WhereCanSpawn)){
                        continue;
                    }
                    if(WhereCanBe.NegativeCheck_JavaPorevo(ProcessedTile, WhereCanBe.TYPE_OF_FLORA_NUM, TMP_WhereCanSpawn)){
                        continue;
                    }
                    if(WhereCanBe.PositiveCheck_JavaPorevo(ProcessedTile, WhereCanBe.TYPE_OF_LAND_NUM, TMP_WhereCanSpawn)){
                        continue;
                    }
                    if(WhereCanBe.NegativeCheck_JavaPorevo(ProcessedTile, WhereCanBe.TYPE_OF_LAND_NUM, TMP_WhereCanSpawn)){
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



}
