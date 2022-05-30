package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.HashMap;

public class TypeOfLand extends TileLayer implements Serializable {
    static final long serialVersionUID = 11L;

    public static HashMap<String, TypeOfLand> AllTypeOfLand = new HashMap<>();
    public static final TypeOfLand Green = new TypeOfLand("Green", 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand Desert = new TypeOfLand("Desert", 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand Snow = new TypeOfLand("Snow", 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand Jungle = new TypeOfLand("Jungle", 0, 0,
            BattleModifier.none, new Wealth());

    TypeOfLand(String modElementName, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, additionalActionPointCost, additionalVision, battleModifier, wealth);
        AllTypeOfLand.put(this.elementName,(TypeOfLand)this.setType(AllTypeOfLand.size()));
    }
}
