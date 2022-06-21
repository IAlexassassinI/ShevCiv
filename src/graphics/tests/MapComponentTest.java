package graphics.tests;

import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import Processing.TileMap.TileUtils.TypeOfLand;
import graphics.components.camera.Camera;
import graphics.components.selectComponents.SelectComponent;
import graphics.components.tiledmap.MapComponent;
import graphics.components.tiledmap.TileComponent;
import graphics.loads.Images;
import graphics.panels.EmptyPanel;
import graphics.panels.Orientation;
import graphics.panels.SelectPanel;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

import java.nio.channels.spi.SelectorProvider;
import java.util.LinkedHashMap;

public class MapComponentTest extends BasicGame implements ComponentListener {

    private MapComponent map;
    private SelectPanel selectPanel;
    private Camera camera;

    public MapComponentTest(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        new Images();
        GameMap gameMap = new GameMap(100, 100);
        this.map = new MapComponent(gameContainer, gameMap, 0, 0);
        this.map.addListener(this);
        this.camera = new Camera(gameContainer, 50, 50, 500, 500, map);
        //System.out.println();
        Tile[] tiles = new Tile[TypeOfLand.AllTypeOfLand.size()];
        int num = 0;
        for(String key : TypeOfLand.AllTypeOfLand.keySet()) {
            tiles[num] = new Tile();
            tiles[num++].setTypeOfLand(TypeOfLand.AllTypeOfLand.get(key));
            //System.out.println(num);
        }
        this.selectPanel = new SelectPanel(Orientation.HORIZONTAL, 0, 600, tiles.length * 150, 100, tiles.length * 150);
        //Tile tile = new Tile();
        //tile.setTypeOfLand(TypeOfLand.FlatLand);
        for(int i = 0; i < tiles.length; i++) {
            this.selectPanel.add(new TileComponent(gameContainer, tiles[i], 600, 0), 100);
            this.selectPanel.add(new EmptyPanel(0,0,0,0,50), 50);
        }
        //this.selectPanel.add(new TileComponent(gameContainer, tile, 600, 0), 100);
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {

    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        this.camera.render(gameContainer, graphics);
        this.selectPanel.render(gameContainer, graphics);
        //graphics.setColor(Color.magenta);
        // graphics.fillRect(0, 0, 600, 600);
        //this.map.render(gameContainer, graphics);
        //Image image = new Image("assets/graphics/tiles/flat.png");
        //image.draw(0, 0, 50, 50);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new MapComponentTest("MapComponentTest"));
        //app.setVSync(true);
        app.setDisplayMode(1280, 720, false);
        app.start();
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(abstractComponent instanceof MapComponent) {
            if(abstractComponent == this.map) {
                if(this.map.getSelectedTile() != null && selectPanel.getSelectedComponent() != null) {
                    Tile tile = this.map.getSelectedTile().getTile();
                    tile.setTypeOfLand(((TileComponent)selectPanel.getSelectedComponent()).getTile().getTypeOfLand());
                }
            }
        }
    }
}
