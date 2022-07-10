package Processing.Units;

import Processing.Player.Player;
import Processing.TileMap.TileUtils.Resource;
import Processing.Units.Ability.Colonize;
import Processing.Units.Ability.ConstructSomethingOnTile;
import Processing.Units.Ability.GetCargoSmall;
import Processing.Utilits.Tag;
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

    @Override
    public String toString() {
        return this.NameOfUnit;
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

            0,

            100,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.none,
            10,
            1000,

            0,
            0,
            0,
            10000,
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
            100,
            1000,
            null,
            null,

            5,

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

            0,
            0,
            0,
            50,
            3,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanWorker = new UnitPattern(
            "HumanWorker",
            45,
            200,
            null,
            null,

            2,

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
            50,
            2,
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

            2,

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
            70,
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

            2.5,

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

            4,
            6,
            5,
            100,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanSwordsman = new UnitPattern(
            "HumanSwordsman",
            110,
            220,
            null,
            null,

            5.5,

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

            7,
            9,
            4,
            110,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanArcher = new UnitPattern(
            "HumanArcher",
            70,
            150,
            null,
            null,

            3.5,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Arrow,
            2,
            4,

            1,
            1,
            1,
            70,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanMusketeer = new UnitPattern(
            "HumanMusketeer",
            110,
            600,
            null,
            null,

            5.5,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Bullet,
            2,
            9,

            1,
            2,
            2,
            90,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanCatapult = new UnitPattern(
            "HumanCatapult",
            125,
            360,
            null,
            null,

            6,

            1,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Rock,
            3,
            11,

            1,
            2,
            2,
            110,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanMortar = new UnitPattern(
            "HumanMortar",
            230,
            900,
            null,
            null,

            11.5,

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
            20,

            1,
            2,
            2,
            150,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanCavalry = new UnitPattern(
            "HumanCavalry",
            80,
            300,
            null,
            null,

            4,

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
            4,
            3,
            100,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanTank = new UnitPattern(
            "HumanTank",
            350,
            3000,
            null,
            null,

            17.5,

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

            30,
            20,
            15,
            350,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern GriffinRider = new UnitPattern(
            "GriffinRider",
            280,
            3000,
            null,
            null,

            14,

            3,

            true,
            WhereCanBe.noPreference,
            1,
            0,
            0,
            0,

            false,
            Projectile.none,
            0,
            0,

            17,
            8,
            0,
            120,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanTrireme = new UnitPattern(
            "HumanTrireme",
            200,
            900,
            null,
            null,

            10,

            3,

            false,
            WhereCanBe.inShores,
            1,
            1,
            1,
            1,

            false,
            Projectile.Arrow,
            0,
            0,

            12,
            4,
            10,
            200,
            3,
            1,

            new String[]{GetCargoSmall.nameOfAbility},
            1,
            new  String[]{Tag.big}
    ).initUnitPattern();

    public static UnitPattern HumanCaravel = new UnitPattern(
            "HumanCaravel",
            280,
            1800,
            null,
            null,

            14,

            3,

            false,
            WhereCanBe.inDeepOceanAndShores,
            1,
            1,
            1,
            1,

            true,
            Projectile.CannonBall,
            2,
            10,

            5,
            7,
            6,
            280,
            3,
            1,

            new String[]{GetCargoSmall.nameOfAbility},
            1,
            new  String[]{Tag.big}
    ).initUnitPattern();

    //////////////////////////////////////////////////////

    public static UnitPattern ElvenSettler = new UnitPattern(
            "ElvenSettler",
            135,
            1000,
            null,
            null,

            7,

            3,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            -0.5,
            1,

            true,
            Projectile.Arrow,
            1,
            1,

            0,
            0,
            0,
            50,
            3,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenWorker = new UnitPattern(
            "ElvenWorker",
            60,
            200,
            null,
            null,

            3,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            -0.5,
            1,

            true,
            Projectile.Arrow,
            1,
            1,

            1,
            1,
            1,
            50,
            2,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            1.25,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenMilitia = new UnitPattern(
            "ElvenMilitia",
            70,
            100,
            null,
            null,

            3.5,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            -0.5,
            1,

            true,
            Projectile.Arrow,
            1,
            2,

            3,
            2,
            2,
            70,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenBladeDancer = new UnitPattern(
            "ElvenBladeDancer",
            150,
            100,
            null,
            null,

            7.5,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            -0.5,
            1,

            false,
            Projectile.none,
            0,
            0,

            6,
            5,
            4,
            150,
            2,
            2,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenArcher = new UnitPattern(
            "ElvenArcher",
            120,
            150,
            null,
            null,

            6,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            -0.5,
            1,

            true,
            Projectile.Arrow,
            2,
            6,

            1,
            0,
            4,
            70,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenSharpshooter = new UnitPattern(
            "ElvenSharpshooter",
            140,
            600,
            null,
            null,

            7,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            -0.5,
            1,

            true,
            Projectile.Arrow,
            2,
            7,

            1,
            1,
            5,
            70,
            2,
            2,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenScorpion = new UnitPattern(
            "ElvenScorpion",
            160,
            360,
            null,
            null,

            8,

            1,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            -0.5,
            1,

            true,
            Projectile.Arrow,
            3,
            8,

            1,
            2,
            2,
            140,
            3,
            2,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenMage = new UnitPattern(
            "ElvenMage",
            230,
            900,
            null,
            null,

            11.5,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            -0.5,
            1,

            true,
            Projectile.BigFireball,
            2,
            22,

            1,
            2,
            2,
            150,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenCavalry = new UnitPattern(
            "ElvenCavalry",
            110,
            300,
            null,
            null,

            5.5,

            3,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            -0.5,
            1,

            true,
            Projectile.Arrow,
            2,
            4,

            2,
            3,
            2,
            110,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenHawkRider = new UnitPattern(
            "ElvenHawkRider",
            280,
            3000,
            null,
            null,

            14,

            3,

            true,
            WhereCanBe.noPreference,
            1,
            0,
            0,
            0,

            true,
            Projectile.Arrow,
            2,
            6,

            2,
            6,
            0,
            100,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenCarrier = new UnitPattern(
            "ElvenCarrier",
            200,
            1800,
            null,
            null,

            10,

            3,

            false,
            WhereCanBe.inDeepOceanAndShores,
            1,
            1,
            1,
            1,

            false,
            Projectile.CannonBall,
            3,
            10,

            2,
            5,
            5,
            150,
            3,
            1,

            new String[]{GetCargoSmall.nameOfAbility},
            1,
            new  String[]{Tag.big}
    ).initUnitPattern();

    ////////////////////////////////////////////////////////////////////

    public static UnitPattern DwarfSettler = new UnitPattern(
            "DwarfSettler",
            145,
            1000,
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

            false,
            Projectile.none,
            0,
            0,

            0,
            3,
            3,
            90,
            3,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfWorker = new UnitPattern(
            "DwarfWorker",
            70,
            200,
            null,
            null,

            3.5,

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
            3,
            3,
            90,
            2,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            2,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfMilitia = new UnitPattern(
            "DwarfMilitia",
            70,
            100,
            null,
            null,

            3.5,

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
            5,
            3,
            110,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfHammerman = new UnitPattern(
            "DwarfHammerman",
            120,
            220,
            null,
            null,

            6,

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

            5,
            12,
            6,
            150,
            2,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            1.5,
            new  String[]{Tag.small}
    ).initUnitPattern();


    public static UnitPattern DwarfSwordsman = new UnitPattern(
            "DwarfSwordsman",
            120,
            220,
            null,
            null,

            6,

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

            5,
            15,
            8,
            200,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfMusketeer = new UnitPattern(
            "DwarfMusketeer",
            130,
            600,
            null,
            null,

            6.5,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Bullet,
            2,
            8,

            2,
            5,
            5,
            130,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfMortar = new UnitPattern(
            "DwarfMortar",
            320,
            360,
            null,
            null,

            16,

            1.5,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.CannonBall,
            3,
            18,

            2,
            3,
            4,
            200,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfJavelin = new UnitPattern(
            "DwarfJavelin",
            260,
            900,
            null,
            null,

            13,

            1.8,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Rocket,
            2,
            35,

            2,
            3,
            3,
            170,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfTank = new UnitPattern(
            "DwarfTank",
            550,
            3000,
            null,
            null,

            27.5,

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

            32,
            25,
            17,
            450,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern Zeppelin = new UnitPattern(
            "Zeppelin",
            300,
            3000,
            null,
            null,

            15,

            3,

            true,
            WhereCanBe.noPreference,
            1,
            0,
            0,
            0,

            false,
            Projectile.none,
            0,
            0,

            4,
            6,
            6,
            200,
            3,
            1,

            new String[]{GetCargoSmall.nameOfAbility},
            1,
            new  String[]{Tag.big}
    ).initUnitPattern();

    public static UnitPattern DemonSettler = new UnitPattern(
            "DemonSettler",
            145,
            1000,
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

            false,
            Projectile.none,
            0,
            0,

            0,
            0,
            0,
            100,
            3,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DemonSlave = new UnitPattern(
            "DemonSlave",
            70,
            200,
            null,
            null,

            3.5,

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

            2,
            0,
            0,
            100,
            2,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DemonMilitia = new UnitPattern(
            "DemonMilitia",
            60,
            100,
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

            false,
            Projectile.none,
            0,
            0,

            4,
            0,
            0,
            160,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DemonSwordsman = new UnitPattern(
            "DemonSwordsman",
            70,
            220,
            null,
            null,

            3.5,

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
            0,
            0,
            210,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    //https://guides.gamepressure.com/heroes3hd/gfx/word/355997651.jpg
    public static UnitPattern Magog = new UnitPattern(
            "Magog",
            80,
            150,
            null,
            null,

            4,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Fireball,
            1,
            6,

            3,
            0,
            0,
            120,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HellCannon = new UnitPattern(
            "HellCannon",
            230,
            360,
            null,
            null,

            11.5,

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
            19,

            4,
            0,
            0,
            300,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern Cerberus = new UnitPattern(
            "Cerberus",
            200,
            300,
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

            3,
            0,
            0,
            400,
            2,
            3,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ArchDevil = new UnitPattern(
            "ArchDevil",
            666,
            3000,
            null,
            null,

            33,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            1,
            6,

            6,
            6,
            6,
            666,
            1,
            6,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DemonSuccubi = new UnitPattern(
            "DemonSuccubi",
            690,
            3000,
            null,
            null,

            34,

            3,

            true,
            WhereCanBe.noPreference,
            1,
            0,
            0,
            0,

            true,
            Projectile.none,
            1,
            6,

            6,
            6,
            6,
            180,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern Kraken = new UnitPattern(
            "Kraken",
            500,
            900,
            null,
            null,

            25,

            2,

            false,
            WhereCanBe.inDeepOceanAndShores,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            4,
            0,
            0,
            1500,
            3,
            8,

            new String[]{GetCargoSmall.nameOfAbility},
            1,
            new  String[]{Tag.big}
    ).initUnitPattern();

    //////////////////////////////////////////////////////

    public static UnitPattern OrkSettler = new UnitPattern(
            "OrkSettler",
            0,
            0,
            null,
            null,

            0,

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

            3,
            2,
            1,
            100,
            4,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern OrkPeon = new UnitPattern(
            "OrkPeon",
            0,
            0,
            null,
            null,

            0,

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
            100,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern OrkSwordsman = new UnitPattern(
            "OrkSwordsman",
            0,
            0,
            null,
            null,

            0,

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

            8,
            7,
            0,
            120,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern OrkHunter = new UnitPattern(
            "OrkHunter",
            0,
            0,
            null,
            null,

            0,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Arrow,
            2,
            2,

            2,
            0,
            0,
            80,
            3,
            2,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern OrkCatapult = new UnitPattern(
            "OrkCatapult",
            0,
            0,
            null,
            null,

            0,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            true,
            Projectile.Rock,
            3,
            12,

            4,
            3,
            0,
            140,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    //Looted musket
    public static UnitPattern OrkWolfRider = new UnitPattern(
            "OrkWolfRider",
            0,
            0,
            null,
            null,

            0,

            3,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.Bullet,
            2,
            9,

            8,
            3,
            2,
            120,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern VeryBigOrk = new UnitPattern(
            "VeryBigOrk",
            0,
            0,
            null,
            null,

            0,

            2,

            false,
            WhereCanBe.onLandNoMountain,
            1,
            1,
            1,
            1,

            false,
            Projectile.Rock,
            3,
            11,

            30,
            15,
            10,
            550,
            3,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    //https://static.wikia.nocookie.net/wow/images/4/4d/Wyvern_orco_fel.jpg/revision/latest?cb=20140114170024&path-prefix=es
    public static UnitPattern OrkWyvern = new UnitPattern(
            "OrkWyvern",
            280,
            0,
            null,
            null,

            0,

            3,

            true,
            WhereCanBe.noPreference,
            1,
            0,
            0,
            0,

            false,
            Projectile.none,
            0,
            0,

            18,
            4,
            0,
            140,
            4,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern OrkBarge = new UnitPattern(
            "OrkBarge",
            0,
            0,
            null,
            null,

            0,

            3,

            false,
            WhereCanBe.inDeepOceanAndShores,
            1,
            1,
            1,
            1,

            false,
            Projectile.none,
            0,
            0,

            2,
            4,
            4,
            200,
            3,
            1,

            new String[]{GetCargoSmall.nameOfAbility},
            1,
            new  String[]{Tag.big}
    ).initUnitPattern();

}
