package Processing.Units;

import java.util.HashMap;

public abstract class SpecialAbility {

    String nameOfAbility;


    public SpecialAbility(String nameOfAbility){
        this.nameOfAbility = nameOfAbility;
        AllSpecialAbilities.put(this.nameOfAbility, this);
    }

    public static HashMap<String, SpecialAbility> AllSpecialAbilities = new HashMap<>();

    abstract public int doSomething(Object[] params);


}
