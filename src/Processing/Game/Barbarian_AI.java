package Processing.Game;

import Processing.City.City;
import Processing.Player.Player;
import Processing.TileMap.Tile;
import Processing.Units.Ability.Colonize;
import Processing.Units.Ability.SpecialAbility;
import Processing.Units.Unit;
import Processing.Utilits.Point;
import Processing.Utilits.TileFinder.LightPlay;
import Processing.Utilits.TileFinder.Path;
import Processing.Utilits.WhereCanBe;
import Processing.Utilits.Wrapers.ThreeTTT;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;

public class Barbarian_AI {

    Player ME;
    LinkedList<Unit> AllUnits = new LinkedList<>();
    //LinkedList<Tile> WhereSpawnPoints = new LinkedList<>();

    int randomIntToSpawn = 100; //Up to 100
    int level = 1;

    private static final double STAY_DAMAGE = 5;
    private boolean unitIsSettler = false;

    public Barbarian_AI(Player Identity){
        this.ME = Identity;
        AllUnits = ME.playerUnits;
    }

    public void doTurn(){
        Iterator<Unit> iterator = AllUnits.iterator();
        while(iterator.hasNext()){
            unitIsSettler = false;
            Unit currentUnit = iterator.next();
            calculateShooting(currentUnit);
            Tile tileToMove = calculateUnitMove(currentUnit);
            if(tileToMove != null){
                currentUnit.move(tileToMove);
            }
            calculateShooting(currentUnit);
            if(unitIsSettler){
                tryColonize(currentUnit);
            }
            calculateMelee(currentUnit);
        }

        Iterator<City> tileIterator = ME.playerCities.iterator();
        while(iterator.hasNext()){
            calculateSpawnOfBarbarian(tileIterator.next().ownedTiles.peekFirst());
        }



    }

    public void spawnOrk(Tile tile){
        if(Game.RandomGen.nextInt(100) >= randomIntToSpawn){
            if(level == 1){

            }
        }
    }

    public void calculateSpawnOfBarbarian(Tile tile){
        if(tile.unit != null){
            if(tile.unit.owner != ME){
                tile.unit.hit(STAY_DAMAGE);
            }
        }
        else{
            spawnOrk(tile);
        }
    }

    public void calculateMelee(Unit unit){
        for(int i = 0; i < unit.currentNumberOfAttacks; i++){
            unit.prepareToMeleeAttack();
            Iterator<Tile> iterator = Unit.whereCanAttackMelee.iterator();
            Tile target = null;
            while(iterator.hasNext()){
                Tile TMP_Tile = iterator.next();
                if(TMP_Tile.unit.owner != ME){
                    if(target == null){
                        target = TMP_Tile;
                    }
                    else if(target.unit.currentHitPoints > TMP_Tile.unit.currentHitPoints){
                        target = TMP_Tile;
                    }
                }
            }
            if(target != null){
                unit.attackMelee(target);
            }
        }
    }

    public void calculateShooting(Unit unit){
        if(unit.typeOfUnit.isRanged){
            for(int i = 0; i < unit.currentNumberOfAttacks; i++){
                unit.prepareToShoot();
                Iterator<Tile> iterator = Unit.whereCanShootAndThereUnit.values().iterator();
                Tile target = null;
                while(iterator.hasNext()){
                    Tile TMP_Tile = iterator.next();
                    if(TMP_Tile.unit.owner != ME){
                        if(target == null){
                            target = TMP_Tile;
                        }
                        else if(target.unit.currentHitPoints > TMP_Tile.unit.currentHitPoints){
                            target = TMP_Tile;
                        }
                    }
                }
                if(target != null){
                    unit.attackRanged(target);
                }
            }
        }
    }

    public Tile calculateUnitMove(Unit unit){
        ThreeTTT<Tile[], HashMap<Point, Tile>, HashMap<Point, Tile>> light = LightPlay.AI_Vision(unit.onTile);
        HashMap<Tile, Path> moveRange = unit.prepareMove();

        Iterator<SpecialAbility>specialAbilityIterator = unit.Abilities.iterator();
        while(specialAbilityIterator.hasNext()){
            if(specialAbilityIterator.next().getClass() == Colonize.class){
                unitIsSettler = true;
                break;
            }
        }

        if(unitIsSettler){
            Iterator<Path> moveIterator = moveRange.values().iterator();
            while(moveIterator.hasNext()){
                Tile checkedTile = moveIterator.next().tilePath.getLast();
                if(checkForCiti(checkedTile)){
                    return checkedTile;
                }
            }
        }
        else{
            Iterator<Tile> foundCities = light.third.values().iterator();
            while(foundCities.hasNext()){
                Tile citiTile = foundCities.next();
                if(citiTile.unit == null){
                    if(moveRange.containsKey(citiTile)){
                        return citiTile;
                    }
                }
                else{
                    for(int i = 0; i < 8; i++){
                        if(moveRange.containsKey(citiTile.map.getTile(citiTile.coordinates.LookAt(Point.ALL_SIDES[i])))){
                            return (citiTile.map.getTile(citiTile.coordinates.LookAt(Point.ALL_SIDES[i])));
                        }
                    }
                }
            }

            Iterator<Tile> foundUnits = light.second.values().iterator();
            while(foundUnits.hasNext()){
                Tile unitTile = foundUnits.next();
                if(unitTile.unit.owner != ME){
                    for(int i = 0; i < 8; i++){
                        if(moveRange.containsKey(unitTile.map.getTile(unitTile.coordinates.LookAt(Point.ALL_SIDES[i])))){
                            return (unitTile.map.getTile(unitTile.coordinates.LookAt(Point.ALL_SIDES[i])));
                        }
                    }
                }
            }
        }


        LinkedList<Tile> whereCanBoardOrUnboard = new LinkedList<>();

        for(int i = 0; i < 8; i++){
            Tile toProceed = unit.onTile.map.getTile(unit.onTile.coordinates.LookAt(Point.ALL_SIDES[i]));
            if(!WhereCanBe.FullCheck(toProceed, unit.typeOfUnit.whereCanMove) && WhereCanBe.FullCheck(toProceed, unit.typeOfUnit.whereCanMove)){ //TODO put there ship and land variant of ork
                whereCanBoardOrUnboard.add(toProceed);
            }
        }


        int randomIndex = Game.RandomGen.nextInt(moveRange.size() + whereCanBoardOrUnboard.size());
        if(randomIndex < moveRange.size()){
            Iterator<Path> findMove = moveRange.values().iterator();
            for(int i = 0; i < randomIndex; i++){
                findMove.next();
            }
            return findMove.next().tilePath.peekLast();
        }
        else{
            Tile tileToBoard = whereCanBoardOrUnboard.get(randomIndex - moveRange.size());
            //TODO change state
            return null;
        }

    }

    public boolean checkForCiti(Tile tile){
        for(int y = -ME.colonizationSquare; y < ME.colonizationSquare; y++){
            for(int x = -ME.colonizationSquare; x < ME.colonizationSquare; x++){
                if(tile.map.getTile(tile.coordinates.LookAt(x,y)) != null){
                    if(tile.map.getTile((tile.coordinates.LookAt(x,y))).city != null){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void tryColonize(Unit unit){
        if(checkForCiti(unit.onTile)){
            Iterator<SpecialAbility> iterator = unit.Abilities.iterator();
            while(iterator.hasNext()){
                SpecialAbility SA = iterator.next();
                if(SA.getClass() == Colonize.class){
                    ((Colonize) SA).prepareFoundCiti();
                    ((Colonize) SA).foundNewCiti();
                }
            }
        }
    }


}
