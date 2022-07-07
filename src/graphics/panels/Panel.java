package graphics.panels;

import graphics.components.button.ButtonComponent;
import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.MouseOverArea;

import java.lang.invoke.TypeDescriptor;
import java.util.ArrayList;

public class Panel {

    protected ArrayList<Panel> panels;
    private Orientation orientation;

    private Panel parent;

    protected float x;
    protected float y;

    protected float width;
    protected float height;

    private int parts;
    private int usedParts;

    private float offset = 0;

    private boolean locked = true;

    public Panel(float x, float y, float width, float height, int parts) {
        this.orientation = Orientation.HORIZONTAL;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parts = parts;
        this.usedParts = 0;
        this.panels = new ArrayList<>();
    }

    public Panel(Orientation orientation, float x, float y, float width, float height, int parts) {
        this.orientation = orientation;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.usedParts = 0;
        this.parts = parts;
        this.panels = new ArrayList<>();
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        for(Panel panel : this.panels) {
            panel.render(container, g);
        }
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
    }

    public void add(Image image, int part) {
        float x = this.x;
        float y = this.y;
        float width = this.width - (2 * offset);
        float height = this.height - (2 * offset);
        if(orientation == Orientation.HORIZONTAL) x = this.x + ((this.width / parts) * usedParts) + offset;
        else y = this.y + ((this.height / parts) * usedParts) + offset;
        if(orientation == Orientation.HORIZONTAL) width = (this.width / parts * part) - (2 * offset);
        else height = (this.height / parts * part) - (2 * offset);
        this.panels.add(new ImagePanel(image, x, y, width, height, part));
        this.usedParts += part;
    }

    public void add(Image image, float offset, int part) {
        float x = this.x;
        float y = this.y;
        float width = this.width - (2 * this.offset);
        float height = this.height - (2 * this.offset);
        if(orientation == Orientation.HORIZONTAL) x = this.x + ((this.width / parts) * usedParts) + this.offset;
        else y = this.y + ((this.height / parts) * usedParts) + this.offset;
        if(orientation == Orientation.HORIZONTAL) width = (this.width / parts * part) - (2 * this.offset);
        else height = (this.height / parts * part) - (2 * this.offset);
        ImagePanel imagePanel = new ImagePanel(image, x, y, width, height, part);
        imagePanel.setOffset(offset);
        this.panels.add(imagePanel);
        this.usedParts += part;
    }

    public void add(ButtonComponent buttonComponent, int part) {
        float x = this.x;
        float y = this.y;
        float width = this.width - (2 * offset);
        float height = this.height - (2 * offset);
        if(orientation == Orientation.HORIZONTAL) x = this.x + ((this.width / parts) * usedParts) + offset;
        else y = this.y + ((this.height / parts) * usedParts) + offset;
        if(orientation == Orientation.HORIZONTAL) width = (this.width / parts * part) - (2 * offset);
        else height = (this.height / parts * part) - (2 * offset);
        this.panels.add(new ButtonComponentPanel(buttonComponent, x, y, width, height, part));
        this.usedParts += part;
    }

    public void add(TileComponent tileComponent, int part) {
        float x = this.x;
        float y = this.y;
        float width = this.width - (2 * this.offset);
        float height = this.height - (2 * this.offset);
        if(orientation == Orientation.HORIZONTAL) x = this.x + ((this.width / parts) * usedParts) + this.offset;
        else y = this.y + ((this.height / parts) * usedParts) + this.offset;
        if(orientation == Orientation.HORIZONTAL) width = (this.width / parts * part) - (2 * this.offset);
        else height = (this.height / parts * part) - (2 * this.offset);
        this.panels.add(new TilePanel(tileComponent, x, y, width, height, part));
        this.usedParts += part;
    }

    public void add(TileComponent tileComponent, float offset, int part) {
        float x = this.x;
        float y = this.y;
        float width = this.width - (2 * this.offset);
        float height = this.height - (2 * this.offset);
        if(orientation == Orientation.HORIZONTAL) x = this.x + ((this.width / parts) * usedParts) + this.offset;
        else y = this.y + ((this.height / parts) * usedParts) + this.offset;
        if(orientation == Orientation.HORIZONTAL) width = (this.width / parts * part) - (2 * this.offset);
        else height = (this.height / parts * part) - (2 * this.offset);
        TilePanel tilePanel = new TilePanel(tileComponent, x, y, width, height, part);
        tilePanel.setOffset(offset);
        this.panels.add(tilePanel);
        this.usedParts += part;
    }

    public void add(AbstractComponent component, int part) {

    }

    public void add(MouseOverArea mouseOverArea, Image image, Rectangle rectangle, int part) {
        float x = this.x;
        float y = this.y;
        float width = this.width - (2 * this.offset);
        float height = this.height - (2 * this.offset);
        if(orientation == Orientation.HORIZONTAL) x = this.x + ((this.width / parts) * usedParts) + this.offset;
        else y = this.y + ((this.height / parts) * usedParts) + this.offset;
        if(orientation == Orientation.HORIZONTAL) width = (this.width / parts * part) - (2 * this.offset);
        else height = (this.height / parts * part) - (2 * this.offset);
        this.panels.add(new MouseOverAreaPanel(mouseOverArea, image, rectangle, x, y, width, height, part));
        this.usedParts += part;
    }

    public void add(MouseOverArea mouseOverArea, Image image, Rectangle rectangle, float offset, int part) {
        float x = this.x;
        float y = this.y;
        float width = this.width - (2 * this.offset);
        float height = this.height - (2 * this.offset);
        if(orientation == Orientation.HORIZONTAL) x = this.x + ((this.width / parts) * usedParts) + this.offset;
        else y = this.y + ((this.height / parts) * usedParts) + this.offset;
        if(orientation == Orientation.HORIZONTAL) width = (this.width / parts * part) - (2 * this.offset);
        else height = (this.height / parts * part) - (2 * this.offset);
        MouseOverAreaPanel mouseOverAreaPanel = new MouseOverAreaPanel(mouseOverArea, image, rectangle, x, y, width, height, part);
        mouseOverAreaPanel.setOffset(offset);
        this.panels.add(mouseOverAreaPanel);
        this.usedParts += part;
    }

    public void add(Panel panel, int part) {
        float x = this.x;
        float y = this.y;
        float width = this.width - (2 * this.offset);
        float height = this.height - (2 * this.offset);
        if(orientation == Orientation.HORIZONTAL) x = this.x + ((this.width / parts) * usedParts) + this.offset;
        else y = this.y + ((this.height / parts) * usedParts) + this.offset;
        if(orientation == Orientation.HORIZONTAL) width = (this.width / parts * part) - (2 * this.offset);
        else height = (this.height / parts * part) - (2 * this.offset);
        panel.setX(x);
        panel.setY(y);
        panel.setWidth(width);
        panel.setHeight(height);
        this.panels.add(panel);
        this.usedParts += part;

    }

    public void add(Panel panel, float offset, int part) {
        float x = this.x;
        float y = this.y;
        float width = this.width - (2 * this.offset);
        float height = this.height - (2 * this.offset);
        if(orientation == Orientation.HORIZONTAL) x = this.x + ((this.width / parts) * usedParts) + this.offset;
        else y = this.y + ((this.height / parts) * usedParts) + this.offset;
        if(orientation == Orientation.HORIZONTAL) width = (this.width / parts * part) - (2 * this.offset);
        else height = (this.height / parts * part) - (2 * this.offset);
        panel.setX(x);
        panel.setY(y);
        panel.setWidth(width);
        panel.setHeight(height);
        panel.setOffset(offset);
        this.panels.add(panel);
        this.usedParts += part;

    }

    public void keyPressedSignalise(int key, char c) {
        for(Panel panel : this.panels) {
            panel.keyPressedSignalise(key, c);
        }
    }

    public void keyReleasedSignalise(int key, char c) {
        for(Panel panel : this.panels) {
            panel.keyReleasedSignalise(key, c);
        }
    }

    public void mouseDraggedSignalise(int oldx, int oldy, int newx, int newy) {
        for(Panel panel : this.panels) {
            panel.mouseDraggedSignalise(oldx, oldy, newx, newy);
        }
    }

    public void mouseClickedSignalise(int button, int x, int y, int clickCount) {
        for(Panel panel : this.panels) {
            panel.mouseClickedSignalise(button, x, y, clickCount);
        }
    }

    public void mouseReleasedSignalise(int button, int x, int y) {
        for(Panel panel : this.panels) {
            panel.mouseReleasedSignalise(button, x, y);
        }
    }

    public void mouseWheelMovedSignalise(int newValue) {
        for(Panel panel : this.panels) {
            panel.mouseWheelMovedSignalise(newValue);
        }
    }

    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        for(Panel panel : this.panels) {
            panel.mouseMovedSignalise(oldx, oldy, newx, newy);
        }
    }

    public void mousePressedSignalise(int button, int x, int y) {
        for(Panel panel : this.panels) {
            panel.mousePressedSignalise(button, x, y);
        }
    }

    public Panel getParent() {
        return parent;
    }

    public void setParent(Panel parent) {
        this.parent = parent;
    }

    public int getParts() {
        return this.parts;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void translate(float dx, float dy) {
        setX(this.x + dx);
        setY(this.y + dy);
    }

    protected void setX(float x) {
        float dx = x - this.x;
        for(Panel panel : this.panels) {
            panel.setX(panel.getX() + dx);
        }
        this.x = x;
    }

    protected void setY(float y) {
        float dy = y - this.y;
        for(Panel panel : this.panels) {
            panel.setY(panel.getY() + dy);
        }
        this.y = y;
    }

    protected void setWidth(float width) {
        float kw = width / this.width;
        for(Panel panel : this.panels) {
            panel.setWidth(panel.getWidth() * kw);
        }
        this.width = width;
    }

    protected void setHeight(float height) {
        float kh = height / this.height;
        for(Panel panel : this.panels) {
            panel.setHeight(panel.getHeight() * kh);
        }
        this.height = height;
    }

    public void setHeightParts(float height, int parts) {
        this.height = height;
        this.parts = parts;
    }

    public void setWidthParts(float width, int parts) {
        this.width = width;
        this.parts = parts;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
        for (Panel panel : this.panels) {
            panel.setLocked(locked);
        }
    }

    public Orientation getOrientation() {
        return orientation;
    }
}
