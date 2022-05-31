package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Height extends TileLayer implements Serializable {
    static final long serialVersionUID = 6L;

    public static LinkedHashMap<String, Height> AllHeight = new LinkedHashMap<>();
    public static final Height Void = new Height("Void",0, 0,
            BattleModifier.none, new Wealth());
    public static final Height DeepOcean = new Height("DeepOcean",0, 0,
            BattleModifier.none, new Wealth());
    public static final Height Shores = new Height("Shores", 0, 0,
            BattleModifier.none, new Wealth());
    public static final Height FlatLand = new Height("FlatLand", 0, 0,
            BattleModifier.none, new Wealth());
    public static final Height Hills = new Height("Hills", 0, 0,
            BattleModifier.none, new Wealth());
    public static final Height Mountains = new Height("Mountains", 0, 0,
            BattleModifier.none, new Wealth());

    Height(String modElementName, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, additionalActionPointCost, additionalVision, battleModifier, wealth);
        AllHeight.put(this.elementName,(Height)this.setType(AllHeight.size()));
    }

}
