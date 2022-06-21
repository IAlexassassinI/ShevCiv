package graphics.components.button;

import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class ButtonComponent extends AbstractComponent {

    private float width;
    private float height;

    private float x;
    private float y;

    private Image image;

    private Color currentColor;
    private Color normalColor;
    private Color backgroundColor;
    private Color mouseOverColor;

    private boolean mouseOver;
    private boolean mouseDown;
    private boolean locked;

    public ButtonComponent(GUIContext container, Image image, float x, float y, float width, float height) {
        super(container);
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.normalColor = Color.white;
        this.backgroundColor = Color.white;
        this.mouseOverColor = new Color(1,1,1, 0.8f);
        this.currentColor = this.normalColor;
        this.mouseOver = false;
        this.mouseDown = false;
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        if(this.mouseDown || this.mouseOver) {
            graphics.setColor(this.backgroundColor);
            graphics.fillRect(this.x, this.y, this.width, this.height);
        }
        this.image.draw(this.x, this.y, this.width, this.height, this.currentColor);
    }

    @Override
    public void setLocation(int i, int i1) {

    }

    public boolean consists(float x, float y) {
        return x >= this.x && x < this.x + this.width && x >= this.y && y < this.y + this.height;
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        if(consists(newx, newy) && !this.locked) {
            this.mouseOver = true;
            this.currentColor = this.mouseOverColor;
            this.backgroundColor = Color.black;
        }
        else {
            this.currentColor = normalColor;
            this.mouseOver = false;
        }
    }

    @Override
    public void mousePressed(int button, int x, int y) {
        if(consists(x, y) && button == Input.MOUSE_LEFT_BUTTON && !this.locked) {
            this.mouseDown = true;
            this.currentColor = this.mouseOverColor;
            this.backgroundColor = Color.white;
            notifyListeners();
        }
        else {
            this.currentColor = normalColor;
            this.mouseDown = false;
        }
    }

    @Override
    public int getX() {
        return (int) this.x;
    }

    @Override
    public int getY() {
        return (int) this.y;
    }

    @Override
    public int getWidth() {
        return (int) this.width;
    }

    @Override
    public int getHeight() {
        return (int) this.height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
