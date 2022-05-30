package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.HashMap;

public class TypeOfFlora extends TileLayer implements Serializable {
    static final long serialVersionUID = 10L;

    public static HashMap<String, TypeOfFlora> AllTypeOfFlora = new HashMap<>();
    public static final TypeOfFlora none = new TypeOfFlora("none",0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfFlora Forest = new TypeOfFlora("Forest", 0, 0,
            BattleModifier.none, new Wealth());


    TypeOfFlora(String modElementName,  int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, additionalActionPointCost, additionalVision, battleModifier, wealth);
        AllTypeOfFlora.put(this.elementName,(TypeOfFlora)this.setType(AllTypeOfFlora.size()));
    }
}
