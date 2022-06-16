package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TypeOfBuilding extends TileLayer implements Serializable {
    static final long serialVersionUID = 9L;

    public static LinkedHashMap<String, TypeOfBuilding> AllTypeOfBuilding = new LinkedHashMap<>();
    public static final TypeOfBuilding none = new TypeOfBuilding("none",  false, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onLandNoMountain);
    public static final TypeOfBuilding City = new TypeOfBuilding("City", true, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onLandNoMountain);
    public static final TypeOfBuilding Ruins = new TypeOfBuilding("Ruins", false, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onLandNoMountain);
    public static final TypeOfBuilding Farmland = new TypeOfBuilding("Farmland", true, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onLandNoMountain);
    public static final TypeOfBuilding Mine = new TypeOfBuilding("Mine", true, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onLandNoMountain);
    public static final TypeOfBuilding Sawmill = new TypeOfBuilding("Sawmill", false, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onForest);

    //Maybe HashMap
    public boolean destroyFlora;
    public WhereCanBe whereCanExist;

    TypeOfBuilding(String modElementName,  boolean destroyFlora, int additionalActionPointCost, BattleModifier battleModifier, Wealth wealth, WhereCanBe whereCanExist){
        super(modElementName, additionalActionPointCost, battleModifier, wealth);
        AllTypeOfBuilding.put(this.elementName,(TypeOfBuilding)this.setType(AllTypeOfBuilding.size()));
        this.destroyFlora = destroyFlora;
        this.whereCanExist = whereCanExist;
    }
}
