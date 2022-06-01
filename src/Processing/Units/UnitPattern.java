package Processing.Units;

import Processing.TileMap.TileUtils.Resource;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;


public class UnitPattern implements Serializable {
    static final long serialVersionUID = 15L;

    String NameOfUnit;

    int productionCost;
    int moneyCost;
    Resource NeededResources[];

    int moneyUpkeep;

    int maxActionPoints;

    boolean isFlying;
    WhereCanBe whereCanMove;

    boolean isRanged;
    int rangeOfAttack;
    int rangedDamage;

    int attackMelee;
    int defenceMelee;
    int defenceRanged;
    int maxHitPoints;
    int visionRange;
    int maxNumberOfAttacks;
    int defenceBonusFromSkipTurn;





}
