package Units;

import java.io.Serializable;

public class BattleModifier implements Serializable {
    static final long serialVersionUID = 2L;

    public int additionalDefenseMelee;
    public int additionalDefenseRanged;

    public int additionalAttackMelee;
    public int additionalAttackRanged;

    public int additionalShootingRange;

    public int additionalHealing;

    public static final BattleModifier none = new BattleModifier(0, 0,0, 0, 0,0);

    BattleModifier(int additionalDefenseMelee,
                   int additionalDefenseRanged,

                   int additionalAttackMelee,
                   int additionalAttackRanged,

                   int additionalShootingRange,

                   int additionalHealing){

        this.additionalDefenseMelee = additionalDefenseMelee;
        this.additionalDefenseRanged = additionalDefenseRanged;

        this.additionalAttackMelee = additionalAttackMelee;
        this.additionalAttackRanged = additionalAttackRanged;

        this.additionalShootingRange = additionalShootingRange;

        this.additionalHealing = additionalHealing;

    }



}
