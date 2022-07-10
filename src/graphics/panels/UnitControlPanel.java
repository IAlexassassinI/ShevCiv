package graphics.panels;

import Processing.TileMap.TileUtils.RoadBridge;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Units.Ability.Colonize;
import Processing.Units.Ability.ConstructSomethingOnTile;
import Processing.Units.Ability.GetCargoSmall;
import Processing.Units.Ability.SpecialAbility;
import Processing.Utilits.Wrapers.TwoTTT;
import graphics.components.button.ButtonComponent;
import graphics.components.button.SelectButtonComponent;
import graphics.components.tiledmap.GameMapComponent;
import graphics.components.tiledmap.GameTileComponent;
import graphics.components.tiledmap.UnitComponent;
import graphics.components.tiledmap.UnitState;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;

public class UnitControlPanel extends Panel implements ComponentListener {

    private GameMapComponent gameMapComponent;
    private UnitComponent unitComponent;

    private Image attackMelee;
    private Image attackRanged;
    private Image defenceMelee;
    private Image defenceRanged;
    private Image getCargoImage;
    private Image putCargoImage;

    private SelectButtonComponent moveButton;
    private SelectButtonComponent attackButton;
    private ButtonComponent coloniseButton;
    private SelectButtonComponent getCargoButton;
    private ButtonComponent buildFarmlandButton;
    private ButtonComponent buildMineButton;
    private ButtonComponent buildSawmillButton;
    private ButtonComponent buildNoneButton;
    private ButtonComponent exitButton;

    private boolean exit = true;
    private boolean colonise = false;
    private boolean construct = false;
    private boolean getCargo = false;

    public UnitControlPanel(GameContainer gameContainer, GameMapComponent gameMapComponent) {
        super(1220, 760, 700, 220, 0);
        this.gameMapComponent = gameMapComponent;
        this.gameMapComponent.addListener(this);
        //this.unitComponent = unitComponent;
        try {
            this.moveButton = new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/move.png"), 1440, 820, 50, 50);
            this.attackButton = new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/attack.png"), 1500, 820, 50, 50);
            this.coloniseButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1560, 820, 50, 50);
            this.exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/exit.png"), 1890, 760, 30, 30);
            this.getCargoImage = new Image("assets/graphics/buttons/unit_control/get_cargo.png");
            this.putCargoImage = new Image("assets/graphics/buttons/unit_control/put_cargo.png");
            this.getCargoButton = new SelectButtonComponent(gameContainer, this.getCargoImage, 1560, 820, 50, 50);
            this.buildFarmlandButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/buildFarmland.png"), 1560, 820, 50, 50);
            this.buildMineButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/buildMine.png"), 1620, 820, 50, 50);
            this.buildSawmillButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/buildSawmill.png"), 1680, 820, 50, 50);
            this.buildNoneButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/delete.png"), 1740, 820, 50, 50);

            this.getCargoButton.addListener(this);
            this.buildFarmlandButton.addListener(this);
            this.buildMineButton.addListener(this);
            this.buildSawmillButton.addListener(this);
            this.buildNoneButton.addListener(this);
            this.moveButton.addListener(this);
            this.attackButton.addListener(this);
            this.coloniseButton.addListener(this);
            this.exitButton.addListener(this);

            this.attackMelee = new Image("assets/graphics/unit_control/attack_melee_icon.png");
            this.attackRanged = new Image("assets/graphics/unit_control/attack_ranged_icon.png");
            this.defenceMelee = new Image("assets/graphics/unit_control/defence_melee_icon.png");
            this.defenceRanged = new Image("assets/graphics/unit_control/defence_ranged_icon.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
        //init();
    }

    public void init() {
        setExit(false);
        colonise = false;
        construct = false;
        getCargo = false;
        if(unitComponent.getUnit().Abilities != null && unitComponent.getUnit().Abilities.size() != 0) {
            if(unitComponent.getUnit().Abilities.get(0) instanceof Colonize) {
                colonise = true;
            }
            else if(unitComponent.getUnit().Abilities.get(0) instanceof ConstructSomethingOnTile) {
                construct = true;
            }
            else if(unitComponent.getUnit().Abilities.get(0) instanceof GetCargoSmall) {
                getCargo = true;
            }
        }
        update();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.black);
        g.fillRect(1220, 760, 700, 220);

        this.unitComponent.getCurrentAnimation().draw(1230, 770, 200, 200);

        g.setColor(Color.white);
        g.drawString("ACP: " + this.unitComponent.getUnit().currentActionPoints + "/" + this.unitComponent.getUnit().typeOfUnit.maxActionPoints, 1230, 770);
        g.drawString("ATP: " + this.unitComponent.getUnit().currentNumberOfAttacks + "/" + this.unitComponent.getUnit().typeOfUnit.maxNumberOfAttacks, 1230, 790);
        this.attackMelee.draw(1230, 810, 20,20);
        g.drawString(String.valueOf(this.unitComponent.getUnit().typeOfUnit.attackMelee), 1250, 810);
        this.attackRanged.draw(1230, 830, 20,20);
        g.drawString(String.valueOf(this.unitComponent.getUnit().typeOfUnit.rangedAttack), 1250, 830);
        this.defenceMelee.draw(1230, 850, 20,20);
        g.drawString(String.valueOf(this.unitComponent.getUnit().typeOfUnit.defenceMelee), 1250, 850);
        this.defenceRanged.draw(1230, 870, 20,20);
        g.drawString(String.valueOf(this.unitComponent.getUnit().typeOfUnit.defenceRanged), 1250, 870);
        g.drawString(unitComponent.getUnit().typeOfUnit.NameOfUnit, 1440, 780);

        g.setColor(Color.gray);
        g.fillRoundRect(1440, 900,470, 40, 5);
        g.setColor(Color.green);
        g.fillRoundRect(1440, 900, Math.max((float) (470.0 / this.unitComponent.getUnit().typeOfUnit.maxHitPoints * this.unitComponent.getUnit().currentHitPoints), 0), 40, 5);
        g.setColor(Color.white);
        g.drawString( this.unitComponent.getUnit().currentHitPoints + "/" + this.unitComponent.getUnit().typeOfUnit.maxHitPoints, 1640, 911);

        this.moveButton.render(container, g);
        this.attackButton.render(container, g);
        //if(this.unitComponent.getUnit().Abilities.contains())
        if(colonise) {
            this.coloniseButton.render(container, g);
        }
        else if(getCargo) {
            this.getCargoButton.render(container, g);
            /*if(((GetCargoSmall) this.unitComponent.getUnit().Abilities.get(0)).isCargoFull) {

            }*/
        }
        else if(construct) {
            this.buildFarmlandButton.render(container, g);
            if(this.unitComponent.getTileComponent().getTile().getTypeOfBuilding() == TypeOfBuilding.Farmland) {
                g.setColor(Color.green);
                g.drawString(String.valueOf(0), this.buildFarmlandButton.getX(), this.buildFarmlandButton.getY());
            }
            else if(this.unitComponent.getTileComponent().getTile().buildingInProcess != null && this.unitComponent.getTileComponent().getTile().buildingInProcess.object == TypeOfBuilding.Farmland) {
                g.setColor(Color.blue);
                g.drawString(String.valueOf(this.unitComponent.getTileComponent().getTile().buildingInProcess.goal-this.unitComponent.getTileComponent().getTile().buildingInProcess.progress), this.buildFarmlandButton.getX(), this.buildFarmlandButton.getY());
            }
            else {
                g.setColor(Color.black);
                g.drawString(String.valueOf(TypeOfBuilding.Farmland.turnsToBuild), this.buildFarmlandButton.getX(), this.buildFarmlandButton.getY());
            }

            this.buildMineButton.render(container, g);
            if(this.unitComponent.getTileComponent().getTile().getTypeOfBuilding() == TypeOfBuilding.Mine) {
                g.setColor(Color.green);
                g.drawString(String.valueOf(0), this.buildMineButton.getX(), this.buildMineButton.getY());
            }
            else if(this.unitComponent.getTileComponent().getTile().buildingInProcess != null && this.unitComponent.getTileComponent().getTile().buildingInProcess.object == TypeOfBuilding.Mine) {
                g.setColor(Color.blue);
                g.drawString(String.valueOf(this.unitComponent.getTileComponent().getTile().buildingInProcess.goal-this.unitComponent.getTileComponent().getTile().buildingInProcess.progress), this.buildMineButton.getX(), this.buildMineButton.getY());
            }
            else {
                g.setColor(Color.black);
                g.drawString(String.valueOf(TypeOfBuilding.Mine.turnsToBuild), this.buildMineButton.getX(), this.buildMineButton.getY());
            }

            this.buildSawmillButton.render(container, g);
            if(this.unitComponent.getTileComponent().getTile().getTypeOfBuilding() == TypeOfBuilding.Sawmill) {
                g.setColor(Color.green);
                g.drawString(String.valueOf(0), this.buildSawmillButton.getX(), this.buildSawmillButton.getY());
            }
            else if(this.unitComponent.getTileComponent().getTile().buildingInProcess != null && this.unitComponent.getTileComponent().getTile().buildingInProcess.object == TypeOfBuilding.Sawmill) {
                g.setColor(Color.blue);
                g.drawString(String.valueOf(this.unitComponent.getTileComponent().getTile().buildingInProcess.goal-this.unitComponent.getTileComponent().getTile().buildingInProcess.progress), this.buildSawmillButton.getX(), this.buildSawmillButton.getY());
            }
            else {
                g.setColor(Color.black);
                g.drawString(String.valueOf(TypeOfBuilding.Sawmill.turnsToBuild), this.buildSawmillButton.getX(), this.buildSawmillButton.getY());
            }
            this.buildNoneButton.render(container, g);
        }
        this.exitButton.render(container, g);
    }

    public void update() {
        if(construct) {
            TwoTTT<LinkedHashMap<String, TwoTTT<TypeOfBuilding, Boolean>>, RoadBridge> list = ((ConstructSomethingOnTile) this.unitComponent.getUnit().Abilities.get(0)).prepareList();
            Iterator<TwoTTT<TypeOfBuilding, Boolean>> iterator = ((LinkedHashMap<String, TwoTTT<TypeOfBuilding, Boolean>>) list.first).values().iterator();
            this.buildFarmlandButton.setLocked(true);
            this.buildMineButton.setLocked(true);
            this.buildSawmillButton.setLocked(true);
            this.buildNoneButton.setLocked(true);
            while(iterator.hasNext()) {
                TwoTTT<TypeOfBuilding, Boolean> tmpTTT = iterator.next();
                if(tmpTTT.first == TypeOfBuilding.Farmland && tmpTTT.second) {
                    this.buildFarmlandButton.setLocked(false);
                }
                else if(tmpTTT.first == TypeOfBuilding.Mine && tmpTTT.second) {
                    this.buildMineButton.setLocked(false);
                }
                else if(tmpTTT.first == TypeOfBuilding.Sawmill && tmpTTT.second) {
                    this.buildSawmillButton.setLocked(false);
                }
                else if(tmpTTT.first == TypeOfBuilding.none && tmpTTT.second) {
                    this.buildNoneButton.setLocked(false);
                }
            }
        }
        if(getCargo) {
            if(((GetCargoSmall) this.unitComponent.getUnit().Abilities.get(0)).isCargoFull) {
                this.getCargoButton.setImage(this.putCargoImage);
            }
            else {
                this.getCargoButton.setImage(this.getCargoImage);
            }
        }
        this.moveButton.setSelected(false);
        this.attackButton.setSelected(false);
        this.getCargoButton.setSelected(false);
        if(this.unitComponent.getState() == UnitState.PREPARE_TO_MOVE) {
            this.moveButton.setSelected(true);
            this.attackButton.setSelected(false);
            this.getCargoButton.setSelected(false);
        }
        if(this.unitComponent.getState() == UnitState.PREPARE_TO_ATTACK) {
            this.moveButton.setSelected(false);
            this.attackButton.setSelected(true);
            this.getCargoButton.setSelected(false);
        }
        if(this.unitComponent.getState() == UnitState.PREPARE_CARGO) {
            this.moveButton.setSelected(false);
            this.attackButton.setSelected(false);
            this.getCargoButton.setSelected(true);
        }
    }

    //public void update(GameContainer gameContainer, int delta) throws SlickException {

    //}

    @Override
    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        super.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.moveButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.attackButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        if(colonise)this.coloniseButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.exitButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        if(getCargo) {
            this.getCargoButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        }
        if(construct) {
            this.buildFarmlandButton.mouseMovedSignalise(oldx, oldy, newx, newy);
            this.buildMineButton.mouseMovedSignalise(oldx, oldy, newx, newy);
            this.buildSawmillButton.mouseMovedSignalise(oldx, oldy, newx, newy);
            this.buildNoneButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        }
    }

    @Override
    public void mousePressedSignalise(int button, int x, int y) {
        super.mousePressedSignalise(button, x, y);
        this.moveButton.mousePressedSignalise(button, x, y);
        this.attackButton.mousePressedSignalise(button, x, y);
        if(colonise)this.coloniseButton.mousePressedSignalise(button, x, y);
        this.exitButton.mousePressedSignalise(button, x, y);
        if(getCargo) {
            this.getCargoButton.mousePressedSignalise(button, x, y);
        }
        if(construct) {
            this.buildFarmlandButton.mousePressedSignalise(button, x, y);
            this.buildMineButton.mousePressedSignalise(button, x, y);
            this.buildSawmillButton.mousePressedSignalise(button, x, y);
            this.buildNoneButton.mousePressedSignalise(button, x, y);
        }
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(this.exit) return;
        if(abstractComponent instanceof SelectButtonComponent) {
            if(abstractComponent == moveButton) {
                this.unitComponent.prepareToMove();
            }
            if(abstractComponent == attackButton) {
                this.unitComponent.prepareToAttack();
            }
            if(abstractComponent == getCargoButton) {
                this.unitComponent.prapareCargo();
            }
        }
        else if(abstractComponent instanceof  ButtonComponent) {
            if(abstractComponent == this.exitButton) {
                this.exit = true;
                this.unitComponent.setState(UnitState.IDLE);
            }
            if(abstractComponent == coloniseButton) {
                //this.unitComponent.getUnit().Abilities
                if(this.unitComponent.colonise()) {
                    this.exit = true;
                }

            }
            if(abstractComponent == buildFarmlandButton) {
                this.unitComponent.buildFarmland();
            }
            if(abstractComponent == buildMineButton) {
                this.unitComponent.buildMine();
            }
            if(abstractComponent == buildSawmillButton) {
                this.unitComponent.buildSawmill();
            }
            if(abstractComponent == buildNoneButton) {
                this.unitComponent.buildNone();
            }
        }
        else if(abstractComponent instanceof GameMapComponent) {
            if(this.unitComponent.getState() == UnitState.PREPARE_TO_MOVE) {
                this.unitComponent.move((GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile());
            }
            else if(this.unitComponent.getState() == UnitState.PREPARE_TO_ATTACK) {
                this.unitComponent.attack((GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile());
            }
            else if(this.unitComponent.getState() == UnitState.PREPARE_CARGO) {
                this.unitComponent.relocateCargo((GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile());
            }
        }
        update();
    }

    public void setUnitComponent(UnitComponent unitComponent) {
        this.unitComponent = unitComponent;
        init();
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
        if(exit){
            this.unitComponent.setState(UnitState.IDLE);
        }
    }

    public UnitComponent getUnitComponent() {
        return unitComponent;
    }
}
