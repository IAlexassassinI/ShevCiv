package Processing.Utilits;

import Processing.TileMap.Tile;

import java.util.LinkedList;

public class Path {

    Path(double currentActionPoints){
        this.currentActionPoints = currentActionPoints;
    }

    Path(Path previousPath, Tile NewTile, double NewActionPoints){
        this.currentActionPoints = NewActionPoints;
        this.tilePath = (LinkedList<Tile>)previousPath.tilePath.clone();
        this.tilePath.add(NewTile);
    }


    public LinkedList<Tile> tilePath = new LinkedList<>();
    double currentActionPoints;

    public LinkedList<Tile> getTilePath() {
        return tilePath;
    }

    public double getCurrentActionPoints() {
        return currentActionPoints;
    }
}
