package Processing.Player;

import Processing.Buildings.Building;
import Processing.Buildings.Job;
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
import java.util.*;

public class Player implements Serializable {
    static final long serialVersionUID = 5L;

    public Player(Game game, String race){
        this.race = race; //TODO init race tech and spawn settler of this race
        this.Game = game;
        this.Map = game.Map;
        this.VisionMap = new int[Map.getHeight()][Map.getWidth()];
        this.OpenFOWMap = new boolean[Map.getHeight()][Map.getWidth()];
        this.TechToResearch = (HashMap<String, ResearchCell>) ResearchCell.AllResearchCells.clone();
    }

    public static final double DEPRESSION_DEBUFF = 0.5;

    public boolean isBarbarianAI = false;
    public String race;

    public Game Game;
    public GameMap Map;
    public boolean myTurn = false;

    public double money = 50;
    public boolean inDepression = false;
    public double engineeringScience = 0;
    public double societyScience = 0;
    public double arcanumScience = 0;

    public double moneyPrevious = money;
    public double engineeringSciencePrevious = engineeringScience;
    public double societySciencePrevious = societyScience;
    public double arcanumSciencePrevious = arcanumScience;

    public double moneyIncome = 0;
    public double engineeringScienceIncome = 0;
    public double societyScienceIncome = 0;
    public double arcanumScienceIncome = 0;

    public int VisionMap[][];
    public boolean OpenFOWMap[][];

    public LinkedList<Resource> playerResources;
    public LinkedList<Integer> playerCountOfResources;

    public int colonizationSquare = 4;

    //List of units
    public LinkedList<Unit> playerUnits = new LinkedList<>();
    //List of cities
    public LinkedList<City> playerCities = new LinkedList<>();

    //Buffs   add if will be time
    //TypeOfBuilding   all researched upgrades of tile
    public LinkedHashMap<String, TwoTTT<TypeOfBuilding, Boolean>> availableUpgradesOfTile = new LinkedHashMap<>();
    //UnitPattern   all researched unit patterns
    public LinkedHashMap<String,UnitPattern> unitPatterns = new LinkedHashMap<>();
    //Buildings   all researched buildings
    public LinkedHashMap<String, Building> buildings = new LinkedHashMap<>();
    //Jobs   add if will be time
    public LinkedHashMap<String, Job> jobs = new LinkedHashMap<>(); //There must be ALL jobs to generate buildings

    public LinkedList<String> namesOfCities = new LinkedList<>();

    public BattleModifier battleModifier = BattleModifier.none;

    HashMap<String, ResearchCell> TechToResearch = new HashMap<>();

    HashMap<String, ResearchCell> AvailableToResearchEngineering = new HashMap<>();
    HashMap<String, ResearchCell> AvailableToResearchSociety = new HashMap<>();
    HashMap<String, ResearchCell> AvailableToResearchArcanum = new HashMap<>();

    HashMap<String, ResearchCell> ResearchedEngineering = new HashMap<>();
    HashMap<String, ResearchCell> ResearchedSociety = new HashMap<>();
    HashMap<String, ResearchCell> ResearchedArcanum = new HashMap<>();

    int numberOfResearchFromWhatCanChooseEngineering = 3;
    ArrayList<ResearchCell> toChooseEngineering = new ArrayList<>();
    int numberOfResearchFromWhatCanChooseSociety = 3;
    ArrayList<ResearchCell> toChooseSociety = new ArrayList<>();
    int numberOfResearchFromWhatCanChooseArcanum = 3;
    ArrayList<ResearchCell> toChooseArcanum = new ArrayList<>();

    ResearchCell chosenEngineering = null;
    double engineeringProgress;
    ResearchCell chosenSociety = null;
    double societyProgress;
    ResearchCell chosenArcanum = null;
    double arcanumProgress;



    public void getTribute(Wealth tribute){
        money +=  tribute.money;
        engineeringScience += tribute.engineeringScience;
        societyScience += tribute.societyScience;
        arcanumScience += tribute.arcanumScience;

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

        rememberPrevious();

        Iterator iterator = playerCities.iterator();
        while(iterator.hasNext()){
            ((City) iterator.next()).doEndTurn();
        }
        iterator = playerUnits.iterator();
        while(iterator.hasNext()){
            ((Unit) iterator.next()).doEndTurn();
        }

        calculateIncome();

        doResearch();

        if(money < 0){
            inDepression = true;
        }
        else{
            inDepression = false;
        }
        myTurn = false;

        Game.giveTurn();
    }

    private void rememberPrevious(){
        moneyPrevious = money;
        engineeringSciencePrevious = engineeringScience;
        societySciencePrevious = societyScience;
        arcanumSciencePrevious = arcanumScience;
    }

    private void calculateIncome(){
        this.moneyIncome = money - moneyPrevious;
        this.engineeringScienceIncome = engineeringScience - engineeringSciencePrevious;
        this.societyScienceIncome = societyScience - societySciencePrevious;
        this.arcanumScienceIncome = arcanumScience - arcanumSciencePrevious;
    }

    private void doResearch(){
        if(chosenEngineering != null){
            double giveTech = 0;
            if(engineeringScience <= engineeringScienceIncome*2){
                giveTech = engineeringScience;
            }
            else{
                giveTech = engineeringScienceIncome*2;
            }
            chosenEngineering.accumulatedResearch += giveTech;
            if(chosenEngineering.accumulatedResearch >= chosenEngineering.researchPointCost){
                chosenEngineering.researchToPlayer(this);
                engineeringScience = chosenEngineering.accumulatedResearch - chosenEngineering.researchPointCost;
                chosenEngineering = null;
            }
        }
        else{
            ResearchCell.generateNextResearchChooseToPlayer(this, ResearchCell.ENGINEERING_NUM);
        }

        if(chosenSociety != null){
            double giveTech = 0;
            if(societyScience <= societyScienceIncome*2){
                giveTech = societyScience;
            }
            else{
                giveTech = societyScienceIncome*2;
            }
            chosenSociety.accumulatedResearch += giveTech;
            if(chosenSociety.accumulatedResearch >= chosenSociety.researchPointCost){
                chosenSociety.researchToPlayer(this);
                societyScience = chosenSociety.accumulatedResearch - chosenSociety.researchPointCost;
                chosenSociety = null;
            }
        }
        else{
            ResearchCell.generateNextResearchChooseToPlayer(this, ResearchCell.SOCIETY_NUM);
        }

        if(chosenArcanum != null){
            double giveTech = 0;
            if(arcanumScience <= arcanumScienceIncome*2){
                giveTech = arcanumScience;
            }
            else{
                giveTech = arcanumScienceIncome*2;
            }
            chosenArcanum.accumulatedResearch += giveTech;
            if(chosenArcanum.accumulatedResearch >= chosenArcanum.researchPointCost){
                chosenArcanum.researchToPlayer(this);
                arcanumScience = chosenArcanum.accumulatedResearch - chosenArcanum.researchPointCost;
                chosenArcanum = null;
            }
        }
        else{
            ResearchCell.generateNextResearchChooseToPlayer(this, ResearchCell.ARCANUM_NUM);
        }
    }


    public void chooseResearch(int index, int Type){
        switch(Type){
            case ResearchCell.ENGINEERING_NUM:
                chosenEngineering = toChooseEngineering.get(index);
                break;
            case ResearchCell.SOCIETY_NUM:
                chosenSociety = toChooseSociety.get(index);
                break;
            case ResearchCell.ARCANUM_NUM:
                chosenArcanum = toChooseArcanum.get(index);
                break;
            default:
                break;
        }
    }

}
