package Processing.Player;

import Processing.Buildings.Building;
import Processing.City.City;
import Processing.Game.Game;
import Processing.TileMap.GameMap;
import Processing.TileMap.TileUtils.Resource;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Units.BattleModifier;
import Processing.Units.Unit;
import Processing.Units.UnitPattern;
import Processing.Utilits.Wealth;
import Processing.Utilits.Wrapers.TwoTTT;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class Player implements Serializable {
    static final long serialVersionUID = 5L;

    public Player(Game game){
        this.Game = game;
        this.Map = game.Map;
        this.VisionMap = new int[Map.getHeight()][Map.getWidth()];
        this.OpenFOWMap = new boolean[Map.getHeight()][Map.getWidth()];
    }

    public static final double DEPRESSION_DEBUFF = 0.5;

    public Game Game;
    public GameMap Map;
    public boolean myTurn = false;

    public double money;
    public boolean inDepression = false;
    public double engineeringScience;
    public double societyScience;
    public double arcanumScience;

    public int VisionMap[][];
    public boolean OpenFOWMap[][];

    public LinkedList<Resource> playerResources;
    public LinkedList<Integer> playerCountOfResources;

    public int colonizationSquare = 4;

    //List of units
    public LinkedList<Unit> playerUnits;
    //List of cities
    public LinkedList<City> playerCities;

    //Buffs   add if will be time
    //TypeOfBuilding   all researched upgrades of tile
    public LinkedList<TwoTTT<TypeOfBuilding, Boolean>> availableUpgradesOfTile = new LinkedList<>();
    //UnitPattern   all researched unit patterns
    public LinkedList<UnitPattern> unitPatterns = new LinkedList<>();
    //Buildings   all researched buildings
    public LinkedList<Building> buildings = new LinkedList<>();
    //Jobs   add if will be time

    public LinkedList<String> namesOfCities = new LinkedList<>();

    public BattleModifier battleModifier = BattleModifier.none;

    public void getTribute(Wealth tribute){
        if(inDepression){
            if(tribute.money > 0){
                money +=  tribute.money * DEPRESSION_DEBUFF;
            }
            else{
                money +=  tribute.money;
            }
            engineeringScience += tribute.engineeringScience/2;
            societyScience += tribute.societyScience/2;
            arcanumScience += tribute.arcanumScience/2;
        }else{
            money +=  tribute.money;
            engineeringScience += tribute.engineeringScience;
            societyScience += tribute.societyScience;
            arcanumScience += tribute.arcanumScience;
        }

    }

    public void getMoney(double tribute){
        money +=  tribute;
    }

    public void doEndTurn(){
        if(inDepression){
            battleModifier = BattleModifier.half;
        }
        else{
            battleModifier = BattleModifier.none;
        }
        Iterator iterator = playerCities.iterator();
        while(iterator.hasNext()){
            ((City) iterator.next()).doEndTurn();
        }
        iterator = playerUnits.iterator();
        while(iterator.hasNext()){
            ((Unit) iterator.next()).doEndTurn();
        }
        if(money < 0){
            inDepression = true;
        }
        else{
            inDepression = false;
        }
        myTurn = false;

        Game.giveTurn();
    }

}
