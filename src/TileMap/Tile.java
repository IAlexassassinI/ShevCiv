package TileMap;

import City.City;
import Player.Player;
import TileMap.TileUtils.*;
import Units.Unit;
import Utilits.Point;

import java.io.Serializable;
import java.util.ArrayList;

public class Tile implements Serializable {
    static final long serialVersionUID = 111L;

    Point coordinates;
    GameMap map;
    Height height = Height.DeepOcean;
    TypeOfLand typeOfLand = TypeOfLand.Green;
    Resource resource = Resource.none;
    TypeOfBuilding typeOfBuilding = TypeOfBuilding.none;
    TypeOfFlora typeOfFlora = TypeOfFlora.none;

    boolean isThereRoad = false;

    Unit unit = null; //TODO INIT by NONE
    City city = null; //TODO INIT by NONE
    City owner = null; //TODO INIT by NONE
    boolean isProcessedByPeople = false;

    ArrayList<Player> isVisibleFor = new ArrayList<>();
    ArrayList<Player> isFogOfWarFor = new ArrayList<>();

    boolean isRiverTop = false;
    boolean isRiverRight = false;
    boolean isRiverLeft = false;
    boolean isRiverBottom = false;

    boolean isBridgeTop = false;
    boolean isBridgeRight = false;
    boolean isBridgeLeft = false;
    boolean isBridgeBottom = false;

    Tile(Point coordinates, GameMap map){
        this.coordinates = coordinates;
        this.map = map;
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

    public Height getHeight() {
        return height;
    }

    public void setHeight(Height height) {
        this.height = height;
    }

    public TypeOfLand getTypeOfLand() {
        return typeOfLand;
    }

    public void setTypeOfLand(TypeOfLand typeOfLand) {
        this.typeOfLand = typeOfLand;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public TypeOfBuilding getTypeOfBuilding() {
        return typeOfBuilding;
    }

    //TODO Maybe need something more
    public void setTypeOfBuilding(TypeOfBuilding typeOfBuilding) {
        this.typeOfBuilding = typeOfBuilding;
    }

    public TypeOfFlora getTypeOfFlora() {
        return typeOfFlora;
    }

    //TODO Maybe need something more
    public void setTypeOfFlora(TypeOfFlora typeOfFlora) {
        this.typeOfFlora = typeOfFlora;
    }

    public boolean isThereRoad() {
        return isThereRoad;
    }

    public void setThereRoad(boolean thereRoad) {
        isThereRoad = thereRoad;
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

    public ArrayList<Player> getIsVisibleFor() {
        return isVisibleFor;
    }

    public void setIsVisibleFor(ArrayList<Player> isVisibleFor) {
        this.isVisibleFor = isVisibleFor;
    }

    public ArrayList<Player> getIsFogOfWarFor() {
        return isFogOfWarFor;
    }

    public void setIsFogOfWarFor (ArrayList<Player> isFogOfWarFor) {
        this.isFogOfWarFor = isFogOfWarFor;
    }

    public boolean isRiverTop() {
        return isRiverTop;
    }

    public void setRiverTop(boolean riverTop) {
        Tile TMP_Tile = map.getTile(coordinates.LookAt(0,-1));
        if(TMP_Tile != null){
            TMP_Tile.isRiverBottom = riverTop;
        }
        isRiverTop = riverTop;
    }

    public boolean isRiverRight() {
        return isRiverRight;
    }

    public void setRiverRight(boolean riverRight) {
        Tile TMP_Tile = map.getTile(coordinates.LookAt(1,0));
        if(TMP_Tile != null){
            TMP_Tile.isRiverLeft = riverRight;
        }
        isRiverRight = riverRight;
    }

    public boolean isRiverLeft() {
        return isRiverLeft;
    }

    public void setRiverLeft(boolean riverLeft) {
        Tile TMP_Tile = map.getTile(coordinates.LookAt(-1,0));
        if(TMP_Tile != null){
            TMP_Tile.isRiverRight = riverLeft;
        }
        isRiverLeft = riverLeft;
    }

    public boolean isRiverBottom() {
        return isRiverBottom;
    }

    public void setRiverBottom(boolean riverBottom) {
        Tile TMP_Tile = map.getTile(coordinates.LookAt(0,1));
        if(TMP_Tile != null){
            TMP_Tile.isRiverTop = riverBottom;
        }
        isRiverBottom = riverBottom;
    }

    public boolean isBridgeTop() {
        return isBridgeTop;
    }

    public void setBridgeTop(boolean bridgeTop) {
        Tile TMP_Tile = map.getTile(coordinates.LookAt(0,-1));
        if(TMP_Tile != null){
            TMP_Tile.isBridgeBottom = bridgeTop;
        }
        isBridgeTop = bridgeTop;
    }

    public boolean isBridgeRight() {
        return isBridgeRight;
    }

    public void setBridgeRight(boolean bridgeRight) {
        Tile TMP_Tile = map.getTile(coordinates.LookAt(1,0));
        if(TMP_Tile != null){
            TMP_Tile.isBridgeLeft = bridgeRight;
        }
        isBridgeRight = bridgeRight;
    }

    public boolean isBridgeLeft() {
        return isBridgeLeft;
    }

    public void setBridgeLeft(boolean bridgeLeft) {
        Tile TMP_Tile = map.getTile(coordinates.LookAt(-1,0));
        if(TMP_Tile != null){
            TMP_Tile.isBridgeRight = bridgeLeft;
        }
        isBridgeLeft = bridgeLeft;
    }

    public boolean isBridgeBottom() {
        return isBridgeBottom;
    }

    public void setBridgeBottom(boolean bridgeBottom) {
        Tile TMP_Tile = map.getTile(coordinates.LookAt(0,1));
        if(TMP_Tile != null){
            TMP_Tile.isBridgeTop = bridgeBottom;
        }
        isBridgeBottom = bridgeBottom;
    }
}
