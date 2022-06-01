package Processing.Units;

import Processing.Player.Player;

import java.io.Serializable;

public class Unit implements Serializable {
    static final long serialVersionUID = 3L;

    UnitPattern typeOfUnit;
    Player owner;

    int currentActionPoints;


}
