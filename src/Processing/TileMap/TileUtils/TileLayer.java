package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Wealth;

import java.io.Serializable;

public class TileLayer implements Serializable {
    static final long serialVersionUID = 8L;

    public int Type;

    public TileLayer setType(int type){
        this.Type = type;
        return this;
    }

    public int additionalActionPointCost;
    public int additionalVision;

    public BattleModifier battleModifier;

    public Wealth wealth;
    public String elementName;

    public TileLayer(String elementName,
                     //int type,

                     int additionalActionPointCost,
                     int additionalVision,

                     BattleModifier battleModifier,

                     Wealth wealth){

        this.elementName = elementName;
        //this.Type = type;
        this.additionalActionPointCost = additionalActionPointCost;
        this.additionalVision = additionalVision;

        this.battleModifier = battleModifier;

        this.wealth = wealth;
    }
}
