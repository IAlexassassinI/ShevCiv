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
        super.translate(dx, dy);
        this.buttonComponent.setX(this.x + dx);
        this.buttonComponent.setY(this.y + dy);
    }

    @Override
    protected void setX(float x) {
        super.setX(x);
        this.buttonComponent.setX(x);
    }

    @Override
    protected void setY(float y) {
        super.setY(y);
        this.buttonComponent.setY(y);
    }

    @Override
    protected void setWidth(float width) {
        super.setWidth(width);
        this.buttonComponent.setWidth(width);
    }

    @Override
    protected void setHeight(float height) {
        super.setHeight(height);
        this.buttonComponent.setHeight(height);
    }

    @Override
    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        super.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.buttonComponent.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressedSignalise(int button, int x, int y) {
        super.mousePressedSignalise(button, x, y);
        this.buttonComponent.mousePressedSignalise(button, x, y);
    }
}
