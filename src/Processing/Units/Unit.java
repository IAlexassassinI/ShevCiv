package Processing.Units;

import Processing.Player.Player;
import Processing.TileMap.Tile;
import Processing.Units.Ability.GetCargoSmall;
import Processing.Units.Ability.SpecialAbility;
import Processing.Utilits.Point;
import Processing.Utilits.TileFinder.LightPlay;
import Processing.Utilits.TileFinder.Path;
import Processing.Utilits.TileFinder.PathFinder;
import Processing.Utilits.Wrapers.TwoTTT;
import graphics.components.tiledmap.UnitComponent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Unit implements Serializable {
    static final long serialVersionUID = 20L;

    public UnitComponent unitComponent;

    public Unit(UnitPattern pattern, Player owner, Tile onTile){
        this.typeOfUnit = pattern;
        this.owner = owner;
        owner.playerUnits.add(this);
        this.onTile = onTile;

        this.currentActionPoints = pattern.maxActionPoints;
        this.currentHitPoints = pattern.maxHitPoints;
        this.currentNumberOfAttacks = pattern.maxNumberOfAttacks;
        Iterator<String> iterator = pattern.Abilities.iterator();
        this.Abilities = new ArrayList<>();
        while(iterator.hasNext()){
            this.Abilities.add(SpecialAbility.fabricateSpecialAbility(iterator.next(), this));
        }
    }

    public UnitPattern typeOfUnit;
    public Player owner;
    public Tile onTile;

    public double currentActionPoints;
    public double currentHitPoints;
    public double currentNumberOfAttacks;
    public ArrayList<SpecialAbility> Abilities;

    static public HashMap<Tile,Path> generatedPath;
    static public LinkedList<Tile> whereCanAttackMelee;
    static public LinkedList<Integer> whereCanAttackDirection;
    static public Tile[] whereCanShoot;
    static public HashMap<Point, Tile> whereCanShootAndThereUnit;

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
            toTile.setUnit(this);
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
                if(this.typeOfUnit.isFlying){
                    if(TMP_Tile.unit != null){
                        //left ability to attack alies and own units
                        whereCanAttackMelee.add(TMP_Tile);
                        whereCanAttackDirection.add(i);
                    }
                }
                else{
                    if(TMP_Tile.unit != null && !TMP_Tile.unit.typeOfUnit.isFlying){
                        //left ability to attack alies and own units
                        whereCanAttackMelee.add(TMP_Tile);
                        whereCanAttackDirection.add(i);
                    }
                }
            }
        }
        return whereCanAttackMelee;
    }


    public Tile[] prepareToShoot(){
        TwoTTT<Tile[], HashMap<Point, Tile>> TMP_Two = LightPlay.findShootingRange(this.onTile);
        whereCanShoot = TMP_Two.first;
        whereCanShootAndThereUnit = TMP_Two.second;
        return whereCanShoot;
    }

    public void attack(Tile whatAttack, boolean ranged){
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
            if(!whereCanShootAndThereUnit.containsKey(whatAttack.coordinates)){
                return;
            }
            attackOwn = this.typeOfUnit.rangedAttack * onTile.resource.battleModifier.additionalAttackRanged * onTile.typeOfBuilding.battleModifier.additionalAttackRanged * onTile.typeOfFlora.battleModifier.additionalAttackRanged * onTile.typeOfLand.battleModifier.additionalAttackRanged;
            defenceTarget = whatAttack.unit.typeOfUnit.defenceRanged * whatAttack.resource.battleModifier.additionalDefenseRanged * whatAttack.typeOfBuilding.battleModifier.additionalDefenseRanged * whatAttack.typeOfFlora.battleModifier.additionalDefenseRanged * whatAttack.typeOfLand.battleModifier.additionalDefenseRanged;

            attackOwn = attackOwn * this.owner.battleModifier.additionalAttackRanged;
            defenceTarget =  defenceTarget * whatAttack.unit.owner.battleModifier.additionalDefenseRanged;

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

            attackOwn = attackOwn * this.owner.battleModifier.additionalAttackMelee;
            defenceOwn = defenceOwn * this.owner.battleModifier.additionalDefenseMelee;

            attackTarget = attackTarget * whatAttack.unit.owner.battleModifier.additionalAttackMelee;
            defenceTarget =  defenceTarget * whatAttack.unit.owner.battleModifier.additionalDefenseMelee;

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
        attack(whatAttack, false);
    }

    public void attackRanged(Tile whatAttack){
        attack(whatAttack, true);
    }

    private static double calculateDamage(double attack, double defence){
        return 10*(attack * BattleModifier.ATTACK_CONST)/(defence + BattleModifier.ATTACK_CONST);
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

    public Unit destroy(){
        LightPlay.removeFromPlayerVision(this);
        if(onTile != null){
            onTile.unit = null;
            onTile = null;
        }
        this.owner.playerUnits.remove(this);
        Iterator<SpecialAbility> iterator = Abilities.iterator();
        while(iterator.hasNext()){
            SpecialAbility TMP_Ability = iterator.next();
            if(TMP_Ability.getClass() == GetCargoSmall.class){
                ((GetCargoSmall) TMP_Ability).destroyCargo();
            }
        }
        return this;
    }

    public void doEndTurn(){
        if(currentActionPoints > 1){
            currentHitPoints = currentHitPoints + typeOfUnit.maxHitPoints*0.05;
        }
        if(currentHitPoints > typeOfUnit.maxHitPoints){
            currentHitPoints = typeOfUnit.maxHitPoints;
        }
        currentActionPoints = typeOfUnit.maxActionPoints;
        currentNumberOfAttacks = typeOfUnit.maxNumberOfAttacks;
        for(int i = 0; i < Abilities.size(); i++){
            Abilities.get(i).decreaseCooldown();
        }
        typeOfUnit.doUpkeep(owner);
    }


}
