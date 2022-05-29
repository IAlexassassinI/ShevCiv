package TileMap.TileUtils;

import Units.BattleModifier;
import Utilits.Wealth;

import java.io.Serializable;
import java.util.ArrayList;

public class Height extends TileLayer implements Serializable {
    static final long serialVersionUID = 6L;

    public static final Height DeepOcean = new Height("DeepOcean", -1,0, 0,
            BattleModifier.none, new Wealth());
    public static final Height Shores = new Height("Shores", -2,0, 0,
            BattleModifier.none, new Wealth());
    public static final Height FlatLand = new Height("FlatLand", -3,0, 0,
            BattleModifier.none, new Wealth());
    public static final Height Hills = new Height("Hills", -4,0, 0,
            BattleModifier.none, new Wealth());
    public static final Height Mountains = new Height("Mountains", -5,0, 0,
            BattleModifier.none, new Wealth());
    public static ArrayList<TypeOfLand> ModHeight = new ArrayList<>();

    Height(String modElementName, int type, int additionalActionPointCost, int additionalVision, BattleModifier battleModifier, Wealth wealth){
        super(modElementName, type, additionalActionPointCost, additionalVision, battleModifier, wealth);
    }

}
