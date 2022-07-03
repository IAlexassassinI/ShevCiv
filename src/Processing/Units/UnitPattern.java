package Processing.Units;

import Processing.Buildings.Building;
import Processing.Buildings.Job;
import Processing.Player.Player;
import Processing.TileMap.TileUtils.Resource;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Units.Ability.Colonize;
import Processing.Units.Ability.ConstructSomethingOnTile;
import Processing.Units.Ability.GetCargoSmall;
import Processing.Units.Ability.SpecialAbility;
import Processing.Utilits.Tag;
import Processing.Utilits.Wealth;
import Processing.Utilits.WhereCanBe;
import Processing.Utilits.Wrapers.TwoTTT;

import java.io.Serializable;
import java.util.*;


public class UnitPattern extends Tag implements Serializable {
    static final long serialVersionUID = 21L;

    public String NameOfUnit;

    public double productionCost;
    public double moneyCost;
    public LinkedList<TwoTTT<Resource, Double>> NeededResources = new LinkedList<>();

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

    public ArrayList<String> Abilities;
    public double workEfficiency = 1;

    public static LinkedHashMap<String, UnitPattern> AllUnitPattern = new LinkedHashMap<>();

    public UnitPattern initUnitPattern(){
        AllUnitPattern.put(this.NameOfUnit, this);
        return this;
    }

    public UnitPattern(
            String NameOfUnit,
            double productionCost,
            double moneyCost,
            Resource NeededResources[],
            Double countOfNeededResources[],

            double moneyUpkeep,

            double maxActionPoints,

            boolean isFlying,
            WhereCanBe whereCanMove,
            double moveModifier,
            double howMuchAffectedByLandAdditionalAPCost,
            double howMuchAffectedByFloraAdditionalAPCost,
            double howMuchAffectedByResourceAdditionalAPCost,

            boolean isRanged,
            Projectile projectile,
            double rangeOfAttack,
            double rangedAttack,

            double attackMelee,
            double defenceMelee,
            double defenceRanged,
            double maxHitPoints,
            double visionRange,
            double maxNumberOfAttacks,

            String Abilities[],
            double workEfficiency,
            String tags[]
    ){
        this.NameOfUnit = NameOfUnit;

        this.productionCost = productionCost;
        this.moneyCost = moneyCost;
        this.NeededResources = new LinkedList<>();
        if(NeededResources == null || countOfNeededResources == null){
            this.NeededResources = null;
        }else {
            for(int i = 0; i < NeededResources.length; i++){
                this.NeededResources.add(new TwoTTT<>(NeededResources[i], countOfNeededResources[i]));
            }
        }

        this.moneyUpkeep = moneyUpkeep;

        this.maxActionPoints = maxActionPoints;

        this.isFlying = isFlying;
        this.whereCanMove = whereCanMove;
        this.moveModifier = moveModifier;
        this.howMuchAffectedByLandAdditionalAPCost = howMuchAffectedByLandAdditionalAPCost;
        this.howMuchAffectedByFloraAdditionalAPCost = howMuchAffectedByFloraAdditionalAPCost;
        this.howMuchAffectedByResourceAdditionalAPCost = howMuchAffectedByResourceAdditionalAPCost;

        this.isRanged = isRanged;
        this.projectile = projectile;
        this.rangeOfAttack = rangeOfAttack;
        this.rangedAttack = rangedAttack;

        this.attackMelee = attackMelee;
        this.defenceMelee = defenceMelee;
        this.defenceRanged = defenceRanged;
        this.maxHitPoints = maxHitPoints;
        this.visionRange = visionRange;
        this.maxNumberOfAttacks = maxNumberOfAttacks;

        this.Abilities = new ArrayList<>(Arrays.stream(Abilities).toList());
        this.workEfficiency = workEfficiency;


        if(tags == null){
            this.tags = null;
        }else {
            this.tags.addAll(Arrays.asList(tags));
        }
    }

    UnitPattern(){

    }

    public void doUpkeep(Player owner){
        owner.getMoney(-moneyUpkeep);
        //TODO add special resource
    }


    public UnitPattern MakeCopyToPlayer(){
        UnitPattern unitPattern = new UnitPattern();
        unitPattern.NameOfUnit = this.NameOfUnit;

        unitPattern.productionCost = this.productionCost;
        unitPattern.moneyCost = this.moneyCost;
        unitPattern.NeededResources = this.NeededResources;

        unitPattern.moneyUpkeep = this.moneyUpkeep;

        unitPattern.maxActionPoints = this.maxActionPoints;

        unitPattern.isFlying = this.isFlying;
        unitPattern.whereCanMove = this.whereCanMove;
        unitPattern.moveModifier = this.moveModifier;
        unitPattern.howMuchAffectedByLandAdditionalAPCost = this.howMuchAffectedByLandAdditionalAPCost;
        unitPattern.howMuchAffectedByFloraAdditionalAPCost = this.howMuchAffectedByFloraAdditionalAPCost;
        unitPattern.howMuchAffectedByResourceAdditionalAPCost = this.howMuchAffectedByResourceAdditionalAPCost;

        unitPattern.isRanged = this.isRanged;
        unitPattern.projectile = this.projectile;
        unitPattern.rangeOfAttack = this.rangeOfAttack;
        unitPattern.rangedAttack = this.rangedAttack;

        unitPattern.attackMelee = this.attackMelee;
        unitPattern.defenceMelee = this.defenceMelee;
        unitPattern.defenceRanged = this.defenceRanged;
        unitPattern.maxHitPoints = this.maxHitPoints;
        unitPattern.visionRange = this.visionRange;
        unitPattern.maxNumberOfAttacks = this.maxNumberOfAttacks;

        unitPattern.Abilities = this.Abilities;
        unitPattern.workEfficiency = this.workEfficiency;

        unitPattern.tags = this.tags;
        return unitPattern;
    }

    public static UnitPattern Settler = new UnitPattern(
            "Settler",
            250,
            1000,
            null,
            null,

            10,

            3,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            1,
            1,
            1,
            10,
            10,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern Worker = new UnitPattern(
            "Worker",
            250,
            1000,
            null,
            null,

            1,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            1,
            1,
            1,
            10,
            2,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanSettler = new UnitPattern(
            "HumanSettler",
            250,
            1000,
            null,
            null,

            10,

            3,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            1,
            1,
            1,
            6,
            2,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanWorker = new UnitPattern(
            "HumanWorker",
            100,
            200,
            null,
            null,

            1,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            1,
            1,
            1,
            5,
            1,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanMilitia = new UnitPattern(
            "HumanMilitia",
            35,
            100,
            null,
            null,

            1,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            3,
            2,
            1,
            7,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanSpearman = new UnitPattern(
            "HumanSpearman",
            50,
            150,
            null,
            null,

            3,

            3,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            4,
            6,
            4,
            8,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanSwordsman = new UnitPattern(
            "HumanSwordsman",
            70,
            220,
            null,
            null,

            5,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            6,
            7,
            5,
            8,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanArcher = new UnitPattern(
            "HumanArcher",
            50,
            150,
            null,
            null,

            3,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Arrow,
            1,
            3,

            1,
            2,
            2,
            6,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanMusketeer = new UnitPattern(
            "HumanMusketeer",
            200,
            600,
            null,
            null,

            7,

            3,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Arrow,
            1,
            8,

            1,
            2,
            2,
            6,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanCatapult = new UnitPattern(
            "HumanCatapult",
            120,
            360,
            null,
            null,

            4,

            1,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Rock,
            2,
            6,

            1,
            2,
            2,
            5,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanCannon = new UnitPattern(
            "HumanCannon",
            300,
            900,
            null,
            null,

            9,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.CannonBall,
            3,
            11,

            1,
            2,
            2,
            6,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanCavalry = new UnitPattern(
            "HumanCavalry",
            100,
            300,
            null,
            null,

            5,

            4,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            6,
            4,
            3,
            10,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanTank = new UnitPattern(
            "HumanCavalry",
            1000,
            3000,
            null,
            null,

            12,

            3,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            4,
            10,
            10,
            14,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern GriffinRider = new UnitPattern(
            "GriffinRider",
            1000,
            3000,
            null,
            null,

            12,

            3,

            true,
            WhereCanBe.noPreference,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            6,
            7,
            5,
            10,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanTrireme = new UnitPattern(
            "HumanTrireme",
            300,
            900,
            null,
            null,

            3,

            3,

            false,
            WhereCanBe.inShores,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            3,
            3,
            3,
            3,
            3,
            1,

            new String[]{GetCargoSmall.nameOfAbility},
            1,
            new  String[]{Tag.big}
    ).initUnitPattern();

    public static UnitPattern HumanCaravel = new UnitPattern(
            "HumanCaravel",
            600,
            1800,
            null,
            null,

            8,

            4,

            false,
            WhereCanBe.inDeepOceanAndShores,
            1,
            1,
            1,
            1,

            true,
            Projectile.CannonBall,
            3,
            10,

            5,
            5,
            5,
            5,
            3,
            2,

            new String[]{GetCargoSmall.nameOfAbility},
            1,
            new  String[]{Tag.big}
    ).initUnitPattern();

}
