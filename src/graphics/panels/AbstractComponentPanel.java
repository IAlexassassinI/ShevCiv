package graphics.panels;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;

public class AbstractComponentPanel extends Panel {

    private AbstractComponent abstractComponent;

    public AbstractComponentPanel(AbstractComponent abstractComponent, float x, float y, float width, float height, int parts) {
        super(x, y, width, height, parts);
        this.abstractComponent = abstractComponent;
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        abstractComponent.render(container, g);
    }
}
