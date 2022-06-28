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

    public static final Building MoneyMaker = new Building("MoneyMaker", new Wealth(), 0, 0, new Job[]{Job.MoneyMaker}, new int[]{1}, null, new int[]{0}, "descr").initBuilding();

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

}
