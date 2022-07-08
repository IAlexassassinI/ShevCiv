package graphics.panels;

import Processing.Player.Player;
import graphics.components.button.ButtonComponent;
import graphics.components.tiledmap.CityComponent;
import graphics.components.tiledmap.GameMapComponent;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;


public class TechTreePanel extends Panel implements ComponentListener {

    private Player player;
    private ButtonComponent exitButton;
    private ButtonComponent[] researchTech = new ButtonComponent[6];

    public TechTreePanel(GameContainer gameContainer, GameMapComponent gameMapComponent, Player player) {
        super(510, 430, 900, 220, 0);
        this.player = player;
        try {
            this.exitButton = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1890, 0, 30, 30);
            this.exitButton.addListener(this);

            researchTech[0] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 755, 475, 50, 50);
            researchTech[1] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 755, 535, 50, 50);
            researchTech[2] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1055, 475, 50, 50);
            researchTech[3] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1055, 535, 50, 50);
            researchTech[4] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1355, 475, 50, 50);
            researchTech[5] = new ButtonComponent(gameContainer, new Image("assets/graphics/buttons/unit_control/colonise.png"), 1355, 535, 50, 50);

            for(int i = 0; i < researchTech.length; i++) {
                researchTech[i].addListener(this);
            }
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.black);
        g.fillRect(this.x, this.y, this.width, this.height);
        g.setColor(Color.white);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {

    }
}
