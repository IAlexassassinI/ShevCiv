package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class TypeOfLand extends TileLayer implements Serializable {
    static final long serialVersionUID = 11L;

    public static LinkedHashMap<String, TypeOfLand> AllTypeOfLand = new LinkedHashMap<>();
    public static TypeOfLand Void = new TypeOfLand("Void",  0,
            BattleModifier.none, new Wealth());
    public static TypeOfLand DeepOcean = new TypeOfLand("DeepOcean",  0,
            BattleModifier.none, new Wealth(0,0,1,0,0,0));
    public static TypeOfLand Shores = new TypeOfLand("Shores",  0,
            BattleModifier.none, new Wealth(0,1,1,0,0,0));
    public static TypeOfLand FlatLand = new TypeOfLand("FlatLand",  0,
            BattleModifier.none, new Wealth(0,0,2,0,0,0));
    public static TypeOfLand Hills = new TypeOfLand("Hills",  0.5,
            BattleModifier.hill, new Wealth(1,0,1,0,0,0));
    public static TypeOfLand Mountains = new TypeOfLand("Mountains",  0,
            BattleModifier.none, new Wealth(0,2,0,1,1,1));

    TypeOfLand(String modElementName, double additionalActionPointCost, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, additionalActionPointCost, battleModifier, wealth);
        AllTypeOfLand.put(this.elementName,(TypeOfLand)this.setType(AllTypeOfLand.size()));
    }

    public void LoadSetTo(Object object){
        TypeOfLand TypeOfLand = (TypeOfLand) object;
        this.elementName = TypeOfLand.elementName;
        this.additionalActionPointCost = TypeOfLand.additionalActionPointCost;
        this.wealth = TypeOfLand.wealth;

    }

}
