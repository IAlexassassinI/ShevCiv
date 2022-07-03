package graphics.panels;

import Processing.Units.Ability.Colonize;
import Processing.Units.Ability.ConstructSomethingOnTile;
import Processing.Units.Ability.GetCargoSmall;
import Processing.Units.Ability.SpecialAbility;
import graphics.components.button.ButtonComponent;
import graphics.components.button.SelectButtonComponent;
import graphics.components.tiledmap.GameMapComponent;
import graphics.components.tiledmap.GameTileComponent;
import graphics.components.tiledmap.UnitComponent;
import graphics.components.tiledmap.UnitState;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

import java.util.ArrayList;

public class UnitControlPanel extends Panel implements ComponentListener {

    private GameMapComponent gameMapComponent;
    private UnitComponent unitComponent;

    private Image attackMelee;
    private Image attackRanged;
    private Image defenceMelee;
    private Image defenceRanged;

    private SelectButtonComponent moveButton;
    private SelectButtonComponent attackButton;
    private SelectButtonComponent coloniseButton;
    private ButtonComponent exitButton;

    private boolean exit = false;
    private boolean colonise = false;
    private boolean construct = false;
    private boolean getCargo = false;

    public UnitControlPanel(GameContainer gameContainer, GameMapComponent gameMapComponent, UnitComponent unitComponent) {
        super(1220, 860, 700, 220, 0);
        this.gameMapComponent = gameMapComponent;
        this.gameMapComponent.addListener(this);
        this.unitComponent = unitComponent;
        try {
            this.moveButton = new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/move.png"), 1440, 920, 50, 50);
            this.attackButton = new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/attack.png"), 1500, 920, 50, 50);
            this.coloniseButton = new SelectButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1560, 920, 50, 50);
            this.exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/exit.png"), 1890, 860, 30, 30);

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
        init();
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
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.black);
        g.fillRect(1220, 860, 700, 220);

        this.unitComponent.getCurrentAnimation().draw(1230, 870, 200, 200);

        g.setColor(Color.white);
        g.drawString("ACP: " + this.unitComponent.getUnit().currentActionPoints + "/" + this.unitComponent.getUnit().typeOfUnit.maxActionPoints, 1230, 870);
        g.drawString("ATP: " + this.unitComponent.getUnit().currentNumberOfAttacks + "/" + this.unitComponent.getUnit().typeOfUnit.maxNumberOfAttacks, 1230, 890);
        this.attackMelee.draw(1230, 910, 20,20);
        g.drawString(String.valueOf(this.unitComponent.getUnit().typeOfUnit.attackMelee), 1250, 910);
        this.attackRanged.draw(1230, 930, 20,20);
        g.drawString(String.valueOf(this.unitComponent.getUnit().typeOfUnit.rangedAttack), 1250, 930);
        this.defenceMelee.draw(1230, 950, 20,20);
        g.drawString(String.valueOf(this.unitComponent.getUnit().typeOfUnit.defenceMelee), 1250, 950);
        this.defenceRanged.draw(1230, 970, 20,20);
        g.drawString(String.valueOf(this.unitComponent.getUnit().typeOfUnit.defenceRanged), 1250, 970);
        g.drawString(unitComponent.getUnit().typeOfUnit.NameOfUnit, 1440, 880);

        g.setColor(Color.gray);
        g.fillRoundRect(1440, 1000,470, 40, 5);
        g.setColor(Color.green);
        g.fillRoundRect(1440, 1000, (float) (470.0 / this.unitComponent.getUnit().typeOfUnit.maxHitPoints * this.unitComponent.getUnit().currentHitPoints), 40, 5);
        g.setColor(Color.white);
        g.drawString( this.unitComponent.getUnit().currentHitPoints + "/" + this.unitComponent.getUnit().typeOfUnit.maxHitPoints, 1640, 1011);

        this.moveButton.render(container, g);
        this.attackButton.render(container, g);
        //if(this.unitComponent.getUnit().Abilities.contains())
        if(colonise) {
            this.coloniseButton.render(container, g);
        }
        this.exitButton.render(container, g);
    }

    @Override
    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        super.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.moveButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.attackButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        if(colonise)this.coloniseButton.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.exitButton.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressedSignalise(int button, int x, int y) {
        super.mousePressedSignalise(button, x, y);
        this.moveButton.mousePressedSignalise(button, x, y);
        this.attackButton.mousePressedSignalise(button, x, y);
        if(colonise)this.coloniseButton.mousePressedSignalise(button, x, y);
        this.exitButton.mousePressedSignalise(button, x, y);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(abstractComponent instanceof SelectButtonComponent) {
            if(abstractComponent == moveButton) {
                this.unitComponent.prepareToMove();
                this.attackButton.setSelected(false);
                this.coloniseButton.setSelected(false);
            }
            if(abstractComponent == attackButton) {
                this.unitComponent.prepareToAttack();
                this.moveButton.setSelected(false);
                this.coloniseButton.setSelected(false);
            }
            if(abstractComponent == coloniseButton) {
                //this.unitComponent.getUnit().Abilities
                this.unitComponent.colonise();
                this.moveButton.setSelected(false);
                this.attackButton.setSelected(false);

            }
        }
        else if(abstractComponent instanceof  ButtonComponent) {
            if(abstractComponent == this.exitButton) {
                this.exit = true;
            }
        }
        else if(abstractComponent instanceof GameMapComponent) {
            if(this.unitComponent.getState() == UnitState.PREPARE_TO_MOVE) {
                this.unitComponent.move((GameTileComponent) ((GameMapComponent) abstractComponent).getSelectedTile());
            }
            this.moveButton.setSelected(false);
            this.attackButton.setSelected(false);
            this.coloniseButton.setSelected(false);
        }
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
            this.moveButton.setSelected(false);
            this.attackButton.setSelected(false);
            this.coloniseButton.setSelected(false);
        }
    }
}
