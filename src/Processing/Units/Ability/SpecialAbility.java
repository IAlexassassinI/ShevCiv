package Processing.Units.Ability;

import Processing.Units.Unit;

import java.io.Serializable;
import java.util.LinkedList;

public abstract class SpecialAbility implements Serializable {
    static final long serialVersionUID = 17L;
    /*
    public static String nameOfAbility;
    public static String description;
    public static double Cooldown;
     */

    public double currentCooldown;

    public void decreaseCooldown(){
        if(this.currentCooldown > 0){
            this.currentCooldown--;
        }
    }

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
