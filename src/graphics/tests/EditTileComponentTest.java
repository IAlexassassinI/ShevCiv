package graphics.tests;

import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import graphics.components.button.SelectButtonComponent;
import graphics.components.camera.Camera;
import graphics.components.tiledmap.EditMapComponent;
import graphics.components.tiledmap.EditMode;
import graphics.components.tiledmap.EditTileComponent;
import graphics.components.tiledmap.TileComponent;
import graphics.loads.Images;
import org.newdawn.slick.*;

public class EditTileComponentTest extends BasicGame {

    //TileComponent tile1;
    //EditTileComponent tile2;
    Camera camera;
    EditMapComponent editMap;
    SelectButtonComponent selectButtonComponent;

    public EditTileComponentTest(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        //tile1 = new TileComponent(gameContainer, new Tile(), 100, 100);
        //tile2 = new EditTileComponent(gameContainer, new Tile(), 300, 100);
        //tile2.setEditMode(EditMode.NONE);
        //selectButtonComponent = new SelectButtonComponent(camer)
        editMap = new EditMapComponent(gameContainer, new GameMap(128, 128), 0, 0);
        editMap.setEditMode(EditMode.EDIT_TYPE_OF_LAND);
        camera = new Camera(gameContainer, 50,50,500,500, editMap);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        camera.render(gameContainer, graphics);
        //editMap.render(gameContainer, graphics);
        //Images.typesOfLandSpriteSheet.startUse();
        //tile1.render(gameContainer, graphics);
        //tile2.render(gameContainer, graphics);
        //Images.resourcesSpriteSheet.getSprite(1,0).draw(200, 100, 100, 100);
        //Images.typesOfLandSpriteSheet.endUse();
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new EditTileComponentTest(""));
        app.setDisplayMode(1280, 720, false);
        app.setVSync(true);
        app.start();
    }
}
