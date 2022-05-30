package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.HashMap;

public class TypeOfBuilding extends TileLayer implements Serializable {
    static final long serialVersionUID = 9L;

    public static HashMap<String, TypeOfBuilding> AllTypeOfBuilding = new HashMap<>();
    public static final TypeOfBuilding none = new TypeOfBuilding("none",  false, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding City = new TypeOfBuilding("City", true, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding Ruins = new TypeOfBuilding("Ruins", false, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding Farmland = new TypeOfBuilding("Farmland", true, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding Mine = new TypeOfBuilding("Mine", true, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding Sawmill = new TypeOfBuilding("Sawmill", false, 0, 0,
            BattleModifier.none, new Wealth());

    public boolean destroyFlora;

    TypeOfBuilding(String modElementName,  boolean destroyFlora, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, additionalActionPointCost, additionalVision, battleModifier, wealth);
        AllTypeOfBuilding.put(this.elementName,(TypeOfBuilding)this.setType(AllTypeOfBuilding.size()));
        this.destroyFlora = destroyFlora;
    }
}
