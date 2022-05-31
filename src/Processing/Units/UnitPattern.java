package Processing.Units;

import Processing.TileMap.TileUtils.Resource;

import java.io.Serializable;


public class UnitPattern implements Serializable {
    static final long serialVersionUID = 15L;

    String NameOfUnit;

    int productionCost;
    int moneyCost;
    Resource NeededResources[];

    int moneyUpkeep;

    int maxActionPoints;

   //WhereCanMove tile layers, high

    boolean isRanged;
    int rangeOfAttack;
    int rangedDamage;



}
