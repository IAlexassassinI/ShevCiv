package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TypeOfFlora extends TileLayer implements Serializable {
    static final long serialVersionUID = 10L;

    public static LinkedHashMap<String, TypeOfFlora> AllTypeOfFlora = new LinkedHashMap<>();
    public static final TypeOfFlora none = new TypeOfFlora("none",0,
            BattleModifier.none, new Wealth(), WhereCanBe.noPreference);
    public static final TypeOfFlora Forest = new TypeOfFlora("Forest", 0,
            BattleModifier.none, new Wealth(), WhereCanBe.onLandNoMountain);

    public WhereCanBe whereCanExist;

    TypeOfFlora(String modElementName, int additionalActionPointCost, BattleModifier battleModifier, Wealth wealth, WhereCanBe whereCanExist){
        super(modElementName, additionalActionPointCost, battleModifier, wealth);
        AllTypeOfFlora.put(this.elementName,(TypeOfFlora)this.setType(AllTypeOfFlora.size()));
        this.whereCanExist = whereCanExist;
    }
}
