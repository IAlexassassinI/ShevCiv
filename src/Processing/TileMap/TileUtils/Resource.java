package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Resource extends TileLayer implements Serializable {
    static final long serialVersionUID = 7L;

    public static LinkedHashMap<String, Resource> AllResource = new LinkedHashMap<>();
    public static final Resource none = new Resource("none", 0, 0,
            BattleModifier.none, new Wealth(), WhereCanBe.noPreference);
    public static final Resource Iron = new Resource("Iron", 0, 0,
            BattleModifier.none, new Wealth(), WhereCanBe.onLandNoMountain);
    public static final Resource Horses = new Resource("Horses", 0, 0,
            BattleModifier.none, new Wealth(), WhereCanBe.onLandNoMountain);

    public WhereCanBe whereCanSpawn;


    Resource(String modElementName, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth, WhereCanBe whereCanSpawn){
        super(modElementName, additionalActionPointCost, additionalVision, battleModifier, wealth);
        AllResource.put(this.elementName,(Resource)this.setType(AllResource.size()));
        this.whereCanSpawn = whereCanSpawn;
    }
}
