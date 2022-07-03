package Processing.Units.Ability;

import Processing.City.City;
import Processing.TileMap.Tile;
import Processing.Units.Unit;

import java.io.Serializable;
import java.util.LinkedList;

public class Colonize extends SpecialAbility implements Serializable {
    static final long serialVersionUID = 14L;
    public static String nameOfAbility = "Colonize";
    public static String description = "Colonize";
    public static double Cooldown = 1;

    public Colonize(Unit unit) {
        currentUnit = unit;
        currentCooldown = 0;
    }


    public Unit currentUnit;
    public static boolean canFoundNewCiti;

    public boolean prepareFoundCiti(){
        for(int y = -currentUnit.owner.colonizationSquare; y < currentUnit.owner.colonizationSquare; y++){
            for(int x = -currentUnit.owner.colonizationSquare; x < currentUnit.owner.colonizationSquare; x++){
                if(currentUnit.onTile.map.getTile(currentUnit.onTile.coordinates.LookAt(x,y)) != null){
                    if(currentUnit.onTile.map.getTile((currentUnit.onTile.coordinates.LookAt(x,y))).city != null){
                        canFoundNewCiti = false;
                        return false;
                    }
                }
            }
        }
        canFoundNewCiti = true;
        return true;
    }

    public void foundNewCiti(){
        if(canFoundNewCiti){
            currentUnit.onTile.city = new City(currentUnit);
            currentUnit.destroy();
        }
    }



}
