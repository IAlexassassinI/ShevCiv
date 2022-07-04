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

            10,

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
            4,
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

            4,
            6,
            4,
            100,
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

            6,
            9,
            5,
            110,
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

            7,

            3,

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

            6,

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

            3,
            4,
            3,
            100,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HumanTank = new UnitPattern(
            "HumanTank",
            450,
            3000,
            null,
            null,

            13,

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

    //TODO here
    public static UnitPattern GriffinRider = new UnitPattern(
            "GriffinRider",
            280,
            3000,
            null,
            null,

            9,

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
            280,
            900,
            null,
            null,

            9,

            3,

            false,
            WhereCanBe.inDeepOceanAndShores,
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
            4,
            1,

            new String[]{GetCargoSmall.nameOfAbility},
            1,
            new  String[]{Tag.big}
    ).initUnitPattern();

    public static UnitPattern HumanCaravel = new UnitPattern(
            "HumanCaravel",
            350,
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
            2,
            10,

            5,
            7,
            6,
            280,
            3,
            2,

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

            1,

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
            5,
            2,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenMilitia = new UnitPattern(
            "ElvenMilitia",
            60,
            100,
            null,
            null,

            1,

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
            1,
            70,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenBladeDancer = new UnitPattern(
            "ElvenBladeDancer",
            140,
            100,
            null,
            null,

            5,

            3,

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
            -0.5,
            1,

            true,
            Projectile.Arrow,
            2,
            4,

            1,
            1,
            1,
            7,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenSharpshooter = new UnitPattern(
            "ElvenSharpshooter",
            110,
            600,
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
            Projectile.Bullet,
            2,
            9,

            1,
            2,
            2,
            9,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenScorpion = new UnitPattern(
            "ElvenScorpion",
            125,
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
            3,
            11,

            1,
            2,
            2,
            11,
            1,
            1,

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
            20,

            1,
            2,
            2,
            15,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ElvenCavalry = new UnitPattern(
            "ElvenCavalry",
            80,
            300,
            null,
            null,

            6,

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

            3,
            4,
            3,
            10,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HawkRider = new UnitPattern(
            "HawkRider",
            300,
            3000,
            null,
            null,

            9,

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

    public static UnitPattern ElvenCarrier = new UnitPattern(
            "ElvenCaravel",
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

    ////////////////////////////////////////////////////////////////////

    public static UnitPattern DwarfSettler = new UnitPattern(
            "DwarfSettler",
            100,
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
            5,
            3,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfWorker = new UnitPattern(
            "DwarfWorker",
            45,
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
            2,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfMilitia = new UnitPattern(
            "DwarfMilitia",
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

    public static UnitPattern DwarfHammerman = new UnitPattern(
            "DwarfSwordsman",
            70,
            220,
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

            6,
            9,
            5,
            11,
            2,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();


    public static UnitPattern DwarfSwordsman = new UnitPattern(
            "DwarfSwordsman",
            70,
            220,
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

            6,
            9,
            5,
            11,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfMusketeer = new UnitPattern(
            "DwarfMusketeer",
            110,
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
            Projectile.Bullet,
            2,
            9,

            1,
            2,
            2,
            9,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfMortar = new UnitPattern(
            "DwarfMortar",
            125,
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
            3,
            11,

            1,
            2,
            2,
            11,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfJavelin = new UnitPattern(
            "DwarfRocketLauncher",
            230,
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
            20,

            1,
            2,
            2,
            15,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DwarfTank = new UnitPattern(
            "DwarfTank",
            450,
            3000,
            null,
            null,

            13,

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
            35,
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

            9,

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

    //TODO
    //TODO
    //TODO

    public static UnitPattern DemonSettler = new UnitPattern(
            "DemonSettler",
            100,
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
            5,
            3,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DemonSlave = new UnitPattern(
            "DemonWorker",
            45,
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
            2,
            1,

            new String[]{ConstructSomethingOnTile.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern DemonMilitia = new UnitPattern(
            "DemonMilitia",
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

    public static UnitPattern DemonSwordsman = new UnitPattern(
            "DemonSwordsman",
            70,
            220,
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

            6,
            9,
            5,
            11,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    //https://guides.gamepressure.com/heroes3hd/gfx/word/355997651.jpg
    public static UnitPattern DemonMagog = new UnitPattern(
            "DemonMagog",
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
            2,
            4,

            1,
            1,
            1,
            7,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern HellCannon = new UnitPattern(
            "HellCannon",
            125,
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
            3,
            11,

            1,
            2,
            2,
            11,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern Cerberus = new UnitPattern(
            "Cerberus",
            80,
            300,
            null,
            null,

            6,

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

            3,
            4,
            3,
            10,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern ArchDevil = new UnitPattern(
            "ArchDevil",
            450,
            3000,
            null,
            null,

            13,

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
            35,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern Succubus = new UnitPattern(
            "Succubus",
            300,
            3000,
            null,
            null,

            9,

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

    public static UnitPattern Kraken = new UnitPattern(
            "Kraken",
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

    //////////////////////////////////////////////////////

    public static UnitPattern OrkSettler = new UnitPattern(
            "OrkSettler",
            100,
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
            5,
            3,
            1,

            new String[]{Colonize.nameOfAbility},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern OrkPeon = new UnitPattern(
            "OrkPeon",
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

    public static UnitPattern OrkSwordsman = new UnitPattern(
            "OrkSwordsman",
            50,
            150,
            null,
            null,

            2,

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
            10,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern OrkHunter = new UnitPattern(
            "OrkHunter",
            70,
            220,
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

            6,
            9,
            5,
            11,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern OrkCatapult = new UnitPattern(
            "OrkCatapult",
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
            2,
            4,

            1,
            1,
            1,
            7,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    //Looted musket
    public static UnitPattern OrkWolfRider = new UnitPattern(
            "OrkWolfRider",
            110,
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
            Projectile.Bullet,
            2,
            9,

            1,
            2,
            2,
            9,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern VeryBigOrk = new UnitPattern(
            "VeryBigOrk",
            125,
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
            3,
            11,

            1,
            2,
            2,
            11,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    //https://static.wikia.nocookie.net/wow/images/4/4d/Wyvern_orco_fel.jpg/revision/latest?cb=20140114170024&path-prefix=es
    public static UnitPattern OrkWyvern = new UnitPattern(
            "OrkBarge",
            80,
            300,
            null,
            null,

            6,

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

            3,
            4,
            3,
            10,
            2,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

    public static UnitPattern OrkBarge = new UnitPattern(
            "OrkBarge",
            450,
            3000,
            null,
            null,

            13,

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
            35,
            1,
            1,

            new String[]{},
            1,
            new  String[]{Tag.small}
    ).initUnitPattern();

}
