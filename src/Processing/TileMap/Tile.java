package Processing.TileMap;

import Processing.City.City;
import Processing.Player.Player;
import Processing.TileMap.TileUtils.*;
import Processing.Units.Unit;
import Processing.Utilits.Point;
import Processing.Utilits.Wealth;
import Processing.Utilits.WhereCanBe;
import Processing.Utilits.Wrapers.CreatableObject;

import java.io.Serializable;

public class Tile implements Serializable {
    static final long serialVersionUID = 111L;

    public Point coordinates;
    public GameMap map;
    public TypeOfLand typeOfLand = TypeOfLand.DeepOcean;
    public Resource resource = Resource.none;
    public TypeOfFlora typeOfFlora = TypeOfFlora.none;
    public TypeOfBuilding typeOfBuilding = TypeOfBuilding.none;

    public CreatableObject buildingInProcess = null;

    //public boolean isThereRoad = false;
    //boolean Bridge[] = new boolean[8];
    public RoadBridge roadAndBridges = new RoadBridge();
    public boolean river[] = new boolean[8];

    public Unit unit = null; //TODO INIT by NONE
    public City city = null; //TODO INIT by NONE
    public City owner = null; //TODO INIT by NONE
    public boolean isProcessedByPeople = false;

    public Wealth wealth = new Wealth(); //TODO must be empty
    public double ActionCost = 0;

    public Tile(Point coordinates, GameMap map){
        this.coordinates = coordinates;
        this.map = map;
    }

    public Tile() {

    }

    public void CalculateWealth(){
        this.wealth.toZero().dWealth(typeOfLand.wealth).dWealth(resource.wealth).dWealth(typeOfBuilding.wealth).dWealth(typeOfFlora.wealth);
    }

    public boolean isVisibleFor(Player player){
        if(player.VisionMap[this.coordinates.y][this.coordinates.x] > 0){
            return true;
        }
        return false;
    }

    public boolean isFogOfWarFor(Player player){
        return !player.OpenFOWMap[this.coordinates.y][this.coordinates.x];
    }


    public double getActionCost() {
        return ActionCost;
    }

    public GameMap getMap() {
        return map;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public TypeOfLand getTypeOfLand() {
        return typeOfLand;
    }

    public void setTypeOfLand(TypeOfLand typeOfLand) {
        if(WhereCanBe.FullCheck(this, this.typeOfFlora.whereCanExist)){
            this.typeOfLand = typeOfLand;
            return;
        }
        this.typeOfFlora = TypeOfFlora.none;
        this.typeOfLand = typeOfLand;
    }

    public Resource getResource() {
        return resource;
    }

    //allow weird set for future ability of terraform
    public void setResource(Resource resource) {
        if(WhereCanBe.FullCheck(this, this.resource.whereCanSpawn)){
            this.resource = resource;
        }
    }

    public TypeOfBuilding getTypeOfBuilding() {
        return typeOfBuilding;
    }

    public void setTypeOfBuilding(TypeOfBuilding typeOfBuilding) {
        if(WhereCanBe.FullCheck(this, this.typeOfBuilding.whereCanExist)){
            if(typeOfBuilding.destroyFlora){
                this.typeOfFlora = TypeOfFlora.none;
            }
            this.typeOfBuilding = typeOfBuilding;
        }
    }

    public TypeOfFlora getTypeOfFlora() {
        return typeOfFlora;
    }

    public void setTypeOfFlora(TypeOfFlora typeOfFlora) {
        if(WhereCanBe.FullCheck(this, this.typeOfFlora.whereCanExist)){
            this.typeOfFlora = typeOfFlora;
        }
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public Wealth getWealth() {
        return wealth;
    }

    public void setWealth(Wealth wealth) {
        this.wealth = wealth;
    }

    public boolean isThereRoad() {
        return roadAndBridges.roadAndBridges[Point.CENTER_NUM];
    }

    public void setThereRoad(boolean thereRoad) {
        roadAndBridges.roadAndBridges[Point.CENTER_NUM] = thereRoad;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getOwner() {
        return owner;
    }

    public void setOwner(City owner) {
        this.owner = owner;
    }

    public boolean isProcessedByPeople() {
        return isProcessedByPeople;
    }

    public void setProcessedByPeople(boolean processedByPeople) {
        isProcessedByPeople = processedByPeople;
    }

    public boolean isRiver(int direction){
        return  river[direction];
    }

    public void setRiver(int direction, boolean value){
        if((direction & 1) == 0){
            Tile TMP_Tile = map.getTile(this.coordinates.LookAt(Point.ALL_SIDES[direction]));
            if(TMP_Tile != null){
                TMP_Tile.river[direction+4%8] = value;
            }
            river[direction] = value;

            boolean setLeft = false;
            boolean setRight= false;
            if(value){
                if(river[(direction+6)%8]){
                    setLeft = true;
                }
                if(river[(direction+2)%8]){
                    setRight = true;
                }
                TMP_Tile = map.getTile(this.coordinates.LookAt(Point.ALL_SIDES[(direction+7)%8]));
                if(TMP_Tile != null){
                    if(TMP_Tile.isRiver((direction+4)%8)){
                        setLeft = true;
                    }
                }
                TMP_Tile = map.getTile(this.coordinates.LookAt(Point.ALL_SIDES[(direction+1)%8]));
                if(TMP_Tile != null){
                    if(TMP_Tile.isRiver((direction+4)%8)){
                        setRight = true;
                    }
                }
            }

            river[(direction+7)%8] = setLeft;
            river[(direction+1)%8] = setRight;

        }
    }

    public boolean isBridge(int direction) {
        return roadAndBridges.roadAndBridges[direction];
    }

    public void setBridge(int direction, boolean value){
        Tile TMP_Tile = map.getTile(this.coordinates.LookAt(Point.ALL_SIDES[direction]));
        if(TMP_Tile != null){
            TMP_Tile.roadAndBridges.roadAndBridges[direction+4%8] = value;
        }
        roadAndBridges.roadAndBridges[direction] = value;
    }

}
