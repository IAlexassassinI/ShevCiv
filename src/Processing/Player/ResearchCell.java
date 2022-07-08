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
    public double researchPointCost = 0;
    public double accumulatedResearch = 0;

    boolean canBeResearched = true;
    LinkedList<String> preRequisitesAND = new LinkedList<>();
    LinkedList<String> preRequisitesOR = new LinkedList<>();
    LinkedList<String> preRequisitesNOT = new LinkedList<>();

    public LinkedList<Object> techGiven = new LinkedList<>();

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
        ResearchCell HumanCulture = new ResearchCell(
                SOCIETY_NUM,
                "HumanCulture",
                "descr",
                0,
                false,
                new String[]{}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanSettler.NameOfUnit),
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanWorker.NameOfUnit),
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanMilitia.NameOfUnit),
                }
        ).initCell();

        ResearchCell ElvenCulture = new ResearchCell(
                SOCIETY_NUM,
                "ElvenCulture",
                "descr",
                0,
                false,
                new String[]{}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenSettler.NameOfUnit),
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenWorker.NameOfUnit),
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenMilitia.NameOfUnit),
                }
        ).initCell();

        ResearchCell DwarfCulture = new ResearchCell(
                SOCIETY_NUM,
                "DwarfCulture",
                "descr",
                0,
                false,
                new String[]{}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfSettler.NameOfUnit),
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfWorker.NameOfUnit),
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfMilitia.NameOfUnit),
                }
        ).initCell();

        ResearchCell DemonCulture = new ResearchCell(
                SOCIETY_NUM,
                "DwarfCulture",
                "descr",
                0,
                false,
                new String[]{}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DemonSettler.NameOfUnit),
                        UnitPattern.AllUnitPattern.get(UnitPattern.DemonSlave.NameOfUnit),
                        UnitPattern.AllUnitPattern.get(UnitPattern.DemonMilitia.NameOfUnit),
                }
        ).initCell();


        ResearchCell Pottery = new ResearchCell(
                SOCIETY_NUM,
                "Pottery",
                "descr",
                10,
                true,
                new String[]{}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Granary.name)
                }
        ).initCell();

        ResearchCell AnimalHusbandry = new ResearchCell(
                SOCIETY_NUM,
                "AnimalHusbandry",
                "descr",
                10,
                true,
                new String[]{}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Stable.name),
                        Building.AllBuildings.get(Building.Caravansary.name)
                }
        ).initCell();

        ResearchCell HorseRiding = new ResearchCell(
                SOCIETY_NUM,
                "HorseRiding",
                "descr",
                10,
                true,
                new String[]{}, //AND
                new String[]{}, //OR
                new String[]{DwarfCulture.nameOfResearch, DemonCulture.nameOfResearch}, //NOT
                new Object[]{

                }
        ).initCell();

        ResearchCell WoodWorking = new ResearchCell(
                ENGINEERING_NUM,
                "WoodWorking",
                "descr",
                10,
                true,
                new String[]{}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        TypeOfBuilding.AllTypeOfBuilding.get(TypeOfBuilding.Sawmill.elementName)
                }
        ).initCell();

        ResearchCell Mining = new ResearchCell(
                ENGINEERING_NUM,
                "Mining",
                "descr",
                10,
                true,
                new String[]{}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        TypeOfBuilding.AllTypeOfBuilding.get(TypeOfBuilding.Mine.elementName)
                }
        ).initCell();

        ResearchCell BowMaking = new ResearchCell(
                ENGINEERING_NUM,
                "BowMaking",
                "descr",
                35,
                true,
                new String[]{WoodWorking.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{DwarfCulture.nameOfResearch, DemonCulture.nameOfResearch}, //NOT
                new Object[]{

                }
        ).initCell();

        ResearchCell ElvenArchery = new ResearchCell(
                SOCIETY_NUM,
                "ElvenArchery",
                "descr",
                45,
                true,
                new String[]{BowMaking.nameOfResearch, ElvenCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenArcher.NameOfUnit)
                }
        ).initCell();

        ResearchCell HumanArchery = new ResearchCell(
                SOCIETY_NUM,
                "HumanArchery",
                "descr",
                45,
                true,
                new String[]{BowMaking.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanArcher.NameOfUnit)
                }
        ).initCell();

        ResearchCell Writing = new ResearchCell(
                ARCANUM_NUM,
                "Writing",
                "descr",
                15,
                true,
                new String[]{Pottery.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Library.name)
                }
        ).initCell();

        ResearchCell EarlyMagic = new ResearchCell(
                ARCANUM_NUM,
                "EarlyMagic",
                "descr",
                25,
                true,
                new String[]{Writing.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{DwarfCulture.nameOfResearch, HumanCulture.nameOfResearch}, //NOT
                new Object[]{

                }
        ).initCell();

        ResearchCell FireBallSpell = new ResearchCell(
                ARCANUM_NUM,
                "FireBallSpell",
                "descr",
                25,
                true,
                new String[]{EarlyMagic.nameOfResearch, DemonCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.Magog.NameOfUnit)
                }
        ).initCell();

        ResearchCell Masonry = new ResearchCell(
                ENGINEERING_NUM,
                "Masonry",
                "descr",
                15,
                true,
                new String[]{Mining.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.StoneWorks.name)
                }
        ).initCell();

        ResearchCell HammerOfTheFaith = new ResearchCell(
                SOCIETY_NUM,
                "HammerOfTheFaith",
                "descr",
                15,
                true,
                new String[]{Masonry.nameOfResearch, DwarfCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfHammerman.NameOfUnit)
                }
        ).initCell();

        ResearchCell Mathematics = new ResearchCell(
                ARCANUM_NUM,
                "Mathematics",
                "descr",
                30,
                true,
                new String[]{Writing.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Courthouse.name)
                }
        ).initCell();

        ResearchCell BronzeWorking = new ResearchCell(
                ENGINEERING_NUM,
                "BronzeWorking",
                "descr",
                15,
                true,
                new String[]{Masonry.nameOfResearch, Writing.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Armory.name)
                }
        ).initCell();

        ResearchCell PointySticks = new ResearchCell(
                SOCIETY_NUM,
                "PointySticks",
                "descr",
                15,
                true,
                new String[]{BronzeWorking.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanSpearman.NameOfUnit)
                }
        ).initCell();

        ResearchCell HumanCavalry = new ResearchCell(
                SOCIETY_NUM,
                "HumanCavalry",
                "descr",
                15,
                true,
                new String[]{PointySticks.nameOfResearch, HorseRiding.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanCavalry.NameOfUnit)
                }
        ).initCell();

        ResearchCell IronWorking = new ResearchCell(
                ENGINEERING_NUM,
                "IronWorking",
                "descr",
                30,
                true,
                new String[]{BronzeWorking.nameOfResearch, Mathematics.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Forge.name)
                }
        ).initCell();

        ResearchCell DemonSwords = new ResearchCell(
                ENGINEERING_NUM,
                "DemonSwords",
                "descr",
                30,
                true,
                new String[]{IronWorking.nameOfResearch, DemonCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DemonSwordsman.NameOfUnit)
                }
        ).initCell();

        ResearchCell ElvenCavalry = new ResearchCell(
                ENGINEERING_NUM,
                "ElvenCavalry",
                "descr",
                30,
                true,
                new String[]{HorseRiding.nameOfResearch, ElvenArchery.nameOfResearch, ElvenCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenCavalry.NameOfUnit)
                }
        ).initCell();

        ResearchCell HellHoundBreeding = new ResearchCell(
                SOCIETY_NUM,
                "HellHoundBreeding",
                "descr",
                30,
                true,
                new String[]{HorseRiding.nameOfResearch, EarlyMagic.nameOfResearch, DemonCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.Cerberus.NameOfUnit)
                }
        ).initCell();

        ResearchCell Engineering = new ResearchCell(
                ENGINEERING_NUM,
                "Engineering",
                "descr",
                45,
                true,
                new String[]{WoodWorking.nameOfResearch, IronWorking.nameOfResearch, Mathematics.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Aqueduct.name),
                        Building.AllBuildings.get(Building.WindMill.name)
                }
        ).initCell();

        ResearchCell ShoreShips = new ResearchCell(
                ENGINEERING_NUM,
                "ShoreShips",
                "descr",
                45,
                true,
                new String[]{Engineering.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanTrireme.NameOfUnit)
                }
        ).initCell();

        ResearchCell Catapult = new ResearchCell(
                ENGINEERING_NUM,
                "Catapult",
                "descr",
                45,
                true,
                new String[]{Engineering.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanCatapult.NameOfUnit)
                }
        ).initCell();

        ResearchCell Currency = new ResearchCell(
                SOCIETY_NUM,
                "Currency",
                "descr",
                45,
                true,
                new String[]{Mathematics.nameOfResearch, Pottery.nameOfResearch, AnimalHusbandry.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Market.name),
                        Building.AllBuildings.get(Building.Mint.name)
                }
        ).initCell();

        ResearchCell Philosophy = new ResearchCell(
                SOCIETY_NUM,
                "Philosophy",
                "descr",
                45,
                true,
                new String[]{Writing.nameOfResearch, Masonry.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Amphitheater.name),
                        Building.AllBuildings.get(Building.Shrine.name)
                }
        ).initCell();

        ResearchCell Theology = new ResearchCell(
                ARCANUM_NUM,
                "Theology",
                "descr",
                45,
                true,
                new String[]{Philosophy.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Temple.name),
                        Building.AllBuildings.get(Building.Garden.name)
                }
        ).initCell();

        ResearchCell Guilds = new ResearchCell(
                ARCANUM_NUM,
                "Guilds",
                "descr",
                60,
                true,
                new String[]{Currency.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Bazaar.name)
                }
        ).initCell();

        ResearchCell Manufacture = new ResearchCell(
                ENGINEERING_NUM,
                "Manufacture",
                "descr",
                60,
                true,
                new String[]{Guilds.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.HumanManufacture.name)
                }
        ).initCell();

        ResearchCell ElvenTrade = new ResearchCell(
                SOCIETY_NUM,
                "ElvenTrade",
                "descr",
                60,
                true,
                new String[]{Guilds.nameOfResearch, ElvenCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.ElvenBazaar.name)
                }
        ).initCell();

        ResearchCell MetalCasting = new ResearchCell(
                ENGINEERING_NUM,
                "MetalCasting",
                "descr",
                60,
                true,
                new String[]{IronWorking.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Workshop.name)
                }
        ).initCell();

        ResearchCell BladeDance = new ResearchCell(
                SOCIETY_NUM,
                "BladeDance",
                "descr",
                75,
                true,
                new String[]{Philosophy.nameOfResearch, MetalCasting.nameOfResearch, ElvenCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenBladeDancer.NameOfUnit)
                }
        ).initCell();

        ResearchCell Knights = new ResearchCell(
                SOCIETY_NUM,
                "Knights",
                "descr",
                75,
                true,
                new String[]{MetalCasting.nameOfResearch, Theology.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanSwordsman.NameOfUnit)
                }
        ).initCell();

        ResearchCell DwarfSwords = new ResearchCell(
                SOCIETY_NUM,
                "DwarfSwords",
                "descr",
                75,
                true,
                new String[]{MetalCasting.nameOfResearch, DwarfCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfSwordsman.NameOfUnit)
                }
        ).initCell();

        ResearchCell Education = new ResearchCell(
                ENGINEERING_NUM,
                "Education",
                "descr",
                90,
                true,
                new String[]{Philosophy.nameOfResearch, Mathematics.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.University.name)
                }
        ).initCell();

        ResearchCell Medicine = new ResearchCell(
                ENGINEERING_NUM,
                "Medicine",
                "descr",
                75,
                true,
                new String[]{Theology.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Hospital.name)
                }
        ).initCell();

        ResearchCell Machinery = new ResearchCell(
                ENGINEERING_NUM,
                "Machinery",
                "descr",
                90,
                true,
                new String[]{Engineering.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Factory.name)
                }
        ).initCell();

        ResearchCell CompositeBow = new ResearchCell(
                ENGINEERING_NUM,
                "CompositeBow",
                "descr",
                90,
                true,
                new String[]{Machinery.nameOfResearch, ElvenCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenSharpshooter.NameOfUnit)
                }
        ).initCell();

        ResearchCell Physic = new ResearchCell(
                ENGINEERING_NUM,
                "Physic",
                "descr",
                90,
                true,
                new String[]{Mathematics.nameOfResearch, Philosophy.nameOfResearch, Engineering.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{

                }
        ).initCell();

        ResearchCell GriffinCavalry = new ResearchCell(
                SOCIETY_NUM,
                "GriffinCavalry",
                "descr",
                90,
                true,
                new String[]{Physic.nameOfResearch, Knights.nameOfResearch, HorseRiding.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                    UnitPattern.AllUnitPattern.get(UnitPattern.GriffinRider.NameOfUnit)
                }
        ).initCell();

        ResearchCell HawkRiders = new ResearchCell(
                SOCIETY_NUM,
                "HawkRiders",
                "descr",
                90,
                true,
                new String[]{Physic.nameOfResearch, CompositeBow.nameOfResearch, ElvenCulture.nameOfResearch, HorseRiding.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.GriffinRider.NameOfUnit)
                }
        ).initCell();

        ResearchCell Astronomy = new ResearchCell(
                ENGINEERING_NUM,
                "Astronomy",
                "descr",
                105,
                true,
                new String[]{Physic.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Observatory.name)
                }
        ).initCell();

        ResearchCell HighMagic = new ResearchCell(
                ARCANUM_NUM,
                "HighMagic",
                "descr",
                105,
                true,
                new String[]{Astronomy.nameOfResearch, EarlyMagic.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{

                }
        ).initCell();

        ResearchCell Palace = new ResearchCell(
                ARCANUM_NUM,
                "Palace",
                "descr",
                115,
                true,
                new String[]{HighMagic.nameOfResearch, DemonCulture.nameOfResearch, Engineering.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.DemonPalace.name)
                }
        ).initCell();

        ResearchCell Succubus = new ResearchCell(
                ARCANUM_NUM,
                "Succubus",
                "descr",
                105,
                true,
                new String[]{HighMagic.nameOfResearch, DemonCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DemonSuccubi.NameOfUnit)
                }
        ).initCell();

        ResearchCell KrakenAwakening = new ResearchCell(
                ARCANUM_NUM,
                "KrakenAwakening",
                "descr",
                105,
                true,
                new String[]{HighMagic.nameOfResearch, DemonCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.Kraken.NameOfUnit)
                }
        ).initCell();

        ResearchCell Druids = new ResearchCell(
                ARCANUM_NUM,
                "Druids",
                "descr",
                105,
                true,
                new String[]{HighMagic.nameOfResearch, ElvenCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenMage.NameOfUnit)
                }
        ).initCell();

        ResearchCell Carrier = new ResearchCell(
                ENGINEERING_NUM,
                "Carrier",
                "descr",
                105,
                true,
                new String[]{Astronomy.nameOfResearch, ElvenCulture.nameOfResearch, Engineering.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenCarrier.NameOfUnit)
                }
        ).initCell();

        ResearchCell DeepOceanShips = new ResearchCell(
                ENGINEERING_NUM,
                "DeepOceanShips",
                "descr",
                105,
                true,
                new String[]{Astronomy.nameOfResearch, HumanCulture.nameOfResearch, Engineering.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanCaravel.NameOfUnit)
                }
        ).initCell();

        ResearchCell Acoustic = new ResearchCell(
                ENGINEERING_NUM,
                "Acoustic",
                "descr",
                105,
                true,
                new String[]{Philosophy.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.OperaHouse.name)
                }
        ).initCell();

        ResearchCell Banking = new ResearchCell(
                ENGINEERING_NUM,
                "Banking",
                "descr",
                105,
                true,
                new String[]{Guilds.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Bank.name)
                }
        ).initCell();

        ResearchCell GoldStandard = new ResearchCell(
                SOCIETY_NUM,
                "GoldStandard",
                "descr",
                105,
                true,
                new String[]{Banking.nameOfResearch, DwarfCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.DwarfTreasury.name)
                }
        ).initCell();

        ResearchCell Chemistry = new ResearchCell(
                ENGINEERING_NUM,
                "Chemistry",
                "descr",
                105,
                true,
                new String[]{Physic.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{

                }
        ).initCell();

        ResearchCell Gunpowder = new ResearchCell(
                ENGINEERING_NUM,
                "Gunpowder",
                "descr",
                120,
                true,
                new String[]{Chemistry.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{ElvenCulture.nameOfResearch}, //NOT
                new Object[]{

                }
        ).initCell();

        ResearchCell Musket = new ResearchCell(
                ENGINEERING_NUM,
                "HumanArchery",
                "descr",
                120,
                true,
                new String[]{Gunpowder.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                    UnitPattern.AllUnitPattern.get(UnitPattern.HumanMusketeer.NameOfUnit)
                }
        ).initCell();

        ResearchCell Arkebuse = new ResearchCell(
                ENGINEERING_NUM,
                "Arkebuse",
                "descr",
                120,
                true,
                new String[]{Gunpowder.nameOfResearch, DwarfCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfMusketeer.NameOfUnit)
                }
        ).initCell();

        ResearchCell Economic = new ResearchCell(
                ENGINEERING_NUM,
                "Economic",
                "descr",
                120,
                true,
                new String[]{Banking.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.StockExchange.name)
                }
        ).initCell();

        ResearchCell Metallurgy = new ResearchCell(
                ENGINEERING_NUM,
                "Metallurgy",
                "descr",
                120,
                true,
                new String[]{MetalCasting.nameOfResearch, Physic.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{

                }
        ).initCell();

        ResearchCell Scorpions = new ResearchCell(
                ENGINEERING_NUM,
                "Scorpions",
                "descr",
                120,
                true,
                new String[]{Metallurgy.nameOfResearch, ElvenCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.ElvenScorpion.NameOfUnit)
                }
        ).initCell();

        ResearchCell HumanMortarsSquad = new ResearchCell(
                SOCIETY_NUM,
                "HumanMortarsSquad",
                "descr",
                120,
                true,
                new String[]{Metallurgy.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanMortar.NameOfUnit)
                }
        ).initCell();

        ResearchCell DwarfMortarsSquad = new ResearchCell(
                SOCIETY_NUM,
                "DwarfMortarsSquad",
                "descr",
                120,
                true,
                new String[]{Metallurgy.nameOfResearch, DwarfCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfMortar.NameOfUnit)
                }
        ).initCell();

        ResearchCell HellCannon = new ResearchCell(
                SOCIETY_NUM,
                "DwarfMortarsSquad",
                "descr",
                120,
                true,
                new String[]{Metallurgy.nameOfResearch, DemonCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfMortar.NameOfUnit)
                }
        ).initCell();

        ResearchCell ScientificTheory = new ResearchCell(
                ENGINEERING_NUM,
                "ScientificTheory",
                "descr",
                150,
                true,
                new String[]{Education.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.PublicSchool.name)
                }
        ).initCell();

        ResearchCell Zeppelins = new ResearchCell(
                ENGINEERING_NUM,
                "Zeppelins",
                "descr",
                150,
                true,
                new String[]{ScientificTheory.nameOfResearch, DwarfCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.Zeppelin.NameOfUnit)
                }
        ).initCell();

        ResearchCell Industrialization = new ResearchCell(
                ENGINEERING_NUM,
                "Industrialization",
                "descr",
                135,
                true,
                new String[]{Metallurgy.nameOfResearch, Engineering.nameOfResearch, Education.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.CoalPlant.name)
                }
        ).initCell();

        ResearchCell HellPortal = new ResearchCell(
                ARCANUM_NUM,
                "HellPortal",
                "descr",
                135,
                true,
                new String[]{Industrialization.nameOfResearch, HighMagic.nameOfResearch, DemonCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.ArchDevil.NameOfUnit)
                }
        ).initCell();

        ResearchCell SteamTanks = new ResearchCell(
                ENGINEERING_NUM,
                "SteamTanks",
                "descr",
                135,
                true,
                new String[]{Industrialization.nameOfResearch, HumanCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.HumanTank.NameOfUnit)
                }
        ).initCell();

        ResearchCell HeavyTanks = new ResearchCell(
                ENGINEERING_NUM,
                "HeavyTanks",
                "descr",
                135,
                true,
                new String[]{Industrialization.nameOfResearch, DwarfCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfTank.NameOfUnit)
                }
        ).initCell();

        ResearchCell Tourism = new ResearchCell(
                ENGINEERING_NUM,
                "Tourism",
                "descr",
                135,
                true,
                new String[]{Acoustic.nameOfResearch, Guilds.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.Hotel.name),
                        Building.AllBuildings.get(Building.Museum.name)
                }
        ).initCell();

        ResearchCell Biology = new ResearchCell(
                ENGINEERING_NUM,
                "Biology",
                "descr",
                210,
                true,
                new String[]{Medicine.nameOfResearch, ScientificTheory.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        Building.AllBuildings.get(Building.MedicalLab.name),
                        Building.AllBuildings.get(Building.ResearchLab.name)
                }
        ).initCell();

        ResearchCell Rockets = new ResearchCell(
                ENGINEERING_NUM,
                "Rockets",
                "descr",
                210,
                true,
                new String[]{Biology.nameOfResearch, DwarfCulture.nameOfResearch}, //AND
                new String[]{}, //OR
                new String[]{}, //NOT
                new Object[]{
                        UnitPattern.AllUnitPattern.get(UnitPattern.DwarfJavelin.NameOfUnit)
                }
        ).initCell();

    }

    private ResearchCell initCell(){
        ResearchCell.AllResearchCells.put(this.nameOfResearch, this);
        return this;
    }

    @Override
    public String toString() {
        return nameOfResearch;
    }
}
