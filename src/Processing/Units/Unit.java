package Processing.Units;

import Processing.Player.Player;
import Processing.TileMap.Tile;
import Processing.Utilits.Path;
import Processing.Utilits.PathFinder;

import java.io.Serializable;

public class Unit implements Serializable {
    static final long serialVersionUID = 3L;

    public UnitPattern typeOfUnit;
    public Player owner;
    public Tile onTile;

    public double currentActionPoints;
    public double currentHitPoints;
    public double currentNumberOfAttacks;

    public Path[] getAllTPathInMoveRange(){
        return PathFinder.findMovePath(currentActionPoints, onTile);
    }

    public Tile[] getAllTilesInMoveRange(){
        Path Pathes[] = PathFinder.findMovePath(currentActionPoints, onTile);
        Tile Res[] = new Tile[Pathes.length];
        for(int i = 0; i < Pathes.length; i++){
            Res[i] = Pathes[i].getTilePath().peekLast();
        }
        return Res;
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
