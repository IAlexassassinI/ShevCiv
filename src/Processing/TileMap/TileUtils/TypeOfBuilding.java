package Processing.TileMap.TileUtils;

import Processing.Buildings.Building;
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
            WhereCanBe.onLandNoMountain, 0, false).initBuildings();
    public static final TypeOfBuilding City = new TypeOfBuilding("City", true, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onLandNoMountain, 0, false).initBuildings();
    public static final TypeOfBuilding Ruins = new TypeOfBuilding("Ruins", false, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onLandNoMountain, 0, true).initBuildings();
    public static final TypeOfBuilding Farmland = new TypeOfBuilding("Farmland", true, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onLandNoMountain, 2, true).initBuildings();
    public static final TypeOfBuilding Mine = new TypeOfBuilding("Mine", true, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onLandNoMountain,  2, true).initBuildings();
    public static final TypeOfBuilding Sawmill = new TypeOfBuilding("Sawmill", false, 0,
            BattleModifier.none, new Wealth(),
            WhereCanBe.onForest, 2, true).initBuildings();

    //Maybe HashMap
    public boolean destroyFlora;
    public WhereCanBe whereCanExist;
    public double turnsToBuild;
    public boolean onlyInBorder;

    public TypeOfBuilding initBuildings(){
        AllTypeOfBuilding.put(this.elementName,(TypeOfBuilding)this.setType(AllTypeOfBuilding.size()));
        return this;
    }

    TypeOfBuilding(String modElementName,  boolean destroyFlora, int additionalActionPointCost, BattleModifier battleModifier, Wealth wealth, WhereCanBe whereCanExist, double turnsToBuild, boolean onlyInBorder){
        super(modElementName, additionalActionPointCost, battleModifier, wealth);
        this.destroyFlora = destroyFlora;
        this.whereCanExist = whereCanExist;
        this.turnsToBuild = turnsToBuild;
        this.onlyInBorder = onlyInBorder;
    }

    @Override
    public boolean equals(Object object){
        if(object == null){
            return false;
        }
        if(object.getClass() == this.getClass()){
            if(this.elementName.hashCode() == ((TypeOfBuilding) object).elementName.hashCode()){
                return true;
            }
        }
        return false;
    }

}
