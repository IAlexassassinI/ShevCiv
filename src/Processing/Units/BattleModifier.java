package Processing.Units;

import Processing.TileMap.TileUtils.TypeOfLand;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class BattleModifier implements Serializable {
    static final long serialVersionUID = 18L;

    static final public double ATTACK_CONST = 15;
    static final public double RESPONSE_WHEN_KILLED = 0D;

    static final public double ATTACK_ACROSS_THE_RIVER_MODIFIER = 0.50D;
    static final public double ATTACK_ACROSS_THE_BRIDGE_MODIFIER = 0.75D;

    static final public double DEFENCE_ACROSS_THE_RIVER_MODIFIER = 1D;
    static final public double DEFENCE_ACROSS_THE_BRIDGE_MODIFIER = 1D;

    public String name;

    public double additionalDefenseMelee;
    public double additionalDefenseRanged;

    public double additionalAttackMelee;
    public double additionalAttackRanged;

    public double additionalShootingRange;
    public double additionalVisionRange;

    public double additionalHealing;

    public static LinkedHashMap<String, BattleModifier> AllTypeOfBattleModifier = new LinkedHashMap<>();
    public static final BattleModifier none = new BattleModifier("None", 1, 1, 1,1, 1, 1,1);
    public static final BattleModifier half = new BattleModifier("Half", 0.5, 0.5, 0.5,0.5, 0.5, 0.5,0.5);
    public static final BattleModifier hill = new BattleModifier("hill", 1.25, 1.25, 1.25,1.25, 1.5, 1.5,1);
    public static final BattleModifier forest = new BattleModifier("forest", 1, 1.25, 1,1, 1, 1,1);
    public static final BattleModifier berserkGases = new BattleModifier("berserkGases", 0.75, 0.75, 2,1.25, 1.25, 1,1.5);
    public static final BattleModifier timeAnomalyFast = new BattleModifier("timeAnomalyFast", 0.75, 0.75, 1.25,1, 1, 1,2);
    public static final BattleModifier timeAnomalySlow = new BattleModifier("timeAnomalySlow", 1.25, 1.25, 0.75,1, 1, 1,0.5);


    BattleModifier(String name,
                   double additionalDefenseMelee,
                   double additionalDefenseRanged,

                   double additionalAttackMelee,
                   double additionalAttackRanged,

                   double additionalShootingRange,
                   double additionalVisionRange,

                   double additionalHealing){

        this.name = name;
        this.additionalDefenseMelee = additionalDefenseMelee;
        this.additionalDefenseRanged = additionalDefenseRanged;

        this.additionalAttackMelee = additionalAttackMelee;
        this.additionalAttackRanged = additionalAttackRanged;

        this.additionalShootingRange = additionalShootingRange;
        this.additionalVisionRange = additionalVisionRange;

        this.additionalHealing = additionalHealing;
        AllTypeOfBattleModifier.put(name, this);
    }

    public void LoadSetTo(Object object){
        BattleModifier BattleModifier = (BattleModifier) object;
        this.name = BattleModifier.name;
        this.additionalDefenseMelee = BattleModifier.additionalDefenseMelee;
        this.additionalDefenseRanged = BattleModifier.additionalDefenseRanged;

        this.additionalAttackMelee = BattleModifier.additionalAttackMelee;
        this.additionalAttackRanged = BattleModifier.additionalAttackRanged;

        this.additionalShootingRange = BattleModifier.additionalShootingRange;
        this.additionalVisionRange = BattleModifier.additionalVisionRange;

        this.additionalHealing = BattleModifier.additionalHealing;
    }


}
