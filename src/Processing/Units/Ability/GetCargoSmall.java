package Processing.Units.Ability;

import Processing.TileMap.Tile;
import Processing.Units.Unit;
import Processing.Utilits.Point;
import Processing.Utilits.Tag;
import Processing.Utilits.TileFinder.LightPlay;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.LinkedList;

public class GetCargoSmall extends SpecialAbility implements Serializable {
    static final long serialVersionUID = 16L;

    public static String nameOfAbility = "GetCargoSmall";
    public static String description = "GetCargoSmall";
    public static double Cooldown = 1;

    static LinkedList<Tile> whereCanTake;
    static LinkedList<Tile> whereCanPut;

    public GetCargoSmall(Unit unit) {
        currentUnit = unit;
        currentCooldown = 0;
        isCargoFull = false;
    }

    public Unit currentUnit;
    public Unit cargo;
    public boolean isCargoFull;

    public LinkedList<Tile> prepareTakeCargo(){
        whereCanTake = new LinkedList<>();
        for(int i = 0; i < Point.ALL_SIDES.length-1; i++){
            Tile TMP_Tile = currentUnit.onTile.map.getTile(currentUnit.onTile.coordinates.LookAt(Point.ALL_SIDES[i]));
            if(TMP_Tile != null){
                if(TMP_Tile.unit != null && TMP_Tile.unit.owner == this.currentUnit.owner){
                    if(TMP_Tile.unit.typeOfUnit.tags.contains(Tag.small)){
                        whereCanTake.add(TMP_Tile);
                    }
                }
            }
        }
        return whereCanTake;
    }

    public LinkedList<Tile> preparePutCargo(){
        whereCanPut = new LinkedList<>();
        for(int i = 0; i < Point.ALL_SIDES.length-1; i++){
            Tile TMP_Tile = currentUnit.onTile.map.getTile(currentUnit.onTile.coordinates.LookAt(Point.ALL_SIDES[i]));
            if(TMP_Tile != null){
                if(TMP_Tile.unit == null){
                    if(cargo != null){
                        if(WhereCanBe.FullCheck(TMP_Tile, cargo.typeOfUnit.whereCanMove)){
                            whereCanPut.add(TMP_Tile);
                        }
                    }
                }
            }
        }
        return whereCanPut;
    }

    public void takeCargo(Tile onTile){
        if(!isCanUse()){
            return;
        }
        if(onTile == null){
            return;
        }
        if(!whereCanTake.contains(onTile)){
            return;
        }

        isCargoFull = true;
        cargo = onTile.unit;
        LightPlay.removeFromPlayerVision(cargo);
        cargo.onTile.setUnit(null);
        cargo.onTile = null;
        setOnCooldown();
    }

    public void putCargo(Tile onTile){
        if(!isCanUse()){
            return;
        }
        if(onTile == null){
            return;
        }
        if(!whereCanPut.contains(onTile)){
            return;
        }

        isCargoFull = false;
        onTile.setUnit(cargo);
        LightPlay.addToPlayerVision(cargo);
        cargo = null;
        setOnCooldown();
    }

    public LinkedList<Tile> prepareCargo(){
        if(isCargoFull){
            return preparePutCargo();
        }
        else{
            return prepareTakeCargo();
        }
    }

    public void relocateCargo(Tile onTile){
        if(isCargoFull){
            putCargo(onTile);
        }
        else{
            takeCargo(onTile);
        }
    }

    public Unit destroyCargo(){
        if(cargo != null){
            return cargo.destroy();
        }
        return null;
    }

    public void setOnCooldown(){
        this.currentCooldown = Cooldown;
    }

    public double getCooldown() {
        return currentCooldown;
    }

    public boolean isCanUse(){
        return currentCooldown == 0;
    }

}
