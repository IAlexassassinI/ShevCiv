package Processing.TileMap;

import Processing.FileHandler.SaveLoadInterface;
import Processing.Game.Game;
import Processing.TileMap.TileUtils.*;
import Processing.Units.Unit;
import Processing.Units.UnitPattern;
import Processing.Utilits.GeneralUtility;
import Processing.Utilits.Point;
import Processing.Utilits.TileFinder.LightPlay;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.LinkedList;

public class GameMap implements Serializable {
    static final long serialVersionUID = 12L;

    public GeneralUtility GU;

    Tile Map[][];
    int height;
    int width;

    public GameMap(int height, int width){
        this.GU = new GeneralUtility();
        GeneralUtility.initAllProcessingNeeds();
        this.Map = CreateBlankGameMap(height, width);
        this.height = height;
        this.width = width;
    }

    private Tile[][] CreateBlankGameMap(int height, int width){
        this.GU.saveStaticParams();
        Tile Map[][] = new Tile[height][width];
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Map[i][j] = new Tile(new Point(j,i), this);
                Map[i][j].CalculateWealth();
            }
        }
        return Map;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Tile getTile(int x, int y){
        if(x < 0){
            x = ((-1*x) % width);
            if(x != 0){
                x = width - x;
            }
        }
        else{
            x = x % width;
        }

        if(y < 0 || y >= height){
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

                    if(WhereCanBe.FullCheck(ProcessedTile, TMP_WhereCanSpawn)){
                        Found.add(ProcessedTile);
                    }
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
                    TileToChangeRes.setResource(TMP_Res);
                    Found.remove(TileToChangeRes);
                }
                else{
                    break;
                }
            }
        }
    }


    public Tile[][] getTiles() {
        return Map;
    }

    public void GenerateSettlers(Game game){
        LinkedList<Tile> Found = new LinkedList<>();
        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                Tile ProcessedTile = Map[y][x];
                if(WhereCanBe.FullCheck(ProcessedTile, UnitPattern.Settler.whereCanMove)){
                    Found.add(ProcessedTile);
                }
            }
        }
        if(Found.size() < game.numberOfPlayers){
            for(int y = 0; y < height; y++){
                for(int x = 0; x < width; x++){
                        Found.add(this.getTile(x,y));
                }
            }
            for(int i = 0; i < game.numberOfPlayers; i++){
                Tile TMP_Tile = Found.get(Game.RandomGen.nextInt(Found.size()));
                Found.remove(TMP_Tile);
                TMP_Tile.setTypeOfLand(TypeOfLand.AllTypeOfLand.get(TypeOfLand.FlatLand.elementName));
                TMP_Tile.setUnit(new Unit(game.players[i].mySettlerType, game.players[i], TMP_Tile));
                LightPlay.addToPlayerVision(TMP_Tile.unit);
            }
        }
        else{
            for(int i = 0; i < game.numberOfPlayers; i++){
                Tile TMP_Tile = Found.get(Game.RandomGen.nextInt(Found.size()));
                Found.remove(TMP_Tile);
                TMP_Tile.setUnit(new Unit(game.players[i].mySettlerType, game.players[i], TMP_Tile));
                LightPlay.addToPlayerVision(TMP_Tile.unit);
            }
        }

    }

}
