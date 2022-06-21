package graphics.loads;

import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.TileMap.TileUtils.TypeOfLand;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.HashMap;

public class Images {

    public static HashMap<String, Image> typesOfLand;
    //public static HashMap<String, Image> typesOfFlora;
    //public static HashMap<String, Image> resources;

    static {
        typesOfLand = new HashMap<>();
        System.out.println("start");
        for(String key : TypeOfLand.AllTypeOfLand.keySet()) {
            try {
                typesOfLand.put(key, new Image("assets/graphics/typeOfLand/" + key + ".jpg"));
                System.out.println("success");
            } catch (SlickException e) {
                System.out.println("fail");
            }
        }
    }

    public static Image getTypeOfLandImage(String name) {
        return typesOfLand.get(name);
    }

}
