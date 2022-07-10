package graphics.panels;

import Processing.Buildings.Building;
import Processing.TileMap.Tile;
import Processing.Units.Unit;
import Processing.Units.UnitPattern;
import Processing.Utilits.Wrapers.CreatableObject;
import graphics.components.button.ButtonComponent;
import graphics.components.button.SelectButtonComponent;
import graphics.components.tiledmap.*;
import graphics.loads.Images;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class CityControlPanel extends Panel implements ComponentListener {

    private ButtonComponent exitButton;
    private ButtonComponent destroyCityButton;
    private ButtonComponent putCitizenButton;
    private ButtonComponent removeCitizenButton;
    private ButtonComponent buyTileButton;

    private double tilePrice = -1;

    private boolean exit = true;

    private GameContainer gameContainer;
    private GameMapComponent gameMapComponent;
    private CityComponent cityComponent;

    private Image addObjectToMakeImage;
    private Image deleteObjectImage;

    private ArrayList<ButtonComponent> addObjectToMake;
    private ArrayList<ButtonComponent> destroyMadeObjects;
    private ArrayList<ButtonComponent> cancelMakingObject;

    private ScrollButtonPanel objectsToMake;
    private ScrollButtonPanel makingObjects;
    private ScrollButtonPanel madeObjects;

    public CityControlPanel(GameContainer gameContainer, GameMapComponent gameMapComponent) {
        super(1260, 0, 660, 1080, 1080);
        this.gameContainer = gameContainer;
        this.gameMapComponent = gameMapComponent;
        this.gameMapComponent.addListener(this);
        //this.cityComponent = cityComponent;
        Image scrollingUpImage = null;
        Image scrollingDownImage = null;
        try {
            this.exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/exit.png"), 1890, 0, 30, 30);
            this.putCitizenButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/add_citizen.png"), 1270, 70, 152, 40);
            this.removeCitizenButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/remove_citizen.png"), 1432, 70, 152, 40);
            this.buyTileButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/buy_tile.png"), 1594, 70, 152, 40);
            this.destroyCityButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/delete_city_control.png"), 1756, 70, 154, 40);

            this.exitButton.addListener(this);
            this.putCitizenButton.addListener(this);
            this.buyTileButton.addListener(this);
            this.removeCitizenButton.addListener(this);
            this.destroyCityButton.addListener(this);
            this.addObjectToMakeImage = new Image("assets/graphics/buttons/add.png");
            this.deleteObjectImage = new Image("assets/graphics/buttons/delete.png");
            scrollingUpImage = new Image("assets/graphics/buttons/scroll_buttons/up_scroll_button_660_30.png");
            scrollingDownImage = new Image("assets/graphics/buttons/scroll_buttons/down_scroll_button_660_30.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        objectsToMake = new ScrollButtonPanel(gameContainer, Orientation.VERTICAL, scrollingUpImage, scrollingDownImage, 1260, 140, 660, 300, 300);
        makingObjects = new ScrollButtonPanel(gameContainer, Orientation.VERTICAL, scrollingUpImage, scrollingDownImage, 1260, 780, 660, 300, 300);
        madeObjects  = new ScrollButtonPanel(gameContainer, Orientation.VERTICAL, scrollingUpImage, scrollingDownImage, 1260, 460, 660, 300, 300);
        /*try {
            this.exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1890, 0, 30, 30);
            this.addObjectToMakeImage = new Image("assets/graphics/buttons/unit_control/colonise.png");
            this.destroyCityButton = new ButtonComponent(gameContainer, new Image("assets/graphics/units/SettlerIdleRight.png"), 0, 0 , 0, 0);
        } catch (SlickException e) {
            e.printStackTrace();
        }*/
        //init();
    }

    public void init() {
        exit = false;
        this.cityComponent.showArea();
        update();
    }

    public void update() {
        this.putCitizenButton.setLocked(true);
        this.removeCitizenButton.setLocked(true);
        this.buyTileButton.setLocked(true);
        this.destroyCityButton.setLocked(false);
        if(this.cityComponent.isInOwnedArea(((GameTileComponent) this.gameMapComponent.getSelectedTile()))) {
            if(this.gameMapComponent.getSelectedTile().getTile().isProcessedByPeople) {
                this.removeCitizenButton.setLocked(false);
            }
            else {
                this.putCitizenButton.setLocked(false);
            }
        }
        else {
            tilePrice = this.cityComponent.getCity().calculatePriceOfTile(this.gameMapComponent.getSelectedTile().getTile());
            if(tilePrice > 0) {
                this.buyTileButton.setLocked(false);
            }
        }
        updateCreationList();
        updateBuiltBuildings();
        updateObjectsCanBuild();
    }

    public void updateCreationList() {
        this.cancelMakingObject = new ArrayList<>();
        Panel makingObjects = new Panel(Orientation.VERTICAL,0, 0, 660, (this.cityComponent.getCity().creationList.size() + this.cityComponent.getCity().createdUnits.size()) * 60, (this.cityComponent.getCity().creationList.size() + this.cityComponent.getCity().createdUnits.size()) * 60);
        for(CreatableObject creatableObject : this.cityComponent.getCity().creationList) {
            Panel panel = new Panel(0, 0, 660, 60, 660);
            panel.add(new BuildingUnitPanel(creatableObject, 0, 0, 600, 60, 600), 600);
            ButtonComponent buttonComponent = new ButtonComponent(this.gameContainer, this.deleteObjectImage, 600, 0, 60, 60);
            panel.add(buttonComponent, 60);
            this.cancelMakingObject.add(buttonComponent);
            panel.setParent(this.makingObjects);
            makingObjects.add(panel, 60);
            buttonComponent.setLocked(false);
            buttonComponent.addListener(this);
        }
        int i = 0;
        for(UnitPattern unitPattern : this.cityComponent.getCity().createdUnits) {
            Panel panel = new Panel(0, 0, 660, 60, 660);
            BuildingUnitPanel buildingUnitPanel = new BuildingUnitPanel(unitPattern, 0, 0, 600, 60, 600);
            buildingUnitPanel.setCreated(true);
            panel.add(buildingUnitPanel, 600);
            if(i == 0) {
                ButtonComponent buttonComponent = new ButtonComponent(this.gameContainer, this.deleteObjectImage, 600, 0, 60, 60);
                panel.add(buttonComponent, 60);
                this.cancelMakingObject.add(buttonComponent);
                buttonComponent.setLocked(false);
                buttonComponent.addListener(this);
            }
            panel.setParent(this.makingObjects);
            makingObjects.add(panel, 60);
            i++;
        }
        this.makingObjects.add(makingObjects, 0);
    }

    public void updateBuiltBuildings() {
        destroyMadeObjects = new ArrayList<>();
        Panel madeObjectsPanel = new Panel(Orientation.VERTICAL, 0, 0, 660, (this.cityComponent.getCity().alreadyBuiltBuildings.size()) * 60, (this.cityComponent.getCity().alreadyBuiltBuildings.size()) * 60);
        for(Building building : this.cityComponent.getCity().alreadyBuiltBuildings) {
            Panel panel = new Panel(0, 0, 660, 60, 660);
            BuildingUnitPanel buildingUnitPanel = new BuildingUnitPanel(building, 0, 0, 600, 60, 600);
            buildingUnitPanel.setCreated(true);
            panel.add(buildingUnitPanel, 600);
            ButtonComponent buttonComponent = new ButtonComponent(this.gameContainer, this.deleteObjectImage, 600, 0, 60, 60);
            panel.add(buttonComponent, 60);
            this.destroyMadeObjects.add(buttonComponent);
            panel.setParent(this);
            madeObjectsPanel.add(panel, 60);
            buttonComponent.setLocked(false);
            buttonComponent.addListener(this);
        }
        this.madeObjects.add(madeObjectsPanel, 0);
    }

    public void updateObjectsCanBuild() {
        addObjectToMake = new ArrayList<>();
        Panel objectsToMakePanel = new Panel(Orientation.VERTICAL,0, 0, 660, (this.cityComponent.getCity().buildingsThatCanBeBuild.size() + this.cityComponent.getCity().unitsThatCanBeBuild.size()) * 60, (this.cityComponent.getCity().buildingsThatCanBeBuild.size() + this.cityComponent.getCity().unitsThatCanBeBuild.size()) * 60);
        Iterator<Building> iterator = this.cityComponent.getCity().buildingsThatCanBeBuild.values().iterator();
        int index = 0;
        while(iterator.hasNext()){
            Panel panel = new Panel(0, 0, 660, 60, 660);
            panel.add(new BuildingUnitPanel(iterator.next(), 0, 0, 600, 60, 600), 600);
            ButtonComponent buttonComponent = new ButtonComponent(this.gameContainer, this.addObjectToMakeImage, 600, 0, 60, 60);
            panel.add(buttonComponent, 60);
            this.addObjectToMake.add(buttonComponent);
            objectsToMakePanel.add(panel, 60);
            if(this.cityComponent.getCity().buildingCanBeBuilt.get(index)){
                buttonComponent.setLocked(false);
            }
            else {
                buttonComponent.setLocked(true);
            }
            buttonComponent.addListener(this);
            index++;
        }

        Iterator<UnitPattern> unitPatternIterator = this.cityComponent.getCity().unitsThatCanBeBuild.values().iterator();
        while(unitPatternIterator.hasNext()){
            Panel panel = new Panel(Orientation.HORIZONTAL, 0, 0, 660, 60, 660);
            panel.add(new BuildingUnitPanel(unitPatternIterator.next(), 0, 0, 600, 60, 600), 600);
            ButtonComponent buttonComponent = new ButtonComponent(this.gameContainer, this.addObjectToMakeImage, 600, 0, 60, 60);
            panel.add(buttonComponent, 60);
            this.addObjectToMake.add(buttonComponent);
            objectsToMakePanel.add(panel, 60);
            buttonComponent.setLocked(false);
            buttonComponent.addListener(this);
            index++;
        }
        objectsToMake.add(objectsToMakePanel, 0);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        Images.bookPaper.getSubImage(0, 0, (int) this.width, (int) this.height).draw(this.x, this.y, this.width, this.height);
        //g.setColor(Color.black);
        //g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(Color.white);
        g.drawString("City", 1260, 0);
        g.setColor(new Color(0.588f, 0.294f, 0, 1));
        g.drawString(String.valueOf(this.cityComponent.getCity().wealth.production), this.x, this.y+20);
        g.setColor(Color.red);
        g.drawString(String.valueOf(this.cityComponent.getCity().wealth.food), this.x+100, this.y+20);
        g.setColor(Color.orange);
        g.drawString(String.valueOf(this.cityComponent.getCity().wealth.money), this.x+200, this.y+20);
        g.setColor(Color.magenta);
        g.drawString(String.valueOf(this.cityComponent.getCity().wealth.engineeringScience), this.x+300, this.y+20);
        g.setColor(Color.cyan);
        g.drawString(String.valueOf(this.cityComponent.getCity().wealth.arcanumScience), this.x+400, this.y+20);
        g.setColor(Color.white);
        g.drawString(String.valueOf(this.cityComponent.getCity().wealth.societyScience), this.x+500, this.y+20);
        g.drawString("Citizens (free/all): " + this.cityComponent.getCity().numberOfFreeCitizen + "/" + this.cityComponent.getCity().numberOfCitizen, this.x, this.y+40);
        g.setColor(Color.white);
        g.drawString("Buildings and units you can create:", 1260, 120);
        this.objectsToMake.render(container, g);
        g.setColor(Color.white);
        g.drawString("Created buildings:", 1260, 440);
        this.makingObjects.render(container, g);
        g.setColor(Color.white);
        g.drawString("Creation list:", 1260, 760);
        this.madeObjects.render(container, g);
        exitButton.render(gameContainer, g);
        destroyCityButton.render(gameContainer, g);
        buyTileButton.render(gameContainer, g);
        if(tilePrice > 0) {
            g.setColor(Color.black);
            g.drawString(String.valueOf(tilePrice), this.buyTileButton.getX(), this.buyTileButton.getY());
        }
        this.putCitizenButton.render(gameContainer, g);
        this.removeCitizenButton.render(gameContainer, g);
    }

    public void update(GameContainer gameContainer, int delta) throws SlickException {
        this.makingObjects.update(gameContainer, delta);
        this.objectsToMake.update(gameContainer, delta);
        this.madeObjects.update(gameContainer, delta);
    }

    @Override
    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        super.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.madeObjects.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.objectsToMake.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.makingObjects.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.exitButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.putCitizenButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.buyTileButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.removeCitizenButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.destroyCityButton.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressedSignalise(int button, int x, int y) {
        super.mousePressedSignalise(button, x, y);
        this.madeObjects.mousePressedSignalise(button, x, y);
        this.objectsToMake.mousePressedSignalise(button, x, y);
        this.makingObjects.mousePressedSignalise(button, x, y);
        this.exitButton.mousePressedSignalise(button, x, y);
        this.putCitizenButton.mousePressedSignalise(button, x, y);
        this.buyTileButton.mousePressedSignalise(button, x, y);
        this.removeCitizenButton.mousePressedSignalise(button, x, y);
        this.destroyCityButton.mousePressedSignalise(button, x, y);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(this.exit) return;
        if(abstractComponent instanceof ButtonComponent) {
            for(int i = 0; i < this.addObjectToMake.size(); i++) {
                if(this.addObjectToMake.get(i) == abstractComponent) {
                    //System.out.println(2);
                    Iterator<Building> iterator = this.cityComponent.getCity().buildingsThatCanBeBuild.values().iterator();
                    int index = 0;
                    while(iterator.hasNext()) {
                        if(i == index) {
                            this.cityComponent.getCity().addToCreationList(iterator.next());
                        }
                        else iterator.next();
                        index++;
                    }
                    Iterator<UnitPattern> unitPatternIterator = this.cityComponent.getCity().unitsThatCanBeBuild.values().iterator();
                    while(unitPatternIterator.hasNext()) {
                        if(i == index) {
                            //System.out.println(3);
                            this.cityComponent.getCity().addToCreationList(unitPatternIterator.next());
                            //System.out.println(5);
                         }
                        else unitPatternIterator.next();
                        index++;
                    }
                }
            }
            for(int i = 0; i < this.destroyMadeObjects.size(); i++) {
                if(this.destroyMadeObjects.get(i) == abstractComponent) {
                    this.cityComponent.getCity().destroyBuilding(this.cityComponent.getCity().alreadyBuiltBuildings.get(i));
                }
            }
            if(this.cancelMakingObject.size() > this.cityComponent.getCity().creationList.size() && this.cancelMakingObject.get(this.cityComponent.getCity().creationList.size()) == abstractComponent) {
                this.cityComponent.getCity().removeFirstUnitReadyList();
            }
            else {
                for (int i = 0; i < this.cancelMakingObject.size(); i++) {
                    if (this.cancelMakingObject.get(i) == abstractComponent) {
                        this.cityComponent.getCity().removeFromCreationList(i);
                        break;
                    }
                }
            }
            if(abstractComponent == exitButton) {
                setExit(true);
                return;
            }
            if(abstractComponent == destroyCityButton) {
                this.cityComponent.getCity().destroyCiti();
                setExit(true);
            }
            if(abstractComponent == putCitizenButton) {
                this.cityComponent.getCity().addCitizenToTile(this.gameMapComponent.getSelectedTile().getTile());
            }
            if(abstractComponent == removeCitizenButton) {
                this.cityComponent.getCity().removeCitizenFromTile(this.gameMapComponent.getSelectedTile().getTile());
            }
            if(abstractComponent == buyTileButton) {
                this.cityComponent.buyTile((GameTileComponent) this.gameMapComponent.getSelectedTile());
            }

        }
        update();
    }

    public CityComponent getCityComponent() {
        return cityComponent;
    }

    public void setCityComponent(CityComponent cityComponent) {
        this.cityComponent = cityComponent;
        init();
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
        if (exit == true) {
            this.cityComponent.setState(CityState.IDLE);
        }
    }

    public class BuildingUnitPanel extends Panel {

        private Object object;
        private boolean created = false;

        public BuildingUnitPanel(Object object, float x, float y, float width, float height, int parts) {
            super(x, y, width, height, parts);
            this.object = object;
        }

        @Override
        public void render(GameContainer container, Graphics g) throws SlickException {
            if(object instanceof CreatableObject) {
                Object o = ((CreatableObject) object).object;
                if(o instanceof UnitPattern) {
                    g.setColor(Color.white);
                    g.drawString(((UnitPattern)o).NameOfUnit, this.x, this.y);
                    g.setColor(Color.gray);
                    g.fillRoundRect(this.x, this.y + 20, this.width, 20, 5);
                    g.setColor(Color.green);
                    g.fillRoundRect(this.x, this.y + 20, Math.min((float) (this.width / ((CreatableObject) object).goal * ((CreatableObject) object).progress), this.width), 20, 5);
                    g.setColor(Color.white);
                    g.drawString( ((CreatableObject) object).progress+ "/" + ((CreatableObject) object).goal, this.x + this.width / 2 - 100, this.y + 21);
                }
                else if(o instanceof Building) {
                    g.setColor(Color.white);
                    g.drawString(((Building)o).name, this.x, this.y);
                    g.setColor(new Color(0.588f, 0.294f, 0, 1));
                    g.drawString(String.valueOf(((Building)o).passiveWealth.production), this.x, this.y+20);
                    g.setColor(Color.red);
                    g.drawString(String.valueOf(((Building)o).passiveWealth.food), this.x+100, this.y+20);
                    g.setColor(Color.orange);
                    g.drawString(String.valueOf(((Building)o).passiveWealth.money), this.x+200, this.y+20);
                    g.setColor(Color.magenta);
                    g.drawString(String.valueOf(((Building)o).passiveWealth.arcanumScience), this.x+300, this.y+20);
                    g.setColor(Color.cyan);
                    g.drawString(String.valueOf(((Building)o).passiveWealth.engineeringScience), this.x+400, this.y+20);
                    g.setColor(Color.white);
                    g.drawString(String.valueOf(((Building)o).passiveWealth.societyScience), this.x+500, this.y+20);
                    g.setColor(Color.gray);
                    g.fillRoundRect(this.x, this.y + 40, this.width, 20, 5);
                    g.setColor(Color.green);
                    g.fillRoundRect(this.x, this.y + 40, Math.min((float) (this.width / ((CreatableObject) object).goal * ((CreatableObject) object).progress), this.width), 20, 5);
                    g.setColor(Color.white);
                    g.drawString( ((CreatableObject) object).progress+ "/" + ((CreatableObject) object).goal, this.x + this.width / 2 - 100, this.y + 41);
                }
            }
            else if(object instanceof UnitPattern) {
                g.setColor(Color.white);
                g.drawString(((UnitPattern)object).NameOfUnit, this.x, this.y);
                if(!this.created) {
                    g.drawString("Production cost: " + ((UnitPattern) object).productionCost, this.x, this.y + 20);
                }
                else {
                    g.drawString("Created!", this.x, this.y + 20);
                }
            }
            else if(object instanceof Building) {
                g.setColor(Color.white);
                g.drawString(((Building)object).name, this.x, this.y);
                g.setColor(new Color(0.588f, 0.294f, 0, 1));
                g.drawString(String.valueOf(((Building)object).passiveWealth.production), this.x, this.y+20);
                g.setColor(Color.red);
                g.drawString(String.valueOf(((Building)object).passiveWealth.food), this.x+100, this.y+20);
                g.setColor(Color.orange);
                g.drawString(String.valueOf(((Building)object).passiveWealth.money), this.x+200, this.y+20);
                g.setColor(Color.magenta);
                g.drawString(String.valueOf(((Building)object).passiveWealth.arcanumScience), this.x+300, this.y+20);
                g.setColor(Color.cyan);
                g.drawString(String.valueOf(((Building)object).passiveWealth.engineeringScience), this.x+400, this.y+20);
                g.setColor(Color.white);
                g.drawString(String.valueOf(((Building)object).passiveWealth.societyScience), this.x+500, this.y+20);
                if(!this.created) {
                    g.drawString("Production cost: " + ((Building) object).productionCost, this.x, this.y + 40);
                }
            }
        }

        public boolean isCreated() {
            return created;
        }

        public void setCreated(boolean created) {
            this.created = created;
        }
    }

}
