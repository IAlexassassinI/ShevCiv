package Processing.Buildings;

import Processing.Player.Player;
import Processing.TileMap.TileUtils.Resource;
import Processing.Utilits.Tag;
import Processing.Utilits.Wealth;
import Processing.Utilits.Wrapers.TwoTTT;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Building extends Tag implements Serializable {
    static final long serialVersionUID = 0L;

    Building(String name, Wealth passiveWealth, double productionCost, double moneyCost, Job jobs[], int countOfJobs[], Resource resource[], int countOfResource[], String description){
        this.passiveWealth = passiveWealth;
        this.productionCost = productionCost;
        this.moneyCost = moneyCost;
        this.description = description;
        this.name = name;
        if(jobs != null){
            if(countOfJobs.length == jobs.length){
                for(int i = 0; i < jobs.length; i++){
                    this.Jobs.add(new TwoTTT<>(jobs[i], countOfJobs[i]));
                }
            }
        }
        if(resource != null){
            if(countOfResource.length == resource.length){
                for(int i = 0; i < resource.length; i++){
                    this.NeededResources.add(new TwoTTT<Resource, Integer>(resource[i], countOfResource[i]));
                }
            }
        }
    }

    Building(){

    }

    @Override
    public String toString() {
        return this.name;
    }


    public void LoadSetTo(Object object){
        Building Building = (Building)object;
        this.passiveWealth = Building.passiveWealth;
        this.productionCost = Building.productionCost;
        this.moneyCost = Building.moneyCost;
        this.description = Building.description;
        this.name = Building.name;
    }

    public Building initBuilding(){
        AllBuildings.put(this.name, this);
        return this;
    }

    public String name;
    public String description;
    public Wealth passiveWealth;
    public double productionCost;
    public double moneyCost;
    public LinkedList<TwoTTT<Job,Integer>> Jobs = new LinkedList<>();
    public LinkedList<TwoTTT<Resource, Integer>> NeededResources = new LinkedList<>();

    public static LinkedHashMap<String, Building> AllBuildings = new LinkedHashMap<>();

    public Building MakeCopyToPlayer(Player player){
        Building building = new Building();
        building.passiveWealth = this.passiveWealth;
        building.productionCost = this.productionCost;
        building.moneyCost = this.moneyCost;
        building.description = this.description;
        building.name = this.name;
        Iterator<TwoTTT<Job,Integer>> iterator = this.Jobs.iterator();
        while(iterator.hasNext()){
            TwoTTT<Job, Integer> TMP_Two = iterator.next();
            building.Jobs.add(new TwoTTT<>(player.jobs.get(TMP_Two.first.name), TMP_Two.second));
        }
        building.NeededResources = this.NeededResources;
        return building;
    }

    @Override
    public boolean equals(Object object){
        if(object == null){
            return false;
        }
        if(object.getClass() == this.getClass()){
            if(this.name.hashCode() == ((Building) object).name.hashCode()){
                return true;
            }
        }
        return false;
    }



    public static final Building Granary = new Building(
            "Granary",
            new Wealth(0,1,3,0,0,0),
            30,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Library = new Building(
            "Library",
            new Wealth(0,-2,0,3,3,3),
            50,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Shrine = new Building(
            "Shrine",
            new Wealth(0,1,0,0,0,4),
            50,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building  StoneWorks = new Building(
            "StoneWorks",
            new Wealth(3,2,0,0,0,0),
            70,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building WindMill = new Building(
            "WindMill",
            new Wealth(4,-2,2,1,0,0),
            90,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building  Amphitheater = new Building(
            "Amphitheater",
            new Wealth(0,2,0,0,3,0),
            100,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Aqueduct = new Building(
            "Aqueduct",
            new Wealth(2,-1,4,0,0,0),
            120,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Caravansary = new Building(
            "Caravansary",
            new Wealth(1,6,1,0,1,0),
            140,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Courthouse = new Building(
            "Courthouse",
            new Wealth(0,-1,0,0,4,0),
            150,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Market = new Building(
            "Market",
            new Wealth(2,8,1,2,0,0),
            180,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Bazaar = new Building(
            "Market",
            new Wealth(3,13,3,3,1,0),
            220,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Mint = new Building(
            "Mint",
            new Wealth(1,4,0,2,0,0),
            200,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Stable = new Building(
            "Stable",
            new Wealth(4,-1,2,0,0,0),
            150,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Temple = new Building(
            "Temple",
            new Wealth(0,2,0,0,0,5),
            200,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Armory = new Building(
            "Armory",
            new Wealth(3,-2,0,2,0,0),
            220,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Forge = new Building(
            "Forge",
            new Wealth(5,-2,0,4,0,0),
            280,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Garden = new Building(
            "Garden",
            new Wealth(0,-1,0,0,3,2),
            70,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building University = new Building(
            "University",
            new Wealth(0,-2,0,10,10,10),
            300,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Workshop = new Building(
            "Workshop",
            new Wealth(7,4,0,2,0,0),
            320,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Bank = new Building(
            "Bank",
            new Wealth(0,20,0,0,2,0),
            340,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Observatory = new Building(
            "Observatory",
            new Wealth(0,-2,0,7,7,7),
            350,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building OperaHouse = new Building(
            "OperaHouse",
            new Wealth(0,4,0,0,4,0),
            300,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Factory = new Building(
            "Factory",
            new Wealth(12,-3,0,2,0,0),
            400,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Hospital = new Building(
            "Hospital",
            new Wealth(0,-3,8,0,3,5),
            410,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Museum = new Building(
            "Museum",
            new Wealth(0,-1,0,0,6,6),
            430,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building PublicSchool = new Building(
            "PublicSchool",
            new Wealth(0,-2,0,16,16,16),
            600,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building StockExchange = new Building(
            "StockExchange",
            new Wealth(0,20,0,0,1,0),
            500,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building Hotel = new Building(
            "Hotel",
            new Wealth(0,5,0,0,1,0),
            300,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building ResearchLab = new Building(
            "ResearchLab",
            new Wealth(0,-2,0,20,20,20),
            700,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building MedicalLab = new Building(
            "MedicalLab",
            new Wealth(0,-2,10,0,0,0),
            500,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building CoalPlant = new Building(
            "MedicalLab",
            new Wealth(25,-5,0,2,0,0),
            900,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    /////////////////////////////////////

    public static final Building DwarfBank = new Building(
            "DwarfBank",
            new Wealth(3,25,0,2,2,0),
            340,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building ElvenBazaar = new Building(
            "ElvenBazaar",
            new Wealth(3,16,5,4,2,2),
            220,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

    public static final Building HumanManufacture = new Building(
            "HumanManufacture",
            new Wealth(7,-3,0,2,1,0),
            320,
            0,
            null,
            null,
            null,
            null,
            "descr"
    ).initBuilding();

}
