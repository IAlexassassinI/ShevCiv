package Processing.Units;

import Processing.TileMap.TileUtils.Resource;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;


public class UnitPattern implements Serializable {
    static final long serialVersionUID = 15L;

    public String NameOfUnit;

    public double productionCost;
    public double moneyCost;
    public Resource NeededResources[];

    public double moneyUpkeep;

    public double maxActionPoints;

    public boolean isFlying;
    public WhereCanBe whereCanMove;
    public double moveModifier;
    public double howMuchAffectedByLandAdditionalAPCost;
    public double howMuchAffectedByFloraAdditionalAPCost;
    public double howMuchAffectedByResourceAdditionalAPCost;

    public boolean isRanged;
    public Projectile projectile;
    public double rangeOfAttack;
    public double rangedAttack;

    public double attackMelee;
    public double defenceMelee;
    public double defenceRanged;
    public double maxHitPoints;
    public double visionRange;
    public double maxNumberOfAttacks;
    public double defenceBonusFromSkipTurn;

    public double maxCapacity;
    public SpecialAbility Abilities[];


}
