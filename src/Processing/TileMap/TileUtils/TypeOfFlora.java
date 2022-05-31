package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TypeOfFlora extends TileLayer implements Serializable {
    static final long serialVersionUID = 10L;

    public static LinkedHashMap<String, TypeOfFlora> AllTypeOfFlora = new LinkedHashMap<>();
    public static final TypeOfFlora none = new TypeOfFlora("none",0, 0,
            BattleModifier.none, new Wealth(), new TypeOfLand[]{TypeOfLand.Void});
    public static final TypeOfFlora Forest = new TypeOfFlora("Forest", 0, 0,
            BattleModifier.none, new Wealth(), new TypeOfLand[]{TypeOfLand.Void});

    public HashMap<String, TypeOfLand> whereCanExist;

    TypeOfFlora(String modElementName, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth, TypeOfLand whereCanExist[]){
        super(modElementName, additionalActionPointCost, additionalVision, battleModifier, wealth);
        AllTypeOfFlora.put(this.elementName,(TypeOfFlora)this.setType(AllTypeOfFlora.size()));
        for(int i = 0; i < whereCanExist.length; i++){
            this.whereCanExist.put(whereCanExist[i].elementName, whereCanExist[i]);
        }
    }
}
