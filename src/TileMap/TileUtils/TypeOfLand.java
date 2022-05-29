package TileMap.TileUtils;

import Units.BattleModifier;
import Utilits.Wealth;

import java.io.Serializable;
import java.util.ArrayList;

public class TypeOfLand extends TileLayer implements Serializable {
    static final long serialVersionUID = 11L;

    public static final TypeOfLand Green = new TypeOfLand("Green", -1, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand Desert = new TypeOfLand("Desert", -1, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand Snow = new TypeOfLand("Snow", -1, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand Jungle = new TypeOfLand("Jungle", -1, 0, 0,
            BattleModifier.none, new Wealth());
    public static ArrayList<TypeOfLand> ModTypeOfLand = new ArrayList<>();

    TypeOfLand(String modElementName, int type, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, type, additionalActionPointCost, additionalVision, battleModifier, wealth);
    }
}
