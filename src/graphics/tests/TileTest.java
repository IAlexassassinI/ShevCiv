package graphics.tests;

import graphics.components.tiledmap.SelectStyle;
import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.*;

public class TileTest extends BasicGame {

    TileComponent[][] tile;

    public TileTest(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        this.tile = new TileComponent[3][3];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                //this.tile[i][j] = new TileComponent(gameContainer, new Image("assets/graphics/tiles/flat.png"), 100 * i, 100 * j);
                this.tile[i][j].setSelectStyle(SelectStyle.WHITE_BORDER);
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if(i == 1 && j == 1)
                this.tile[i][j].render(gameContainer, graphics);
            }
        }
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new TileTest("TileTest"));
        app.setDisplayMode(1280, 720, false);
        app.start();
    }
}
