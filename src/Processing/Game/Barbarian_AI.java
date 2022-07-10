package Processing.Game;

import Processing.City.City;
import Processing.Player.Player;
import Processing.TileMap.Tile;
import Processing.Units.Ability.Colonize;
import Processing.Units.Ability.GetCargoSmall;
import Processing.Units.Ability.SpecialAbility;
import Processing.Units.Unit;
import Processing.Units.UnitPattern;
import Processing.Utilits.Point;
import Processing.Utilits.TileFinder.LightPlay;
import Processing.Utilits.TileFinder.Path;
import Processing.Utilits.WhereCanBe;
import Processing.Utilits.Wrapers.ThreeTTT;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Barbarian_AI {

    Player ME;
    LinkedList<Unit> AllUnits = new LinkedList<>();
    //LinkedList<Tile> WhereSpawnPoints = new LinkedList<>();

    int randomIntToSpawn = 100; //Up to 100
    int level = 1;

    private static final double STAY_DAMAGE = 5;
    private boolean unitIsSettler = false;

    public Barbarian_AI(Player Identity, int IntToSpawn, int level){
        this.level = level;
        this.randomIntToSpawn = IntToSpawn;
        this.ME = Identity;
        AllUnits = ME.playerUnits;
    }



    public void doTurn(){
        LinkedList<Unit> TMP_ListOfUnits = new LinkedList<>(AllUnits);
        Iterator<Unit> iterator = TMP_ListOfUnits.iterator();
        while(iterator.hasNext()){
            unitIsSettler = false;
            Unit currentUnit = iterator.next();
            if(currentUnit.onTile == null){
                continue;
            }
            calculateShooting(currentUnit);
            Tile tileToMove = calculateUnitMove(currentUnit);
            if(tileToMove != null){
                if(currentUnit.onTile != null){
                    //currentUnit.unitComponent.move(tileToMove);
                    currentUnit.move(tileToMove);
                }
            }
            calculateShooting(currentUnit);
            if(unitIsSettler){
                tryColonize(currentUnit);
            }
            calculateMelee(currentUnit);
        }

        Iterator<City> tileIterator = ME.playerCities.iterator();
        while(tileIterator.hasNext()){
            City TMP_City = tileIterator.next();
            calculateSpawnOfBarbarian(TMP_City.ownedTiles.peekFirst());
        }

        ME.doEndTurn();

    }

    public void spawnOrk(Tile tile){
        if(!scanSquareForEnemy(tile, 3)){
            if(Game.RandomGen.nextBoolean()){
                tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkSettler.NameOfUnit), ME, tile));
                return;
            }
        }

        int intToRand = level * ME.Game.year;

        if(intToRand <= 10){
            tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkPeon.NameOfUnit), ME, tile));
            return;
        }
        else if(intToRand <= 20){
            switch(Game.RandomGen.nextInt(3)){
                case 0:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkSwordsman.NameOfUnit), ME, tile));
                    break;
                case 1:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkPeon.NameOfUnit), ME, tile));
                    break;
            }
            return;
        }
        else if(intToRand <= 30){
            tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkSwordsman.NameOfUnit), ME, tile));
            return;
        }
        else if(intToRand <= 40){
            switch(Game.RandomGen.nextInt(3)){
                case 0:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkSwordsman.NameOfUnit), ME, tile));
                    break;
                case 1:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkHunter.NameOfUnit), ME, tile));
                    break;
            }
            return;
        }
        else if(intToRand <= 50){
            switch(Game.RandomGen.nextInt(3)){
                case 0:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkSwordsman.NameOfUnit), ME, tile));
                    break;
                case 1:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkHunter.NameOfUnit), ME, tile));
                    break;
                case 2:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkCatapult.NameOfUnit), ME, tile));
                    break;
            }
            return;
        }
        else if(intToRand <= 60){
            switch(Game.RandomGen.nextInt(4)){
                case 0:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkSwordsman.NameOfUnit), ME, tile));
                    break;
                case 1:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkHunter.NameOfUnit), ME, tile));
                    break;
                case 2:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkCatapult.NameOfUnit), ME, tile));
                    break;
                case 3:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkWolfRider.NameOfUnit), ME, tile));
                    break;
            }
            return;
        }
        else if(intToRand <= 70){
            switch(Game.RandomGen.nextInt(4)){
                case 0:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkSwordsman.NameOfUnit), ME, tile));
                    break;
                case 1:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkWyvern.NameOfUnit), ME, tile));
                    break;
                case 2:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkCatapult.NameOfUnit), ME, tile));
                    break;
                case 3:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkWolfRider.NameOfUnit), ME, tile));
                    break;
            }
            return;
        }
        else if(intToRand <= 80){
            switch(Game.RandomGen.nextInt(4)){
                case 0:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.VeryBigOrk.NameOfUnit), ME, tile));
                    break;
                case 1:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkWyvern.NameOfUnit), ME, tile));
                    break;
                case 2:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkCatapult.NameOfUnit), ME, tile));
                    break;
                case 3:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkWolfRider.NameOfUnit), ME, tile));
                    break;
            }
            return;
        }
        else if(intToRand <= 90){
            switch(Game.RandomGen.nextInt(4)){
                case 0:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.VeryBigOrk.NameOfUnit), ME, tile));
                    break;
                case 1:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkWyvern.NameOfUnit), ME, tile));
                    break;
                case 2:
                    tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkCatapult.NameOfUnit), ME, tile));
                    break;
            }
            return;
        }
    }


    public boolean scanSquareForEnemy(Tile tile, int squareRadius){
        for(int y = -squareRadius; y < squareRadius; y++){
            for(int x = -squareRadius; x < squareRadius; x++){
                if(this.ME.Game.Map.getTile(tile.coordinates.LookAt(x,y)) != null){
                    if(this.ME.Game.Map.getTile(tile.coordinates.LookAt(x,y)).unit != null && this.ME.Game.Map.getTile(tile.coordinates.LookAt(x,y)).unit.owner != this.ME){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void calculateSpawnOfBarbarian(Tile tile){
        if(tile.unit != null){
            if(tile.unit.owner != ME){
                //tile.unit.hit(STAY_DAMAGE);
            }
        }
        else{
            if(scanSquareForEnemy(tile, 2)){
                if(Game.RandomGen.nextInt(100) <= randomIntToSpawn*1.2){
                    spawnOrk(tile);
                }
            }
            else if(scanSquareForEnemy(tile, 3)){
                if(Game.RandomGen.nextInt(100) <= randomIntToSpawn*1.1){
                    spawnOrk(tile);
                }
            }
            else{
                if(Game.RandomGen.nextInt(100) <= randomIntToSpawn){
                    spawnOrk(tile);
                }
            }
        }
    }

    public void calculateMelee(Unit unit){
        if(unit.onTile == null){
            return;
        }
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
                //unit.unitComponent.attack(target);
                unit.attackMelee(target);
            }
        }
    }

    public void calculateShooting(Unit unit){
        if(unit.onTile == null){
            return;
        }
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
                    //unit.unitComponent.attack(target);
                    unit.attackRanged(target);
                }
            }
        }
    }

    public Tile calculateUnitMove(Unit unit){
        if(unit.onTile == null){
            return null;
        }
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
                if(citiTile.owner.owner == ME){
                    continue;
                }
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

        if(UnitPattern.AllUnitPattern.get(unit.typeOfUnit.NameOfUnit) != UnitPattern.OrkWyvern){
            for(int i = 0; i < 8; i++){
                Tile toProceed = unit.onTile.map.getTile(unit.onTile.coordinates.LookAt(Point.ALL_SIDES[i]));
                if(toProceed == null || toProceed.unit != null){
                    continue;
                }
                if(UnitPattern.AllUnitPattern.get(unit.typeOfUnit.NameOfUnit) == UnitPattern.OrkBarge){
                    if(!WhereCanBe.FullCheck(toProceed, unit.typeOfUnit.whereCanMove) && WhereCanBe.FullCheck(toProceed, UnitPattern.OrkPeon.whereCanMove)){
                        whereCanBoardOrUnboard.add(toProceed);
                    }
                }
                else if(!WhereCanBe.FullCheck(toProceed, unit.typeOfUnit.whereCanMove) && WhereCanBe.FullCheck(toProceed, UnitPattern.OrkBarge.whereCanMove)) {
                    whereCanBoardOrUnboard.add(toProceed);
                }
            }
        }

        if(moveRange.size() + whereCanBoardOrUnboard.size() > 0){
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
                boardUnBoard(unit, tileToBoard);
                return null;
            }
        }
        return null;

    }

    private void boardUnBoard(Unit unit, Tile tile){
        if(unit.onTile == null){
            return;
        }
        if(UnitPattern.AllUnitPattern.get(unit.typeOfUnit.NameOfUnit) == UnitPattern.OrkBarge){
            Iterator<SpecialAbility> abilityIterator = unit.Abilities.iterator();
            GetCargoSmall foundAbility = null;
            while(abilityIterator.hasNext()){
                SpecialAbility SA = abilityIterator.next();
                if(SA.getClass() == GetCargoSmall.class){
                    foundAbility = ((GetCargoSmall) SA);
                    break;
                }
            }
            if(foundAbility != null){
                foundAbility.preparePutCargo();
                foundAbility.putCargo(tile);
                unit.destroy();
            }
        }
        else{
            tile.setUnit(new Unit(UnitPattern.AllUnitPattern.get(UnitPattern.OrkBarge.NameOfUnit), ME, tile));
            Iterator<SpecialAbility> abilityIterator = tile.unit.Abilities.iterator();
            GetCargoSmall foundAbility = null;
            while(abilityIterator.hasNext()){
                SpecialAbility SA = abilityIterator.next();
                if(SA.getClass() == GetCargoSmall.class){
                    foundAbility = ((GetCargoSmall) SA);
                    break;
                }
            }
            if(foundAbility != null){
                foundAbility.prepareTakeCargo();
                foundAbility.takeCargo(unit.onTile);
            }
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
        if(unit.onTile == null){
            return;
        }
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
