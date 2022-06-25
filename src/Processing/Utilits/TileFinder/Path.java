package Processing.Utilits.TileFinder;

import Processing.TileMap.Tile;

import java.util.LinkedList;

public class Path {
    static final long serialVersionUID = 23L;
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
