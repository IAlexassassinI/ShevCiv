package Processing.Units;

import Processing.TileMap.TileUtils.Resource;

public class UnitPattern {
    String NameOfUnit;

    int productionCost;
    int moneyCost;
    Resource NeededResources[];

    int moneyUpkeep;

    int maxActionPoints;

    boolean canMoveOnGround;
    boolean canMoveOnMountain;
    boolean canMoveOnShores;
    boolean canMoveOnDeepOcean;

    boolean isRanged;
    int rangeOfAttack;
    int rangedDamage;



}
