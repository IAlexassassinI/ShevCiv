package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Resource extends TileLayer implements Serializable {
    static final long serialVersionUID = 6L;

    public static LinkedHashMap<String, Resource> AllResource = new LinkedHashMap<>();
    public static Resource none = new Resource("none", 0,
            BattleModifier.none, new Wealth(0,0,0,0,0,0), WhereCanBe.noPreference);
    public static Resource Iron = new Resource("Iron",  0,
            BattleModifier.none, new Wealth(1,0,0,0,0,0), WhereCanBe.onLandNoMountain);
    public static Resource Horses = new Resource("Horses", 0,
            BattleModifier.none, new Wealth(1,0,0,0,0,0), WhereCanBe.onLandNoMountain);
    public static Resource TimeAnomalyFast = new Resource("TimeAnomalyFast", -0.5,
            BattleModifier.timeAnomalyFast, new Wealth(2,0,2,0,0,0), WhereCanBe.noPreference);
    public static Resource TimeAnomalySlow = new Resource("TimeAnomalySlow", 0.5,
            BattleModifier.timeAnomalySlow, new Wealth(0,0,0,1,1,1), WhereCanBe.noPreference);
    public static Resource BerserkGases = new Resource("BerserkGases", 0,
            BattleModifier.berserkGases, new Wealth(0,0,0,0,1,0), WhereCanBe.onLandNoMountain);
    public static Resource AncientArtifacts = new Resource("AncientArtifacts", 0,
            BattleModifier.none, new Wealth(0,0,0,2,0,2), WhereCanBe.noPreference);

    public WhereCanBe whereCanSpawn;


    Resource(String modElementName, double additionalActionPointCost, BattleModifier battleModifier, Wealth wealth, WhereCanBe whereCanSpawn){
        super(modElementName, additionalActionPointCost, battleModifier, wealth);
        AllResource.put(this.elementName,(Resource)this.setType(AllResource.size()));
        this.whereCanSpawn = whereCanSpawn;
    }

    public void LoadSetTo(Object object){
        Resource Resource = (Resource) object;
        this.elementName = Resource.elementName;
        this.additionalActionPointCost = Resource.additionalActionPointCost;
        this.wealth = Resource.wealth;
    }
}
