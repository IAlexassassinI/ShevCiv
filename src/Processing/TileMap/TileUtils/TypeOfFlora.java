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
    public static TypeOfFlora none = new TypeOfFlora("none",0,
            BattleModifier.none, new Wealth(), WhereCanBe.noPreference);
    public static TypeOfFlora Forest = new TypeOfFlora("Forest", 0,
            BattleModifier.none, new Wealth(), WhereCanBe.onLandNoMountain);

    public WhereCanBe whereCanExist;

    TypeOfFlora(String modElementName, int additionalActionPointCost, BattleModifier battleModifier, Wealth wealth, WhereCanBe whereCanExist){
        super(modElementName, additionalActionPointCost, battleModifier, wealth);
        this.whereCanExist = whereCanExist;
        AllTypeOfFlora.put(this.elementName,(TypeOfFlora)this.setType(AllTypeOfFlora.size()));
    }

    public void LoadSetTo(Object object){
        TypeOfFlora TypeOfFlora = (TypeOfFlora) object;
        this.elementName = TypeOfFlora.elementName;
        this.additionalActionPointCost = TypeOfFlora.additionalActionPointCost;
        this.wealth = TypeOfFlora.wealth;

    }
}
