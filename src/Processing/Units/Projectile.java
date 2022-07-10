package Processing.Units;

import Processing.TileMap.TileUtils.TypeOfLand;
import Processing.Utilits.Wealth;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Projectile implements Serializable {
    static final long serialVersionUID = 19L;
    public String name;
    public WhereCanBe whereCanBe;


    public Projectile(String name, WhereCanBe whereCanBe){
        this.name = name;
        this.whereCanBe = whereCanBe;
        AllTypeOfProjectile.put(name, this);
    }

    public static LinkedHashMap<String, Projectile> AllTypeOfProjectile = new LinkedHashMap<>();
    public static final Projectile none = new Projectile("none", WhereCanBe.noPreference);
    public static final Projectile LightFlying = new Projectile("LightFlying", WhereCanBe.noPreference);
    public static final Projectile LightLand = new Projectile("LightLand", WhereCanBe.landLight);
    public static final Projectile Arrow = new Projectile("Arrow", WhereCanBe.landLight);
    public static final Projectile Bullet = new Projectile("Bullet", WhereCanBe.landLight);
    public static final Projectile Rock = new Projectile("Rock", WhereCanBe.noMountain);
    public static final Projectile CannonBall = new Projectile("Cannonball", WhereCanBe.noPreference);
    public static final Projectile Fireball = new Projectile("Fireball", WhereCanBe.noMountain);
    public static final Projectile BigFireball = new Projectile("BigFireball", WhereCanBe.noMountain);
    public static final Projectile Rocket = new Projectile("Rocket", WhereCanBe.noPreference);

    public void LoadSetTo(Object object){
        Projectile Projectile = (Projectile) object;
        this.name = Projectile.name;
    }

}
