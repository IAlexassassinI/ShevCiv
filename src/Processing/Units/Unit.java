package Processing.Units;

import Processing.Player.Player;
import Processing.TileMap.Tile;
import Processing.Utilits.Point;
import Processing.Utilits.TileFinder.LightPlay;
import Processing.Utilits.TileFinder.Path;
import Processing.Utilits.TileFinder.PathFinder;
import Processing.Utilits.TwoTTT;

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
    static private LinkedList<Tile> whereCanAttackMelee;
    static private LinkedList<Integer> whereCanAttackDirection;
    static private Tile[] whereCanShoot;
    static private HashMap<Point, Tile> whereCanShootAndThereUnit;

    public HashMap<Tile,Path> prepareMove(){
        generatedPath = PathFinder.findMovePath(currentActionPoints, onTile);
        return generatedPath;
    }

    public Tile[] getAllTilesInMoveRange(){
        Path Pathes[] = prepareMove().values().toArray(new Path[0]);
        Tile Res[] = new Tile[Pathes.length];
        for(int i = 0; i < Pathes.length; i++){
            Res[i] = Pathes[i].getTilePath().peekLast();
        }
        return Res;
    }

    public LinkedList<Tile> move(Tile toTile){
        Path pathToTile = generatedPath.get(toTile);
        if(pathToTile != null){

            LightPlay.removeFromPlayerVision(this);

            this.onTile.unit = null;
            pathToTile.getTilePath().peekLast().unit = this;
            this.currentActionPoints = pathToTile.getCurrentActionPoints();

            LightPlay.addToPlayerVision(this);

            return pathToTile.tilePath;
        }
        return null;
    }

    public LinkedList<Tile> prepareToMeleeAttack(){
        whereCanAttackMelee = new LinkedList<>();
        whereCanAttackDirection = new LinkedList<>();
        for(int i = 0; i < Point.ALL_SIDES.length-1; i++){
            Tile TMP_Tile = onTile.map.getTile(onTile.coordinates.LookAt(Point.ALL_SIDES[i]));
            if(TMP_Tile != null){
                if(TMP_Tile.unit != null && !TMP_Tile.unit.typeOfUnit.isFlying){
                    //left ability to attack alies and own units
                    whereCanAttackMelee.add(TMP_Tile);
                    whereCanAttackDirection.add(i);
                }
            }
        }
        return whereCanAttackMelee;
    }


    public Tile[] PrepareToShoot(){
        TwoTTT<Tile[], HashMap<Point, Tile>> TMP_Two = LightPlay.findShootingRange(this.onTile);
        whereCanShoot = TMP_Two.First;
        whereCanShootAndThereUnit = TMP_Two.Second;
        return whereCanShoot;
    }

    public void Attack(Tile whatAttack, boolean ranged){
        if(whatAttack == null){
            return;
        }
        if(this.currentNumberOfAttacks <= 0){
            return;
        }

        double attackOwn;
        double defenceOwn;
        double attackTarget;
        double defenceTarget;


        if(ranged){
            if(!whereCanShootAndThereUnit.containsKey(whatAttack)){
                return;
            }
            attackOwn = this.typeOfUnit.rangedAttack * onTile.resource.battleModifier.additionalAttackRanged * onTile.typeOfBuilding.battleModifier.additionalAttackRanged * onTile.typeOfFlora.battleModifier.additionalAttackRanged * onTile.typeOfLand.battleModifier.additionalAttackRanged;
            defenceTarget = whatAttack.unit.typeOfUnit.defenceRanged * whatAttack.resource.battleModifier.additionalDefenseRanged * whatAttack.typeOfBuilding.battleModifier.additionalDefenseRanged * whatAttack.typeOfFlora.battleModifier.additionalDefenseRanged * whatAttack.typeOfLand.battleModifier.additionalDefenseRanged;

            whatAttack.unit.hit(calculateDamage(attackOwn, defenceTarget));
        }
        else{
            if(!whereCanAttackMelee.contains(whatAttack)){
                return;
            }
            attackOwn = this.typeOfUnit.attackMelee * onTile.resource.battleModifier.additionalAttackMelee * onTile.typeOfBuilding.battleModifier.additionalAttackMelee * onTile.typeOfFlora.battleModifier.additionalAttackMelee * onTile.typeOfLand.battleModifier.additionalAttackMelee;
            defenceOwn = this.typeOfUnit.defenceMelee * onTile.resource.battleModifier.additionalDefenseMelee * onTile.typeOfBuilding.battleModifier.additionalDefenseMelee * onTile.typeOfFlora.battleModifier.additionalDefenseMelee * onTile.typeOfLand.battleModifier.additionalDefenseMelee;

            attackTarget = whatAttack.unit.typeOfUnit.attackMelee * whatAttack.resource.battleModifier.additionalAttackMelee * whatAttack.typeOfBuilding.battleModifier.additionalAttackMelee * whatAttack.typeOfFlora.battleModifier.additionalAttackMelee * whatAttack.typeOfLand.battleModifier.additionalAttackMelee;
            defenceTarget = whatAttack.unit.typeOfUnit.defenceMelee * whatAttack.resource.battleModifier.additionalDefenseMelee * whatAttack.typeOfBuilding.battleModifier.additionalDefenseMelee * whatAttack.typeOfFlora.battleModifier.additionalDefenseMelee * whatAttack.typeOfLand.battleModifier.additionalDefenseMelee;

            int direction = whereCanAttackDirection.get(whereCanAttackMelee.indexOf(whatAttack));
            if(onTile.isBridge(direction)){
                attackOwn = attackOwn * BattleModifier.ATTACK_ACROSS_THE_BRIDGE_MODIFIER;
                defenceTarget = defenceTarget * BattleModifier.DEFENCE_ACROSS_THE_BRIDGE_MODIFIER;
            }
            else if(onTile.isRiver(direction)){
                attackOwn = attackOwn * BattleModifier.ATTACK_ACROSS_THE_RIVER_MODIFIER;
                defenceTarget = defenceTarget * BattleModifier.DEFENCE_ACROSS_THE_RIVER_MODIFIER;
            }

            double selfDamage = calculateDamage(attackTarget, defenceOwn);
            double targetDamage = calculateDamage(attackOwn, defenceTarget);

            if(whatAttack.unit.hit(targetDamage)){
                this.hit(selfDamage * BattleModifier.RESPONSE_WHEN_KILLED);
            }
            else{
                this.hit(selfDamage);
            }
        }

        this.currentNumberOfAttacks--;
    }

    public void attackMelee(Tile whatAttack){
        Attack(whatAttack, false);
    }

    public void attackRanged(Tile whatAttack){
        Attack(whatAttack, true);
    }

    private static double calculateDamage(double attack, double defence){
        return (attack * BattleModifier.ATTACK_CONST)/(defence + BattleModifier.ATTACK_CONST);
    }

    public boolean hit(double damage){
        this.currentHitPoints = this.currentHitPoints - damage;
        if(this.currentHitPoints <= 0){
            this.destroy();
            return true;
        }
        else{
            return false;
        }
    }

    private Unit destroy(){
        LightPlay.removeFromPlayerVision(this);
        onTile.unit = null;
        //TODO delete from player list of units
        return this;
    }




}
