package Processing.TileMap.TileUtils;

import Processing.Buildings.Job;
import Processing.Units.BattleModifier;
import Processing.Utilits.LoadStatic;
import Processing.Utilits.Wealth;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Resource extends TileLayer implements Serializable, LoadStatic {
    static final long serialVersionUID = 6L;

    public static LinkedHashMap<String, Resource> AllResource = new LinkedHashMap<>();
    public static Resource none = new Resource("none", 0,
            BattleModifier.none, new Wealth(), WhereCanBe.noPreference);
    public static Resource Iron = new Resource("Iron",  0,
            BattleModifier.none, new Wealth(), WhereCanBe.onLandNoMountain);
    public static Resource Horses = new Resource("Horses", 0,
            BattleModifier.none, new Wealth(), WhereCanBe.onLandNoMountain);

    public WhereCanBe whereCanSpawn;


    Resource(String modElementName, int additionalActionPointCost, BattleModifier battleModifier, Wealth wealth, WhereCanBe whereCanSpawn){
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
