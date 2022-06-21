package Processing.Units.Ability;

import Processing.Units.Unit;

import java.util.LinkedList;

public abstract class SpecialAbility {

    /*
    public static String nameOfAbility;
    public static String description;
    public static double Cooldown;
     */


    public static LinkedList<String> AllSpecialAbilities = new LinkedList<>();

    SpecialAbility(){

    }

    static public void initAbilityList(){

        AllSpecialAbilities.add(ConstructSomethingOnTile.nameOfAbility);
        AllSpecialAbilities.add(GetCargoSmall.nameOfAbility);
        AllSpecialAbilities.add(Colonize.nameOfAbility);

    }

    static public SpecialAbility fabricateSpecialAbility(String nameOfAbility, Unit unitToLink){
        switch(AllSpecialAbilities.indexOf(nameOfAbility)){
            case 0:
                return new ConstructSomethingOnTile(unitToLink);
            case 1:
                return new GetCargoSmall(unitToLink);
            case 2:
                return new Colonize(unitToLink);
            default:
                return null;
        }
    }

}
