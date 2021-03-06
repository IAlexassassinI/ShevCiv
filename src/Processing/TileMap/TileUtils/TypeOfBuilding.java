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
    public static TypeOfBuilding none = new TypeOfBuilding("none",  false, 0,
            BattleModifier.none, new Wealth(0,0,0,0,0,0),
            WhereCanBe.onLandNoMountain, 0, true).initBuildings();
    public static TypeOfBuilding City = new TypeOfBuilding("City", true, 0,
            BattleModifier.none, new Wealth(5,5,5,2,2,2),
            WhereCanBe.onLandNoMountain, 0, false).initBuildings();
    public static TypeOfBuilding BuildingInProcess = new TypeOfBuilding("BuildingInProcess", false, 0,
            BattleModifier.none, new Wealth(0,0,0,0,0,0),
            WhereCanBe.onLandNoMountain, 0, false).initBuildings();
    public static TypeOfBuilding Farmland = new TypeOfBuilding("Farmland", true, 0,
            BattleModifier.none, new Wealth(0,0,2,0,0,0),
            WhereCanBe.onLandNoMountain, 2, true).initBuildings();
    public static TypeOfBuilding Mine = new TypeOfBuilding("Mine", true, 0,
            BattleModifier.none, new Wealth(2,1,0,0,0,0),
            WhereCanBe.onHills,  2, true).initBuildings();
    public static TypeOfBuilding Sawmill = new TypeOfBuilding("Sawmill", false, 0,
            BattleModifier.none, new Wealth(1,0,0,0,0,0),
            WhereCanBe.onForest, 2, true).initBuildings();

    //Maybe HashMap
    public boolean destroyFlora;
    public WhereCanBe whereCanExist;
    public double turnsToBuild;
    public boolean onlyInBorder;

    @Override
    public String toString() {
        return this.elementName;
    }

    public TypeOfBuilding initBuildings(){
        AllTypeOfBuilding.put(this.elementName,(TypeOfBuilding)this.setType(AllTypeOfBuilding.size()));
        return this;
    }

    TypeOfBuilding(String modElementName,  boolean destroyFlora, double additionalActionPointCost, BattleModifier battleModifier, Wealth wealth, WhereCanBe whereCanExist, double turnsToBuild, boolean onlyInBorder){
        super(modElementName, additionalActionPointCost, battleModifier, wealth);
        this.destroyFlora = destroyFlora;
        this.whereCanExist = whereCanExist;
        this.turnsToBuild = turnsToBuild;
        this.onlyInBorder = onlyInBorder;
    }

    public void LoadSetTo(Object object){
        TypeOfBuilding TypeOfBuilding = (TypeOfBuilding) object;
        this.elementName = TypeOfBuilding.elementName;
        this.additionalActionPointCost = TypeOfBuilding.additionalActionPointCost;
        this.wealth = TypeOfBuilding.wealth;

        this.destroyFlora = TypeOfBuilding.destroyFlora;
        this.turnsToBuild = TypeOfBuilding.turnsToBuild;
        this.onlyInBorder = TypeOfBuilding.onlyInBorder;
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
