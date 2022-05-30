package Processing.TileMap;

import Processing.Utilits.Point;

import java.io.Serializable;

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

}
