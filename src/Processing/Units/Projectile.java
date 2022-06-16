package Processing.Units;

import Processing.TileMap.TileUtils.TypeOfLand;
import Processing.Utilits.Wealth;
import Processing.Utilits.WhereCanBe;

import java.util.LinkedHashMap;

public class Projectile {

    public String name;
    public WhereCanBe whereCanBe;


    public Projectile(String name, WhereCanBe whereCanBe){
        this.name = name;
        this.whereCanBe = whereCanBe;
        AllTypeOfProjectile.put(name, this);
    }

    public static LinkedHashMap<String, Projectile> AllTypeOfProjectile = new LinkedHashMap<>();
    public static final Projectile LightFlying = new Projectile("LightFlying", WhereCanBe.noPreference);
    public static final Projectile LightLand = new Projectile("LightLand", WhereCanBe.noPreference);
    //TODO Make whereCanBe for LightLand where excluded hills mountains and forest
}