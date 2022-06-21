package graphics.tests;

import Processing.TileMap.Tile;
import Processing.TileMap.TileUtils.TypeOfLand;
import graphics.components.tiledmap.TileComponent;
import graphics.loads.Images;
import graphics.panels.*;
import org.lwjgl.opengl.ARBPointParameters;
import org.newdawn.slick.*;

public class ScrollButtonPanelTest extends BasicGame {

    private ScrollButtonPanel scrollButtonPanel;
    private SelectPanel selectPanel;

    public ScrollButtonPanelTest(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        /*new Images();
        Tile[] tiles = new Tile[TypeOfLand.AllTypeOfLand.size()];
        int num = 0;
        for(String key : TypeOfLand.AllTypeOfLand.keySet()) {
            tiles[num] = new Tile();
            tiles[num++].setTypeOfLand(TypeOfLand.AllTypeOfLand.get(key));
        }
        this.selectPanel = new SelectPanel(Orientation.HORIZONTAL, 0, 600, tiles.length * 150, 100, tiles.length * 150);
        //Tile tile = new Tile();
        //tile.setTypeOfLand(TypeOfLand.FlatLand);
        for(int i = 0; i < tiles.length; i++) {
            this.selectPanel.add(new TileComponent(gameContainer, tiles[i], 600, 0), 100);
            this.selectPanel.add(new EmptyPanel(0,0,0,0,50), 50);
        }*/
        ImagePanel imagePanel = new ImagePanel(new Image("assets/graphics/fortest.png"), 0, 0, 600, 100, 0);
        this.scrollButtonPanel = new ScrollButtonPanel(gameContainer, new Image("assets/graphics/buttons/scroll_buttons/left_scroll_button_30_120.png"), new Image("assets/graphics/buttons/scroll_buttons/right_scroll_button_30_120.png"), 0,0,160,120,160);
        this.scrollButtonPanel.add(imagePanel, 0);
    }

    @Override
    public void update(GameContainer gameContainer, int delta) throws SlickException {
        this.scrollButtonPanel.update(gameContainer, delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.scrollButtonPanel.render(gameContainer, graphics);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new ScrollButtonPanelTest(""));
        app.setDisplayMode(1280, 720, false);
        app.start();
    }
}
