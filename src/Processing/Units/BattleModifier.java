package Processing.Units;

import java.io.Serializable;

public class BattleModifier implements Serializable {
    static final long serialVersionUID = 2L;

    public double additionalDefenseMelee;
    public double additionalDefenseRanged;

    public double additionalAttackMelee;
    public double additionalAttackRanged;

    public double additionalShootingRange;

    public double additionalHealing;

    public static final BattleModifier none = new BattleModifier(0, 0,0, 0, 0,0);

    BattleModifier(double additionalDefenseMelee,
                   double additionalDefenseRanged,

                   double additionalAttackMelee,
                   double additionalAttackRanged,

                   double additionalShootingRange,

                   double additionalHealing){

        this.additionalDefenseMelee = additionalDefenseMelee;
        this.additionalDefenseRanged = additionalDefenseRanged;

        this.additionalAttackMelee = additionalAttackMelee;
        this.additionalAttackRanged = additionalAttackRanged;

        this.additionalShootingRange = additionalShootingRange;

        this.additionalHealing = additionalHealing;

    }



}
