package Processing.Utilits.TileFinder;

import Processing.TileMap.Tile;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Path implements Serializable {
    static final long serialVersionUID = 23L;
    Path(double currentActionPoints){
        this.currentActionPoints = currentActionPoints;
    }

    Path(Path previousPath, Tile NewTile, double NewActionPoints){
        this.currentActionPoints = NewActionPoints;
        this.tilePath = new LinkedList<>();
        Iterator<Tile> iterator = previousPath.tilePath.iterator();
        while(iterator.hasNext()){
            this.tilePath.add(iterator.next());
        }
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
