package TileMap.TileUtils;

import Units.BattleModifier;
import Utilits.Wealth;

import java.io.Serializable;
import java.util.ArrayList;

public class Resource extends TileLayer implements Serializable {
    static final long serialVersionUID = 7L;

    public static final Resource none = new Resource("none", -1,0, 0,
            BattleModifier.none, new Wealth());
    public static final Resource Iron = new Resource("Iron", -2,0, 0,
            BattleModifier.none, new Wealth());
    public static final Resource Horses = new Resource("Horses", -3,0, 0,
            BattleModifier.none, new Wealth());
    public static ArrayList<TypeOfLand> ModResource = new ArrayList<>();

    Resource(String modElementName, int type,  int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, type, additionalActionPointCost, additionalVision, battleModifier, wealth);
    }
}
