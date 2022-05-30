package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.HashMap;

public class Resource extends TileLayer implements Serializable {
    static final long serialVersionUID = 7L;

    public static HashMap<String, Resource> AllResource = new HashMap<>();
    public static final Resource none = new Resource("none", 0, 0,
            BattleModifier.none, new Wealth());
    public static final Resource Iron = new Resource("Iron", 0, 0,
            BattleModifier.none, new Wealth());
    public static final Resource Horses = new Resource("Horses", 0, 0,
            BattleModifier.none, new Wealth());

    Resource(String modElementName, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, additionalActionPointCost, additionalVision, battleModifier, wealth);
        AllResource.put(this.elementName,(Resource)this.setType(AllResource.size()));
    }
}
