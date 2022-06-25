package graphics.loads;

import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.TileMap.TileUtils.TypeOfLand;
import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.util.HashMap;

public class Images {

    public static HashMap<String, Image> typesOfLand;
    public static SpriteSheet typesOfLandSpriteSheet;
    public static SpriteSheet typesOfFloraSpriteSheet;
    public static SpriteSheet resourcesSpriteSheet;
    public static SpriteSheet categorySpriteSheet;
    public static Image buttonNewGame;
    public static Image buttonEditMap;
    public static Image buttonExit;
    //public static HashMap<String, Image> typesOfFlora;
    //public static HashMap<String, Image> resources;

    static {
        try {
            Image typesOfLandImage = new Image("assets/graphics/typeOfLand/typeOfLands.png");//, Color.white
            typesOfLandSpriteSheet = new SpriteSheet(typesOfLandImage, TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);
            Image typesOfFloraImage = new Image("assets/graphics/typeOfFlora/typeOfFloras.png");//, Color.white
            typesOfFloraSpriteSheet = new SpriteSheet(typesOfFloraImage, TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);
            Image resourcesImage = new Image("assets/graphics/resource/resources.png");//, Color.white
            resourcesSpriteSheet = new SpriteSheet(resourcesImage, TileComponent.STANDARD_WIDTH, TileComponent.STANDARD_HEIGHT);
            categorySpriteSheet = new SpriteSheet("assets/graphics/categorySpriteSheet.png", 50, 50);
            buttonNewGame = new Image("assets/graphics/buttons/new_game.png");
            buttonEditMap = new Image("assets/graphics/buttons/edit_map.png");
            buttonExit = new Image("assets/graphics/buttons/exit.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
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
