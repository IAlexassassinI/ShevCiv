package TileMap.TileUtils;

import Units.BattleModifier;
import Utilits.Wealth;

import java.io.Serializable;

public class TileLayer implements Serializable {
    static final long serialVersionUID = 8L;

    public int Type;
    public int additionalActionPointCost;
    public int additionalVision;

    public BattleModifier battleModifier;

    public Wealth wealth;
    public String modElementName;

    public TileLayer(String modElementName,
                     int type,

                     int additionalActionPointCost,
                     int additionalVision,

                     BattleModifier battleModifier,

                     Wealth wealth){

        this.modElementName = modElementName;
        this.Type = type;
        this.additionalActionPointCost = additionalActionPointCost;
        this.additionalVision = additionalVision;

        this.battleModifier = battleModifier;

        this.wealth = wealth;
    }
}
