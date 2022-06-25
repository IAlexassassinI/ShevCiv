package Processing.Units;

import Processing.TileMap.TileUtils.TypeOfLand;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class BattleModifier implements Serializable {
    static final long serialVersionUID = 18L;

    static final public double ATTACK_CONST = 10;
    static final public double RESPONSE_WHEN_KILLED = 0.5D;

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



}
