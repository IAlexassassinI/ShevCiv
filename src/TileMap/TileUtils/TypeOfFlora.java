package TileMap.TileUtils;

import Units.BattleModifier;
import Utilits.Wealth;

import java.io.Serializable;
import java.util.ArrayList;

public class TypeOfFlora extends TileLayer implements Serializable {
    static final long serialVersionUID = 10L;

    public static final TypeOfFlora none = new TypeOfFlora("none", -1, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfFlora Forest = new TypeOfFlora("Forest", -2, 0, 0,
            BattleModifier.none, new Wealth());
    public static ArrayList<TypeOfLand> ModTypeOfFlora = new ArrayList<>();


    TypeOfFlora(String modElementName, int type, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, type, additionalActionPointCost, additionalVision, battleModifier, wealth);
    }
}
