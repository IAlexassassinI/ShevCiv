package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TypeOfBuilding extends TileLayer implements Serializable {
    static final long serialVersionUID = 9L;

    public static LinkedHashMap<String, TypeOfBuilding> AllTypeOfBuilding = new LinkedHashMap<>();
    public static final TypeOfBuilding none = new TypeOfBuilding("none",  false, 0, 0,
            BattleModifier.none, new Wealth(),
            new Resource[]{Resource.none}, new TypeOfFlora[]{TypeOfFlora.none}, new TypeOfLand[]{TypeOfLand.Void});
    public static final TypeOfBuilding City = new TypeOfBuilding("City", true, 0, 0,
            BattleModifier.none, new Wealth(),
            new Resource[]{Resource.none}, new TypeOfFlora[]{TypeOfFlora.none}, new TypeOfLand[]{TypeOfLand.Void});
    public static final TypeOfBuilding Ruins = new TypeOfBuilding("Ruins", false, 0, 0,
            BattleModifier.none, new Wealth(),
            new Resource[]{Resource.none}, new TypeOfFlora[]{TypeOfFlora.none}, new TypeOfLand[]{TypeOfLand.Void});
    public static final TypeOfBuilding Farmland = new TypeOfBuilding("Farmland", true, 0, 0,
            BattleModifier.none, new Wealth(),
            new Resource[]{Resource.none}, new TypeOfFlora[]{TypeOfFlora.none}, new TypeOfLand[]{TypeOfLand.Void});
    public static final TypeOfBuilding Mine = new TypeOfBuilding("Mine", true, 0, 0,
            BattleModifier.none, new Wealth(),
            new Resource[]{Resource.none}, new TypeOfFlora[]{TypeOfFlora.none}, new TypeOfLand[]{TypeOfLand.Void});
    public static final TypeOfBuilding Sawmill = new TypeOfBuilding("Sawmill", false, 0, 0,
            BattleModifier.none, new Wealth(),
            new Resource[]{Resource.none}, new TypeOfFlora[]{TypeOfFlora.Forest}, new TypeOfLand[]{TypeOfLand.Void});

    //Maybe HashMap
    public boolean destroyFlora;
    public HashMap<String,Resource> whatResourceIsNeeded;
    public HashMap<String,TypeOfFlora> whatFloraIsNeeded;
    public HashMap<String,TypeOfLand> whatLandIsNeeded;

    TypeOfBuilding(String modElementName,  boolean destroyFlora, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth, Resource whatResourceIsNeeded[], TypeOfFlora whatFloraIsNeeded[], TypeOfLand whatLandIsNeeded[]){
        super(modElementName, additionalActionPointCost, additionalVision, battleModifier, wealth);
        AllTypeOfBuilding.put(this.elementName,(TypeOfBuilding)this.setType(AllTypeOfBuilding.size()));
        this.destroyFlora = destroyFlora;
        for(int i = 0; i < whatResourceIsNeeded.length; i++){
            this.whatResourceIsNeeded.put(whatResourceIsNeeded[i].elementName, whatResourceIsNeeded[i]);
        }
        for(int i = 0; i < whatFloraIsNeeded.length; i++){
            this.whatFloraIsNeeded.put(whatFloraIsNeeded[i].elementName, whatFloraIsNeeded[i]);
        }
        for(int i = 0; i < whatLandIsNeeded.length; i++){
            this.whatLandIsNeeded.put(whatLandIsNeeded[i].elementName, whatLandIsNeeded[i]);
        }

    }
}
