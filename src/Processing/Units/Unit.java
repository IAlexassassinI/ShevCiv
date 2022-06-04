package Processing.Units;

import Processing.Player.Player;
import Processing.TileMap.Tile;
import Processing.Utilits.Path;
import Processing.Utilits.PathFinder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

public class Unit implements Serializable {
    static final long serialVersionUID = 3L;

    public UnitPattern typeOfUnit;
    public Player owner;
    public Tile onTile;

    public double currentActionPoints;
    public double currentHitPoints;
    public double currentNumberOfAttacks;

    static private HashMap<Tile,Path> generatedPath; //TODO
    public HashMap<Tile,Path> getAllTPathInMoveRange(){
        generatedPath = PathFinder.findMovePath(currentActionPoints, onTile);
        return generatedPath;
    }

    public Tile[] getAllTilesInMoveRange(){
        Path Pathes[] = getAllTPathInMoveRange().values().toArray(new Path[0]);
        Tile Res[] = new Tile[Pathes.length];
        for(int i = 0; i < Pathes.length; i++){
            Res[i] = Pathes[i].getTilePath().peekLast();
        }
        return Res;
    }

    public LinkedList<Tile> Move(Tile toTile){
        Path pathToTile = generatedPath.get(toTile);
        if(pathToTile != null){
            this.onTile.unit = null;
            pathToTile.getTilePath().peekLast().unit = this;
            this.currentActionPoints = pathToTile.getCurrentActionPoints();
            return pathToTile.tilePath;
        }
        return null;
    }

    /*
    double costOfUpgrade;
    boolean canBeUpgradedByTech = false;

    public int canBeUpgraded(){
        if(costOfUpgrade <= owner.totalMoney){
            if(canBeUpgradedByTech){
                return 1; //can
            }
            else{
                return -2; //Dont have tech
            }
        }
        else{
            return -1; //Not enough money
        }
    }

    Talk about it
    variation of choose to upgrade
     */



}
