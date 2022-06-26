package Processing.Utilits;

import Processing.Buildings.Building;
import Processing.Buildings.Job;
import Processing.TileMap.TileUtils.Resource;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.TileMap.TileUtils.TypeOfFlora;
import Processing.TileMap.TileUtils.TypeOfLand;
import Processing.Units.Ability.SpecialAbility;
import Processing.Units.BattleModifier;
import Processing.Units.Projectile;
import Processing.Units.UnitPattern;

import java.io.Serializable;
import java.util.*;

public class GeneralUtility implements Serializable {
    static final long serialVersionUID = 29L;
    static public int Round(double toRound){

        int IntPart = (int)toRound;
        if(toRound - IntPart < 0.5D){
            return IntPart;
        }
        else{
            return IntPart+1;
        }
    }

    static public void initAllProcessingNeeds(){
        WhereCanBe.initTypes();
        SpecialAbility.initAbilityList();
    }

    private LinkedHashMap<String, Building> AllBuildings;
    private LinkedHashMap<String, Job> AllJobs;
    private LinkedHashMap<String, Resource> AllResource;
    private LinkedHashMap<String, TypeOfBuilding> AllTypeOfBuilding;
    public LinkedHashMap<String, TypeOfFlora> AllTypeOfFlora;
    private LinkedHashMap<String, TypeOfLand> AllTypeOfLand;
    private LinkedHashMap<String, BattleModifier> AllTypeOfBattleModifier;
    private LinkedHashMap<String, Projectile> AllTypeOfProjectile;
    private LinkedHashMap<String, UnitPattern> AllUnitPattern;
    private LinkedList<String> AllTags;
    private LinkedHashMap<String, WhereCanBe> AllWhereCanSpawn;

    public void saveStaticParams(){
        this.AllBuildings = Building.AllBuildings;
        this.AllJobs = Job.AllJobs;
        this.AllResource = Resource.AllResource;
        this.AllTypeOfBuilding = TypeOfBuilding.AllTypeOfBuilding;
        this.AllTypeOfFlora = TypeOfFlora.AllTypeOfFlora;
        this.AllTypeOfLand = TypeOfLand.AllTypeOfLand;
        this.AllTypeOfBattleModifier = BattleModifier.AllTypeOfBattleModifier;
        this.AllTypeOfProjectile = Projectile.AllTypeOfProjectile;
        this.AllUnitPattern = UnitPattern.AllUnitPattern;
        this.AllTags = Tag.AllTags;
        this.AllWhereCanSpawn = WhereCanBe.AllWhereCanSpawn;
    }

    public void loadStaticParams(){

        Tag.AllTags = this.AllTags;
        Building.AllBuildings = this.AllBuildings;
        Job.AllJobs = this.AllJobs;
        Resource.AllResource = this.AllResource;
        TypeOfBuilding.AllTypeOfBuilding = this.AllTypeOfBuilding;
        TypeOfFlora.AllTypeOfFlora = this.AllTypeOfFlora;
        TypeOfLand.AllTypeOfLand = this.AllTypeOfLand;
        BattleModifier.AllTypeOfBattleModifier = this.AllTypeOfBattleModifier;
        Projectile.AllTypeOfProjectile = this.AllTypeOfProjectile;
        UnitPattern.AllUnitPattern = this.AllUnitPattern;
        WhereCanBe.AllWhereCanSpawn = this.AllWhereCanSpawn;
    }



}
