package Processing.City;

import Processing.Buildings.Building;
import Processing.Buildings.Job;
import Processing.Player.Player;
import Processing.TileMap.Tile;
import Processing.TileMap.TileUtils.Resource;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Units.Unit;
import Processing.Units.UnitPattern;
import Processing.Utilits.TileFinder.LightPlay;
import Processing.Utilits.Wealth;
import Processing.Utilits.Wrapers.CreatableObject;
import Processing.Utilits.Wrapers.TwoTTT;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class City implements Serializable {
    static final long serialVersionUID = 2L;

    public String name;

    public Player owner;

    public LinkedList<Tile> ownedTiles;

    public Wealth wealth = new Wealth();
    public LinkedList<TwoTTT<Resource, Integer>> NeededResources = new LinkedList<>();

    public LinkedList<Building> buildingsThatCanBeBuild = new LinkedList<>();
    public LinkedList<Boolean> buildingCanBeBuilt = new LinkedList<>();
    public LinkedList<Building> alreadyBuiltBuildings = new LinkedList<>();

    public LinkedList<UnitPattern> unitsThatCanBeBuild = new LinkedList<>();
    public LinkedList<UnitPattern> createdUnits = new LinkedList<>();

    public LinkedList<CreatableObject> creationList = new LinkedList<>();

    public LinkedList<Job> jobs = new LinkedList<>();
    public LinkedList<Integer> jobsCount = new LinkedList<>();
    public LinkedList<Integer> jobsOccupiedCount = new LinkedList<>();

    public static final double FOOD_FOR_NEW_CITIZEN = 32;
    public static final double BASE_FOOD_STOCK = 5;
    public static final int STANDARD_NUMBER_OF_START_CITIZENS = 1;
    public static final double BASIC_TILE_PRICE = 10;
    public static double PriceForTile = 10;
    public double foodStock;
    public double foodToNewCitizen;
    public int numberOfCitizen;
    public int numberOfFreeCitizen;

    public City(Unit unit){
        this.owner = unit.owner;
        this.ownedTiles.add(unit.onTile);
        LightPlay.addToPlayerVision(unit.onTile, this.owner);

        if(owner.namesOfCities.isEmpty()){
           this.name = "New_city";
        } else {
            this.name = owner.namesOfCities.pop();
        }
        buildingsThatCanBeBuild = owner.buildings;
        Iterator iterator = buildingsThatCanBeBuild.iterator();
        while(iterator.hasNext()){
            buildingCanBeBuilt.add(true);
            iterator.next();
        }
        unitsThatCanBeBuild = owner.unitPatterns;
        foodToNewCitizen = FOOD_FOR_NEW_CITIZEN;
        foodStock = BASE_FOOD_STOCK;
        numberOfCitizen = STANDARD_NUMBER_OF_START_CITIZENS;
        numberOfFreeCitizen = numberOfCitizen;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addToCreationList(Object object){
        if(object.getClass() == UnitPattern.class){
            UnitPattern pattern = ((UnitPattern)object);
            if(unitsThatCanBeBuild.contains(pattern)){
                creationList.add(new CreatableObject<UnitPattern>(pattern, pattern.productionCost));
            }
        }
        else{
            Building pattern = ((Building)object);
            if(buildingsThatCanBeBuild.contains(pattern) && buildingCanBeBuilt.get(buildingCanBeBuilt.indexOf(pattern))){
                creationList.add(new CreatableObject<Building>(pattern, pattern.productionCost));
                this.buildingCanBeBuilt.set(this.buildingsThatCanBeBuild.indexOf((Building)object), false);
            }
        }
    }

    public void removeFromCreationList(int index){
        CreatableObject object = creationList.remove(index);
        if(object.object.getClass() == Building.class){
            this.buildingCanBeBuilt.set(this.buildingsThatCanBeBuild.indexOf((Building)object.object), true);
        }
    }

    public void changeOrderInCreationList(int First, int Second){
        CreatableObject TMP_CreationObjectFirst = creationList.get(First).getCopy();
        creationList.set(First, creationList.get(Second));
        creationList.set(Second, TMP_CreationObjectFirst);
    }

    public void changeOrderInCreationList(CreatableObject First, CreatableObject Second){
        changeOrderInCreationList(creationList.indexOf(First), creationList.indexOf(Second));
    }

    public Object addProduction(){
        if(!creationList.isEmpty()){
            if(owner.inDepression){
                creationList.peekFirst().addProgress(this.wealth.production * Player.DEPRESSION_DEBUFF);
            }
            else{
                creationList.peekFirst().addProgress(this.wealth.production);
            }
            if(creationList.peekFirst().status){
                return creationList.pop();
            }
        }
        return null;
    }

    public void addBuilding(Building building){
        this.alreadyBuiltBuildings.add(building);
        this.wealth = this.wealth.dWealth(this.alreadyBuiltBuildings.peekLast().passiveWealth);
    }

    public void destroyBuilding(Building building){
        this.wealth = this.wealth.dMinusWealth(this.alreadyBuiltBuildings.get(this.alreadyBuiltBuildings.indexOf(building)).passiveWealth);
        this.alreadyBuiltBuildings.remove(building);
        if(this.owner.buildings.contains(building)){
            this.buildingCanBeBuilt.set(this.buildingsThatCanBeBuild.indexOf(building), true);
        }
    }

    public void addUnit(UnitPattern pattern){
        createdUnits.add(pattern);
    }

    public void removeFirstUnitReadyList(){
        this.createdUnits.pop();
    }

    public void addCitizenToJob(Job job){
        int index = this.jobs.indexOf(job);
        if(numberOfFreeCitizen > 0){
            if(jobsCount.get(index) > jobsOccupiedCount.get(index)){
                numberOfFreeCitizen--;
                jobsOccupiedCount.set(index, jobsOccupiedCount.get(index)+1);
                this.wealth.dWealth(job.wealth);
            }
        }
    }

    public void removeCitizenFromJob(Job job){
        int index = this.jobs.indexOf(job);
        if(jobsOccupiedCount.get(index) > 0){
            numberOfFreeCitizen++;
            jobsOccupiedCount.set(index, jobsOccupiedCount.get(index)-1);
            this.wealth.dMinusWealth(job.wealth);
        }
    }

    public void addCitizenToTile(Tile tile){
        if(ownedTiles.contains(tile)){
            if(!tile.isProcessedByPeople){
                if(numberOfFreeCitizen > 0){
                    numberOfFreeCitizen--;
                    tile.isProcessedByPeople = true;
                    this.wealth.dWealth(tile.wealth);
                }
            }
        }
    }

    public void removeCitizenFromTile(Tile tile){
        if(ownedTiles.contains(tile)){
            if(tile.isProcessedByPeople){
                numberOfFreeCitizen++;
                tile.isProcessedByPeople = false;
                this.wealth.dMinusWealth(tile.wealth);
           }
        }
    }

    public void destroyCiti(){
        this.ownedTiles.peekFirst().typeOfBuilding = TypeOfBuilding.none;
        this.ownedTiles.peekFirst().city = null;
        owner.playerCities.remove(this);
        Iterator<Tile> iterator = ownedTiles.iterator();
        while(iterator.hasNext()){
            iterator.next().owner = null;
        }
        this.owner = null;
    }

    public void conquerCiti(Player newOwner){
        LightPlay.removeFromPlayerVision(ownedTiles, owner);
        owner.playerCities.remove(this);
        newOwner.playerCities.add(this);
        this.owner = newOwner;
        buildingsThatCanBeBuild = owner.buildings;
        Iterator iterator = buildingsThatCanBeBuild.iterator();
        while(iterator.hasNext()){
            if(alreadyBuiltBuildings.contains(iterator.next())){
                buildingCanBeBuilt.add(false);
            }
            else{
                buildingCanBeBuilt.add(true);
            }
        }
        unitsThatCanBeBuild = owner.unitPatterns;
        LightPlay.addToPlayerVision(ownedTiles, owner);
        creationList = new LinkedList<>();
    }

    public void killCitizen(){
        if(numberOfFreeCitizen <= 0) {
            boolean found = false;
            Iterator<Integer> iterator = this.jobsOccupiedCount.iterator();
            int i = 0;
            while (iterator.hasNext()) {
                if (iterator.next() > 0) {
                    found = true;
                    removeCitizenFromJob(this.jobs.get(i));
                    break;
                }
                i++;
            }
            if (!found) {
                Iterator<Tile> iteratorT = this.ownedTiles.iterator();
                Tile TMP_Tile;
                while (iteratorT.hasNext()) {
                    TMP_Tile = iteratorT.next();
                    if (TMP_Tile.isProcessedByPeople) {
                        removeCitizenFromTile(TMP_Tile);
                        break;
                    }
                }
            }
        }
        numberOfFreeCitizen--;
        numberOfCitizen--;
        if(numberOfCitizen <= 0){
            this.destroyCiti();
        }
    }

    public void doEndTurn(){
        if(ownedTiles.peekFirst().unit.owner != owner){
            this.conquerCiti(ownedTiles.peekFirst().unit.owner);
        }
        Object construct = addProduction();
        if(construct != null){
            if(construct.getClass() == Building.class){
                addBuilding((Building) construct);
            }
            else{
                addUnit((UnitPattern) construct);
            }
        }
        if(!createdUnits.isEmpty() && ownedTiles.peekFirst().unit == null){
            Unit TMP_Unit = new Unit(createdUnits.pop(), owner, ownedTiles.peekFirst());
            ownedTiles.peekFirst().unit = TMP_Unit;
            TMP_Unit.owner.playerUnits.add(TMP_Unit);
        }
        if(owner.inDepression){
            foodStock = foodStock + (wealth.food * Player.DEPRESSION_DEBUFF) - numberOfCitizen;
        }
        else{
            foodStock = foodStock + wealth.food - numberOfCitizen;
        }
        if(foodStock < 0){
            this.killCitizen();
        }
        if(foodStock >= foodToNewCitizen){
            numberOfCitizen++;
            numberOfFreeCitizen++;
        }
        owner.getTribute(wealth);
    }

    public double calculatePriceOfTile(Tile tileToBuy){
        if(tileToBuy.owner == null){
            int Y = Math.abs((tileToBuy.coordinates.y - ownedTiles.peekFirst().coordinates.y));
            int xDelta = Math.abs((tileToBuy.coordinates.x - ownedTiles.peekFirst().coordinates.x));
            int X = Math.min(xDelta, (tileToBuy.map.getWidth() - xDelta));
            PriceForTile = (X*X+Y*Y)*BASIC_TILE_PRICE;
            return PriceForTile;
        }
        return -1;
    }

    public void buyTile(Tile tileToBuy){
        if(PriceForTile >= 0){
            if(owner.money >= PriceForTile && tileToBuy.owner == null){
                owner.getMoney(-PriceForTile);
                tileToBuy.owner = this;
                LightPlay.addToPlayerVision(tileToBuy, this.owner);
            }
        }
    }

    public void buyBuilding(Building building){
        if(owner.money >= building.moneyCost){
            owner.getMoney(-PriceForTile);
            this.addBuilding(building);
            this.buildingCanBeBuilt.set(this.buildingsThatCanBeBuild.indexOf(building), false);
        }
    }

    public void buyUnit(UnitPattern pattern){
        if(owner.money >= pattern.moneyCost){
            owner.getMoney(-PriceForTile);
            this.addUnit(pattern);
        }
    }

    public void transferTile(Tile tileToTransfer){
        if(tileToTransfer.owner != null){
            if(tileToTransfer.owner.owner == owner){
                if(!ownedTiles.contains(tileToTransfer)){
                    tileToTransfer.owner.removeCitizenFromTile(tileToTransfer);
                    this.ownedTiles.add(tileToTransfer.owner.ownedTiles.remove(tileToTransfer.owner.ownedTiles.indexOf(tileToTransfer)));
                }
            }
        }
    }

}
