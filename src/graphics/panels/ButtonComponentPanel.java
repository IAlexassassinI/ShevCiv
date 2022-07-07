package graphics.panels;

import graphics.components.button.ButtonComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class ButtonComponentPanel extends Panel {

    private ButtonComponent buttonComponent;

    public ButtonComponentPanel(ButtonComponent buttonComponent, float x, float y, float width, float height, int parts) {
        super(x, y, width, height, parts);
        this.buttonComponent = buttonComponent;
        this.buttonComponent.setX(x);
        this.buttonComponent.setY(y);
        this.buttonComponent.setWidth(width);
        this.buttonComponent.setHeight(height);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        this.buttonComponent.render(container, g);
    }

    @Override
    public void translate(float dx, float dy) {
        this.buttonComponent.setX(this.x + dx);
        this.buttonComponent.setY(this.y + dy);
    }

    @Override
    protected void setX(float x) {
        this.buttonComponent.setX(x);
    }

    @Override
    protected void setY(float y) {
        this.buttonComponent.setY(y);
    }

    @Override
    protected void setWidth(float width) {
        this.buttonComponent.setWidth(width);
    }

    @Override
    protected void setHeight(float height) {
        this.buttonComponent.setHeight(height);
    }
}
