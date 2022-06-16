package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class TypeOfLand extends TileLayer implements Serializable {
    static final long serialVersionUID = 11L;

    public static LinkedHashMap<String, TypeOfLand> AllTypeOfLand = new LinkedHashMap<>();
    public static final TypeOfLand Void = new TypeOfLand("Void",  0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand DeepOcean = new TypeOfLand("DeepOcean",  0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand Shores = new TypeOfLand("Shores",  0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand FlatLand = new TypeOfLand("FlatLand",  0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand Hills = new TypeOfLand("Hills",  0,
            BattleModifier.none, new Wealth());
    public static final TypeOfLand Mountains = new TypeOfLand("Mountains",  0,
            BattleModifier.none, new Wealth());

    TypeOfLand(String modElementName, int additionalActionPointCost, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, additionalActionPointCost, battleModifier, wealth);
        AllTypeOfLand.put(this.elementName,(TypeOfLand)this.setType(AllTypeOfLand.size()));
    }
}
