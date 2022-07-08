package graphics.panels;

import Processing.Buildings.Building;
import Processing.Units.Unit;
import Processing.Units.UnitPattern;
import Processing.Utilits.Wrapers.CreatableObject;
import graphics.components.button.ButtonComponent;
import graphics.components.button.SelectButtonComponent;
import graphics.components.tiledmap.CityComponent;
import graphics.components.tiledmap.GameMapComponent;
import graphics.components.tiledmap.UnitComponent;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class CityControlPanel extends Panel implements ComponentListener {

    private ButtonComponent exitButton;
    private ButtonComponent destroyCityButton;
    private ButtonComponent putCitizenButton;
    private ButtonComponent removeCitizenButton;
    private ButtonComponent buyTileButton;

    private boolean exit;

    private GameContainer gameContainer;
    private GameMapComponent gameMapComponent;
    private CityComponent cityComponent;

    private Image addObjectToMakeImage;
    private Image cancelMakingObjectImage;

    private ArrayList<ButtonComponent> addObjectToMake;
    private ArrayList<ButtonComponent> destroyMadeObjects;
    private ArrayList<ButtonComponent> cancelMakingObject;

    private ScrollButtonPanel objectsToMake;
    private ScrollButtonPanel makingObjects;
    private ScrollButtonPanel madeObjects;

    public CityControlPanel(GameContainer gameContainer, GameMapComponent gameMapComponent, CityComponent cityComponent) {
        super(1260, 0, 660, 1080, 1080);
        this.gameContainer = gameContainer;
        this.gameMapComponent = gameMapComponent;
        this.cityComponent = cityComponent;
        try {
            this.exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/exit.png"), 1890, 0, 30, 30);
            this.addObjectToMakeImage = new Image("assets/graphics/buttons/unit_control/colonise.png");
            //this.getCargoButton = new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1560, 920, 50, 50);

        } catch (SlickException e) {
            e.printStackTrace();
        }
        init();
    }

    public void init() {
        exit = false;
        objectsToMake = new ScrollButtonPanel(gameContainer, Orientation.VERTICAL, this.addObjectToMakeImage, this.addObjectToMakeImage, 0, 0, 660, 360, 360);
        makingObjects = new ScrollButtonPanel(gameContainer, Orientation.VERTICAL, this.addObjectToMakeImage, this.addObjectToMakeImage, 0, 360, 660, 360, 360);
        madeObjects  = new ScrollButtonPanel(gameContainer, Orientation.VERTICAL, this.addObjectToMakeImage, this.addObjectToMakeImage, 0, 720, 660, 360, 360);
        update();
    }

    public LinkedList<Object> getObjectsCanBeBuild() {
        LinkedList<Object> objectsCanBeBuild = new LinkedList<>();
        Iterator<Building> iterator = this.cityComponent.getCity().buildingsThatCanBeBuild.values().iterator();
        int index = 0;
        while(iterator.hasNext()){
            if(this.cityComponent.getCity().buildingCanBeBuilt.get(index)){
                objectsCanBeBuild.add(iterator.next());
            }
            index++;
        }
        for(UnitPattern unit : this.cityComponent.getCity().unitsThatCanBeBuild.values()) {
            objectsCanBeBuild.add(unit);
        }
        return objectsCanBeBuild;
    }

    public void update() {
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
            index++;
        }

        Iterator<UnitPattern> unitPatternIterator = this.cityComponent.getCity().unitsThatCanBeBuild.values().iterator();
        while(unitPatternIterator.hasNext()){
            Panel panel = new Panel(0, 0, 660, 60, 660);
            panel.add(new BuildingUnitPanel(unitPatternIterator.next(), 0, 0, 600, 60, 600), 600);
            ButtonComponent buttonComponent = new ButtonComponent(this.gameContainer, this.addObjectToMakeImage, 600, 0, 60, 60);
            panel.add(buttonComponent, 60);
            this.addObjectToMake.add(buttonComponent);
            objectsToMakePanel.add(panel, 60);
            buttonComponent.setLocked(false);
            index++;
        }
        objectsToMake.add(objectsToMakePanel, 0);

        Panel makingObjects = new Panel(Orientation.VERTICAL,0, 0, 660, (this.cityComponent.getCity().buildingsThatCanBeBuild.size() + this.cityComponent.getCity().unitsThatCanBeBuild.size()) * 60, (this.cityComponent.getCity().creationList.size() + this.cityComponent.getCity().creationList.size()) * 60);
        for(CreatableObject creatableObject : this.cityComponent.getCity().creationList) {
            Panel panel = new Panel(0, 0, 660, 60, 660);
            panel.add(new BuildingUnitPanel(creatableObject, 0, 0, 600, 60, 600), 600);
            ButtonComponent buttonComponent = new ButtonComponent(this.gameContainer, this.addObjectToMakeImage, 600, 0, 60, 60);
            panel.add(buttonComponent, 60);
            this.cancelMakingObject.add(buttonComponent);
            makingObjects.add(panel, 60);
            buttonComponent.setLocked(false);
        }
        this.makingObjects.add(makingObjects, 0);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        this.objectsToMake.render(container, g);
        this.makingObjects.render(container, g);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {

    }

    public class BuildingUnitPanel extends Panel {

        private Object object;

        public BuildingUnitPanel(Object object, float x, float y, float width, float height, int parts) {
            super(x, y, width, height, parts);
            this.object = object;
        }

        @Override
        public void render(GameContainer container, Graphics g) throws SlickException {
            if(object instanceof CreatableObject) {
                /*if(((CreatableObject) object).getObject() instanceof UnitPattern) {
                    g.drawString(((UnitPattern)object).NameOfUnit, this.x, this.y);
                    g.drawString("Production cost: " + ((UnitPattern)object).productionCost, this.x, this.y + 20);
                }
                else if(((CreatableObject) object).getObject() instanceof Building) {
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
                    g.drawString("Production cost: " + ((Building)object).productionCost, this.x, this.y+40 );
                }*/
            }
            else if(object instanceof UnitPattern) {
                g.drawString(((UnitPattern)object).NameOfUnit, this.x, this.y);
                g.drawString("Production cost: " + ((UnitPattern)object).productionCost, this.x, this.y + 20);
            }
            else if(object instanceof Building) {
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
                g.drawString("Production cost: " + ((Building)object).productionCost, this.x, this.y+40 );
            }
        }
    }

}
