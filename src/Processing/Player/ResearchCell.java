package Processing.Player;

import Processing.Buildings.Building;
import Processing.City.City;
import Processing.Game.Game;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Units.UnitPattern;
import Processing.Utilits.Wrapers.TwoTTT;

import java.util.*;

public class ResearchCell {

    static final public int ENGINEERING_NUM = 0;
    static final public int SOCIETY_NUM = 1;
    static final public int ARCANUM_NUM = 2;

    int typeOfResearch = 0;
    String nameOfResearch = "";
    String description = "";
    double researchPointCost = 0;
    double accumulatedResearch = 0;

    boolean canBeResearched = true;
    LinkedList<String> preRequisitesAND = new LinkedList<>();
    LinkedList<String> preRequisitesOR = new LinkedList<>();
    LinkedList<String> preRequisitesNOT = new LinkedList<>();

    LinkedList<Object> techGiven = new LinkedList<>();

    static public HashMap<String, ResearchCell> AllResearchCells = new HashMap<>();

    private ResearchCell(int Type, String nameOfResearch, String description, double researchPointCost, boolean canBeResearched,
                         String AND[], String OR[], String NOT[],
                         Object tech[]){

        this.typeOfResearch = Type;
        this.nameOfResearch = nameOfResearch;
        this.description = description;
        this.researchPointCost = researchPointCost;
        this.canBeResearched = canBeResearched;
        this.preRequisitesAND = new LinkedList<>(Arrays.stream(AND).toList());
        this.preRequisitesOR = new LinkedList<>(Arrays.stream(OR).toList());
        this.preRequisitesNOT = new LinkedList<>(Arrays.stream(NOT).toList());

        this.techGiven = new LinkedList<>(Arrays.stream(tech).toList());

    }

    public void openToTechPlayer(Player player){
        Iterator<Object> iterator = techGiven.iterator();
        while(iterator.hasNext()){
            Object researchedObject = iterator.next();
            if(researchedObject.getClass() == Building.class){
                player.buildings.put(((Building) researchedObject).name, (Building) researchedObject);
            }
            else if(researchedObject.getClass() == UnitPattern.class){
                player.unitPatterns.put(((UnitPattern) researchedObject).NameOfUnit, ((UnitPattern) researchedObject));
            }
            else if(researchedObject.getClass() == TypeOfBuilding.class){
                player.availableUpgradesOfTile.put(((TypeOfBuilding) researchedObject).elementName, new TwoTTT<>(((TypeOfBuilding) researchedObject), false));
            }
        }
        Iterator<City> cityIterator = player.playerCities.iterator();
        while(cityIterator.hasNext()){
            cityIterator.next().addBooleanToBuildings();
        }
    }

    public void researchToPlayer(Player player){
        this.openToTechPlayer(player);
        switch(this.typeOfResearch){
            case ENGINEERING_NUM:
                player.ResearchedEngineering.put(this.nameOfResearch, this);
                player.AvailableToResearchEngineering.remove(this.nameOfResearch);
                break;
            case SOCIETY_NUM:
                player.ResearchedSociety.put(this.nameOfResearch, this);
                player.AvailableToResearchSociety.remove(this.nameOfResearch);
                break;
            case ARCANUM_NUM:
                player.ResearchedArcanum.put(this.nameOfResearch, this);
                player.AvailableToResearchArcanum.remove(this.nameOfResearch);
                break;
            default:
                break;
        }
        player.TechToResearch.remove(this.nameOfResearch);
        modifyAvailableResearchToPlayer(player);
        generateNextResearchChooseToPlayer(player, this.typeOfResearch);
    }

    public static void modifyAvailableResearchToPlayer(Player player){
        Iterator<ResearchCell> iterator = player.TechToResearch.values().iterator();
        while(iterator.hasNext()){
            ResearchCell TMP_ResearchCell = iterator.next();

            if(!TMP_ResearchCell.canBeResearched){
                continue;
            }

            Iterator<String> AND = TMP_ResearchCell.preRequisitesAND.iterator();
            Iterator<String> OR = TMP_ResearchCell.preRequisitesOR.iterator();
            Iterator<String> NOT = TMP_ResearchCell.preRequisitesNOT.iterator();

            boolean CanBeResearched = true;

            switch(TMP_ResearchCell.typeOfResearch){
                case ENGINEERING_NUM:
                    if(player.AvailableToResearchEngineering.containsKey(TMP_ResearchCell.nameOfResearch) || player.ResearchedEngineering.containsKey(TMP_ResearchCell.nameOfResearch)) {
                        CanBeResearched = false;
                    }
                    break;
                case SOCIETY_NUM:
                    if(player.AvailableToResearchSociety.containsKey(TMP_ResearchCell.nameOfResearch) || player.ResearchedSociety.containsKey(TMP_ResearchCell.nameOfResearch)) {
                        CanBeResearched = false;
                    }
                    break;
                case ARCANUM_NUM:
                    if(player.AvailableToResearchArcanum.containsKey(TMP_ResearchCell.nameOfResearch) || player.ResearchedArcanum.containsKey(TMP_ResearchCell.nameOfResearch)) {
                        CanBeResearched = false;
                    }
                    break;
                default:
                    break;
            }
            if(!CanBeResearched){
                continue;
            }

            //AND check
            while(AND.hasNext()){
                String SSS = AND.next();
                if (!player.ResearchedEngineering.containsKey(SSS) && !player.ResearchedSociety.containsKey(SSS) && !player.ResearchedArcanum.containsKey(SSS)) {
                    CanBeResearched = false;
                    break;
                }
            }
            if(!CanBeResearched){
                continue;
            }
            //AND check

            //OR check
            boolean FindOrPrerequisite = false;
            while(OR.hasNext()){
                String SSS = OR.next();
                if(player.ResearchedEngineering.containsKey(SSS) || player.ResearchedSociety.containsKey(SSS) || player.ResearchedArcanum.containsKey(SSS)){
                    FindOrPrerequisite = true;
                    break;
                }
            }
            if(!FindOrPrerequisite){
                CanBeResearched = false;
                continue;
            }
            //OR check

            //NOT check
            while(NOT.hasNext()){
                String SSS = NOT.next();
                if(player.ResearchedEngineering.containsKey(SSS) || player.ResearchedSociety.containsKey(SSS) || player.ResearchedArcanum.containsKey(SSS)){
                    CanBeResearched = false;
                    break;
                }
            }
            if(!CanBeResearched){
                continue;
            }
            //NOT check

            switch(TMP_ResearchCell.typeOfResearch){
                case ENGINEERING_NUM:
                    player.AvailableToResearchEngineering.put(TMP_ResearchCell.nameOfResearch, TMP_ResearchCell);
                    break;
                case SOCIETY_NUM:
                    player.AvailableToResearchSociety.put(TMP_ResearchCell.nameOfResearch, TMP_ResearchCell);
                    break;
                case ARCANUM_NUM:
                    player.AvailableToResearchArcanum.put(TMP_ResearchCell.nameOfResearch, TMP_ResearchCell);
                    break;
                default:
                    break;
            }

        }
    }

    public static void generateNextResearchChooseToPlayer(Player player, int typeOfResearchNum){
        switch(typeOfResearchNum){
            case ENGINEERING_NUM:
                if(player.numberOfResearchFromWhatCanChooseEngineering >= player.AvailableToResearchEngineering.size()){
                    player.toChooseEngineering = new ArrayList<>(player.AvailableToResearchEngineering.values());
                }
                else{
                    player.toChooseEngineering = new ArrayList<>();
                    for(int i = 0; i < player.numberOfResearchFromWhatCanChooseEngineering; i++){
                        int randomIndex = Game.RandomGen.nextInt(0, player.AvailableToResearchEngineering.size());
                        Iterator<ResearchCell> iterator = player.AvailableToResearchEngineering.values().iterator();
                        for(int j = 0; j < randomIndex; j++){
                            iterator.next();
                        }
                        ResearchCell TMP_ResearchCell = iterator.next();
                        player.toChooseEngineering.add(TMP_ResearchCell);
                        player.AvailableToResearchEngineering.remove(TMP_ResearchCell.nameOfResearch);
                    }
                    for(int i = 0; i < player.numberOfResearchFromWhatCanChooseEngineering; i++){
                        player.AvailableToResearchEngineering.put(player.toChooseEngineering.get(i).nameOfResearch, player.toChooseEngineering.get(i));
                    }
                }
                break;
            case SOCIETY_NUM:
                if(player.numberOfResearchFromWhatCanChooseSociety >= player.AvailableToResearchSociety.size()){
                    player.toChooseSociety = new ArrayList<>(player.AvailableToResearchSociety.values());
                }
                else{
                    player.toChooseSociety = new ArrayList<>();
                    for(int i = 0; i < player.numberOfResearchFromWhatCanChooseSociety; i++){
                        int randomIndex = Game.RandomGen.nextInt(0, player.AvailableToResearchSociety.size());
                        Iterator<ResearchCell> iterator = player.AvailableToResearchSociety.values().iterator();
                        for(int j = 0; j < randomIndex; j++){
                            iterator.next();
                        }
                        ResearchCell TMP_ResearchCell = iterator.next();
                        player.toChooseSociety.add(TMP_ResearchCell);
                        player.AvailableToResearchSociety.remove(TMP_ResearchCell.nameOfResearch);
                    }
                    for(int i = 0; i < player.numberOfResearchFromWhatCanChooseSociety; i++){
                        player.AvailableToResearchSociety.put(player.toChooseSociety.get(i).nameOfResearch, player.toChooseSociety.get(i));
                    }
                }
                break;
            case ARCANUM_NUM:
                if(player.numberOfResearchFromWhatCanChooseArcanum >= player.AvailableToResearchArcanum.size()){
                    player.toChooseArcanum = new ArrayList<>(player.AvailableToResearchArcanum.values());
                }
                else{
                    player.toChooseArcanum = new ArrayList<>();
                    for(int i = 0; i < player.numberOfResearchFromWhatCanChooseArcanum; i++){
                        int randomIndex = Game.RandomGen.nextInt(0, player.AvailableToResearchArcanum.size());
                        Iterator<ResearchCell> iterator = player.AvailableToResearchArcanum.values().iterator();
                        for(int j = 0; j < randomIndex; j++){
                            iterator.next();
                        }
                        ResearchCell TMP_ResearchCell = iterator.next();
                        player.toChooseArcanum.add(TMP_ResearchCell);
                        player.AvailableToResearchArcanum.remove(TMP_ResearchCell.nameOfResearch);
                    }
                    for(int i = 0; i < player.numberOfResearchFromWhatCanChooseArcanum; i++){
                        player.AvailableToResearchArcanum.put(player.toChooseArcanum.get(i).nameOfResearch, player.toChooseArcanum.get(i));
                    }
                }
                break;
            default:
                break;
        }
    }

    public static void initResearchTree(){

    }

    private void initCell(){
        ResearchCell.AllResearchCells.put(this.nameOfResearch, this);
    }

}
