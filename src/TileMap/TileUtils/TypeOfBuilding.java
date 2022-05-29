package TileMap.TileUtils;

import Units.BattleModifier;
import Utilits.Wealth;

import java.io.Serializable;
import java.util.ArrayList;

public class TypeOfBuilding extends TileLayer implements Serializable {
    static final long serialVersionUID = 9L;

    public static final TypeOfBuilding none = new TypeOfBuilding("none", -1, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding City = new TypeOfBuilding("City", -2, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding Ruins = new TypeOfBuilding("Ruins", -3, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding Farmland = new TypeOfBuilding("Farmland", -4, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding Mine = new TypeOfBuilding("Mine", -5, 0, 0,
            BattleModifier.none, new Wealth());
    public static final TypeOfBuilding Sawmill = new TypeOfBuilding("Sawmill", -6, 0, 0,
            BattleModifier.none, new Wealth());
    public static ArrayList<TypeOfLand> ModTypeOfBuilding = new ArrayList<>();

    TypeOfBuilding(String modElementName, int type, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, type, additionalActionPointCost, additionalVision, battleModifier, wealth);
    }
}
