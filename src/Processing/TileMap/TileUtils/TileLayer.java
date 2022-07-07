package Processing.TileMap.TileUtils;

import Processing.Units.BattleModifier;
import Processing.Utilits.Tag;
import Processing.Utilits.Wealth;

import java.io.Serializable;

public class TileLayer extends Tag implements Serializable {
    static final long serialVersionUID = 8L;

    public int Type;

    public TileLayer setType(int type){
        this.Type = type;
        return this;
    }

    public double additionalActionPointCost;

    public BattleModifier battleModifier;

    public Wealth wealth;
    public String elementName;

    public TileLayer(String elementName,
                     //int type,

                     double additionalActionPointCost,

                     BattleModifier battleModifier,

                     Wealth wealth){

        this.elementName = elementName;
        //this.Type = type;
        this.additionalActionPointCost = additionalActionPointCost;

        this.battleModifier = battleModifier;

        this.wealth = wealth;
    }
}
