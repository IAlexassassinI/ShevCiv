package graphics.states;

import Processing.TileMap.GameMap;
import Processing.TileMap.TileUtils.Resource;
import Processing.TileMap.TileUtils.TypeOfFlora;
import Processing.TileMap.TileUtils.TypeOfLand;
import graphics.components.button.ButtonComponent;
import graphics.components.button.SelectButtonComponent;
import graphics.components.camera.Camera;
import graphics.components.tiledmap.EditMapComponent;
import graphics.components.tiledmap.EditMode;
import graphics.loads.Images;
import graphics.panels.*;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.*;

public class EditMap extends BasicGameState implements ComponentListener {

    public static final int ID = 2;
    public static final String saveFile = "assets/saved_maps/map.txt";

    private GameMap gameMap;

    private StateBasedGame game;

    private Camera camera;
    private EditMapComponent editMapComponent;
    private SelectionPanel category;
    private SelectionPanel typesOfLand;
    private SelectionPanel resources;
    private SelectionPanel typesOfFlora;
    private ScrollButtonPanel scrollTypesOfLand;
    private ScrollButtonPanel scrollTypesOfFlora;
    private ScrollButtonPanel scrollResources;

    private ButtonComponent saveButton;
    private ButtonComponent loadButton;
    private ButtonComponent exitButton;

    @Override
    public int getID() {
        return ID;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        this.game = stateBasedGame;
        this.gameMap = new GameMap(20, 20);
        this.editMapComponent = new EditMapComponent(gameContainer, this.gameMap, 20, 20);
        this.editMapComponent.addListener(this);
        editMapComponent.setEditMode(EditMode.EDIT_TYPE_OF_LAND);
        this.camera = new Camera(gameContainer, 20, 20, 1250, 850, this.editMapComponent);

        this.scrollTypesOfLand = new ScrollButtonPanel(gameContainer, new Image("assets/graphics/buttons/scroll_buttons/left_scroll_button_30_120.png"), new Image("assets/graphics/buttons/scroll_buttons/right_scroll_button_30_120.png"), 20,940,1250,120,1880);
        this.scrollTypesOfFlora = new ScrollButtonPanel(gameContainer, new Image("assets/graphics/buttons/scroll_buttons/left_scroll_button_30_120.png"), new Image("assets/graphics/buttons/scroll_buttons/right_scroll_button_30_120.png"), 20,940,1250,120,1880);
        this.scrollResources = new ScrollButtonPanel(gameContainer, new Image("assets/graphics/buttons/scroll_buttons/left_scroll_button_30_120.png"), new Image("assets/graphics/buttons/scroll_buttons/right_scroll_button_30_120.png"), 20,940,1250,120,1880);


        this.category = new SelectionPanel(Orientation.HORIZONTAL, 20, 880, 190, 50, 190);
        for(int i = 0; i < Images.categorySpriteSheet.getHorizontalCount(); i++) {
            category.add(new EmptyPanel(0,0,10, 50, 0), 10);
            SelectButtonComponent selectButtonComponent = new SelectButtonComponent(gameContainer, Images.categorySpriteSheet.getSprite(i, 0), 0,0, 10, 100);
            category.add(selectButtonComponent, 50);
        }
        category.add(new EmptyPanel(0,0,10, 50, 0), 10);

        this.typesOfLand = new SelectionPanel(Orientation.HORIZONTAL, 0, 0, Images.typesOfLandSpriteSheet.getHorizontalCount() * 110 + 10, 100, Images.typesOfLandSpriteSheet.getHorizontalCount() * 110 + 10);
        for(int i = 0; i < Images.typesOfLandSpriteSheet.getHorizontalCount(); i++) {
            typesOfLand.add(new EmptyPanel(0,0,10, 100, 0), 10);
            SelectButtonComponent selectButtonComponent = new SelectButtonComponent(gameContainer, Images.typesOfLandSpriteSheet.getSprite(i, 0), 0,0, 10, 100);
            typesOfLand.add(selectButtonComponent, 100);
        }
        typesOfLand.add(new EmptyPanel(0,0,10, 100, 0), 10);

        this.typesOfFlora = new SelectionPanel(Orientation.HORIZONTAL, 0, 0, Images.typesOfFloraSpriteSheet.getHorizontalCount() * 110 + 10, 100, Images.typesOfFloraSpriteSheet.getHorizontalCount() * 110 + 10);
        for(int i = 0; i < Images.typesOfFloraSpriteSheet.getHorizontalCount(); i++) {
            typesOfFlora.add(new EmptyPanel(0,0,10, 100, 0), 10);
            SelectButtonComponent selectButtonComponent = new SelectButtonComponent(gameContainer, Images.typesOfFloraSpriteSheet.getSprite(i, 0), 0,0, 10, 100);
            typesOfFlora.add(selectButtonComponent, 100);
        }
        typesOfFlora.add(new EmptyPanel(0,0,10, 100, 0), 10);

        this.resources = new SelectionPanel(Orientation.HORIZONTAL, 0, 0, Images.resourcesSpriteSheet.getHorizontalCount() * 110 + 10, 100, Images.resourcesSpriteSheet.getHorizontalCount() * 110 + 10);
        for(int i = 0; i < Images.resourcesSpriteSheet.getHorizontalCount(); i++) {
            resources.add(new EmptyPanel(0,0,10, 100, 0), 10);
            SelectButtonComponent selectButtonComponent = new SelectButtonComponent(gameContainer, Images.resourcesSpriteSheet.getSprite(i, 0), 0,0, 10, 100);
            resources.add(selectButtonComponent, 100);
        }
        resources.add(new EmptyPanel(0,0,10, 100, 0), 10);

        Panel panelTypesOfLand = new Panel(Orientation.VERTICAL,0,0, this.typesOfLand.getWidth(), 120, 120);
        panelTypesOfLand.add(new EmptyPanel(0, 0, this.typesOfLand.getWidth(), 10, 0), 10);
        panelTypesOfLand.add(this.typesOfLand, 100);
        panelTypesOfLand.add(new EmptyPanel(0, 0, this.typesOfLand.getWidth(), 10, 0), 10);
        this.scrollTypesOfLand.add(panelTypesOfLand, 0);

        Panel panelTypesOfFlora = new Panel(Orientation.VERTICAL,0,0, this.typesOfFlora.getWidth(), 120, 120);
        panelTypesOfFlora.add(new EmptyPanel(0, 0, this.typesOfFlora.getWidth(), 10, 0), 10);
        panelTypesOfFlora.add(this.typesOfFlora, 100);
        panelTypesOfFlora.add(new EmptyPanel(0, 0, this.typesOfFlora.getWidth(), 10, 0), 10);
        this.scrollTypesOfFlora.add(panelTypesOfFlora, 0);

        Panel panelResources = new Panel(Orientation.VERTICAL,0,0, this.resources.getWidth(), 120, 120);
        panelResources.add(new EmptyPanel(0, 0, this.resources.getWidth(), 10, 0), 10);
        panelResources.add(this.resources, 100);
        panelResources.add(new EmptyPanel(0, 0, this.resources.getWidth(), 10, 0), 10);
        this.scrollResources.add(panelResources, 0);

        //System.out.println(this.scrollTypesOfFlora.getX() + " " + this.scrollTypesOfFlora.getY());
        //System.out.println(this.scrollResources.getX() + " " + this.scrollResources.getY());
        this.saveButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/save_edit_map.png"), 1290, 20, 610, 75);
        this.saveButton.addListener(this);
        this.loadButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/load_edit_map.png"), 1290, 115, 610, 75);
        this.loadButton.addListener(this);
        this.exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/exit_edit_map.png"), 1290, 210, 610, 75);
        this.exitButton.addListener(this);
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        this.camera.render(gameContainer, graphics);
        this.category.render(gameContainer, graphics);
        switch (category.getSelectedComponent().getId()) {
            case 0:
                this.scrollTypesOfLand.render(gameContainer, graphics);
                break;
            case 1:
                this.scrollTypesOfFlora.render(gameContainer, graphics);
                break;
            case 2:
                this.scrollResources.render(gameContainer, graphics);
                break;
            default:
                break;
        }
        saveButton.render(gameContainer, graphics);
        loadButton.render(gameContainer, graphics);
        exitButton.render(gameContainer, graphics);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        switch (category.getSelectedComponent().getId()) {
            case 0:
                this.editMapComponent.setEditMode(EditMode.EDIT_TYPE_OF_LAND);
                break;
            case 1:
                this.editMapComponent.setEditMode(EditMode.EDIT_TYPE_OF_FLORA);
                break;
            case 2:
                this.editMapComponent.setEditMode(EditMode.EDIT_RESOURCE);
                break;
            default:
                break;
        }
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(abstractComponent instanceof EditMapComponent) {
            switch (category.getSelectedComponent().getId()) {
                case 0:
                    TypeOfLand typeOfLand = TypeOfLand.Void;
                    for(String key : TypeOfLand.AllTypeOfLand.keySet()) {
                        if(TypeOfLand.AllTypeOfLand.get(key).Type == this.typesOfLand.getSelectedComponent().getId()) {
                            typeOfLand = TypeOfLand.AllTypeOfLand.get(key);
                        }
                    }
                    ((EditMapComponent) abstractComponent).getSelectedTile().getTile().setTypeOfLand(typeOfLand);
                    break;
                case 1:
                    TypeOfFlora typeOfFlora = TypeOfFlora.none;
                    for(String key : TypeOfFlora.AllTypeOfFlora.keySet()) {
                        if(TypeOfFlora.AllTypeOfFlora.get(key).Type == this.typesOfFlora.getSelectedComponent().getId()) {
                            typeOfFlora = TypeOfFlora.AllTypeOfFlora.get(key);
                        }
                    }
                    ((EditMapComponent) abstractComponent).getSelectedTile().getTile().setTypeOfFlora(typeOfFlora);
                    break;
                case 2:
                    Resource resource = Resource.none;
                    for(String key : Resource.AllResource.keySet()) {
                        if(Resource.AllResource.get(key).Type == this.resources.getSelectedComponent().getId()) {
                            resource = Resource.AllResource.get(key);
                        }
                    }
                    ((EditMapComponent) abstractComponent).getSelectedTile().getTile().setResource(resource);
                    break;
                default:
                    break;
            }
        }
        else if(abstractComponent instanceof ButtonComponent) {
            if(abstractComponent == this.saveButton) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(saveFile));
                    oos.writeObject(this.gameMap);
                    oos.close();
                }  catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(abstractComponent == this.loadButton) {

            }
            else if(abstractComponent == this.exitButton) {
                this.game.enterState(MainMenu.ID);
            }
        }
    }
}
