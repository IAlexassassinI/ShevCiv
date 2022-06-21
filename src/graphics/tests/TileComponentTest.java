package graphics.tests;

import Processing.TileMap.Tile;
import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.*;

public class TileComponentTest extends BasicGame {

    TileComponent tile1;
    TileComponent tile2;

    public TileComponentTest(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        tile1 = new TileComponent(gameContainer, new Tile(), 0, 0);
        tile2 = new TileComponent(gameContainer, new Tile(), 0, 100);
        tile1.scale(2f, 2f, 0, 0);
        tile2.scale(2f, 2f, 0, 0);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.setAntiAlias(false);
        graphics.scale(tile1.getWidth() / tile1.getImageWidth(), tile1.getWidth() / tile1.getImageWidth());
        //graphics.setAntiAlias(false);
        this.tile1.render(gameContainer, graphics);
        this.tile2.render(gameContainer, graphics);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new TileComponentTest(""));
        app.setDisplayMode(1280, 720, false);
        app.start();
    }
}
