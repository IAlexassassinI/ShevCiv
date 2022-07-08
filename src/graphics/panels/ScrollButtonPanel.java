package graphics.panels;

import graphics.components.button.ButtonComponent;
import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

public class ScrollButtonPanel extends Panel implements ComponentListener {

    private ButtonComponent button1;
    private ButtonComponent button2;

    private Panel panel;

    private float buttonPart;

    private int scrollingTime;
    private int currentScrollingTime;
    private float currentScrollingLength;

    private boolean scrollingRight;
    private boolean scrollingLeft;
    private boolean scrollingUp;
    private boolean scrollingDown;

    public ScrollButtonPanel(GUIContext container, Image imageButton1, Image imageButton2, float x, float y, float width, float height, int parts) throws SlickException {
        super(x, y, width, height, parts);
        this.buttonPart = 30;
        this.scrollingTime = 500;
        this.currentScrollingTime = 0;
        this.currentScrollingLength = 0;
        this.scrollingLeft = false;
        this.scrollingRight = false;
        this.scrollingUp = false;
        this.scrollingDown = false;
        button1 = new ButtonComponent(container, imageButton1, x, y, width / parts * buttonPart, height);
        button2 = new ButtonComponent(container, imageButton2, x + width - width / parts * buttonPart, y, width / parts * buttonPart, height);
        button1.addListener(this);
        button2.addListener(this);
        //this.panels.add(button1);
        //this.panels.add(button2);
    }

    public ScrollButtonPanel(GUIContext container, Orientation orientation, Image imageButton1, Image imageButton2, float x, float y, float width, float height, int parts) {
        super(orientation, x, y, width, height, parts);
        this.buttonPart = 30;
        this.scrollingTime = 500;
        this.currentScrollingTime = 0;
        this.currentScrollingLength = 0;
        this.scrollingLeft = false;
        this.scrollingRight = false;
        this.scrollingUp = false;
        this.scrollingDown = false;
        if(orientation == Orientation.HORIZONTAL) {
            button1 = new ButtonComponent(container, imageButton1, x, y, width / parts * buttonPart, height);
            button2 = new ButtonComponent(container, imageButton2, x + width - width / parts * buttonPart, y, width / parts * buttonPart, height);
        }
        else if(orientation == Orientation.VERTICAL) {
            button1 = new ButtonComponent(container, imageButton1, x, y, width, height / parts * buttonPart);
            button2 = new ButtonComponent(container, imageButton2, x, y + height - height / parts * buttonPart, width, height / parts * buttonPart);
        }
        button1.addListener(this);
        button2.addListener(this);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        if(getOrientation() == Orientation.HORIZONTAL) {
            g.setClip((int) getX() + this.button1.getWidth(), (int) getY(), (int) getWidth() - 2 * this.button2.getWidth(), (int) getHeight());
            this.panel.render(container, g);
            g.clearClip();
            g.resetTransform();
        }
        else if(getOrientation() == Orientation.VERTICAL) {
            g.setClip((int) getX(), (int) getY() + this.button1.getHeight(), (int) getWidth(), (int) getHeight() - 2 * this.button2.getHeight());
            this.panel.render(container, g);
            g.clearClip();
            g.resetTransform();
        }
        this.button1.render(container, g);
        this.button2.render(container, g);
    }

    public void update(GameContainer gameContainer, int delta) throws SlickException {
        if(this.scrollingRight || this.scrollingLeft || this.scrollingUp || this.scrollingDown) {
            button1.setLocked(true);
            button2.setLocked(true);
        }
        else {
            button1.setLocked(false);
            button2.setLocked(false);
        }
        if(this.contains(this.panel.x, this.panel.y)){
            this.button2.setLocked(true);
        }
        if(this.contains(this.panel.x + this.panel.width - 1, this.panel.y + this.panel.height - 1)) {
            this.button1.setLocked(true);
        }
        if(this.scrollingRight) {
            float areaWidth = getWidth() - 2 * this.button2.getWidth();
            if(this.currentScrollingLength + areaWidth / this.scrollingTime * delta >= areaWidth) {
                this.panel.translate(-(areaWidth - this.currentScrollingLength), 0);
                this.scrollingRight = false;
                this.currentScrollingLength = 0;
            }
            else {
                this.panel.translate(-(areaWidth / this.scrollingTime * delta), 0);
                this.currentScrollingLength += areaWidth / this.scrollingTime * delta;
            }
        }
        else if(this.scrollingLeft) {
            float areaWidth = getWidth() - 2 * this.button2.getWidth();
            if(this.currentScrollingLength + areaWidth / this.scrollingTime * delta >= areaWidth) {
                this.panel.translate((areaWidth - this.currentScrollingLength), 0);
                this.scrollingLeft = false;
                this.currentScrollingLength = 0;
            }
            else {
                this.panel.translate((areaWidth / this.scrollingTime * delta), 0);
                this.currentScrollingLength += areaWidth / this.scrollingTime * delta;
            }
        }
        else if(this.scrollingUp) {
            float areaHeight = getHeight() - 2 * this.button2.getHeight();
            if(this.currentScrollingLength + areaHeight / this.scrollingTime * delta >= areaHeight) {
                this.panel.translate(0, (areaHeight - this.currentScrollingLength));
                this.scrollingUp = false;
                this.currentScrollingLength = 0;
            }
            else {
                this.panel.translate(0, (areaHeight / this.scrollingTime * delta));
                this.currentScrollingLength += areaHeight / this.scrollingTime * delta;
            }
        }
        else if(this.scrollingDown) {
            float areaHeight = getHeight() - 2 * this.button2.getHeight();
            if(this.currentScrollingLength + areaHeight / this.scrollingTime * delta >= areaHeight) {
                this.panel.translate(0, -(areaHeight - this.currentScrollingLength));
                this.scrollingUp = false;
                this.currentScrollingLength = 0;
            }
            else {
                this.panel.translate(0, -(areaHeight / this.scrollingTime * delta));
                this.currentScrollingLength += areaHeight / this.scrollingTime * delta;
            }
        }
    }

    @Override
    public boolean contains(float x, float y) {
        //float areaWidth = getWidth() - 2 * this.button2.getWidth();
        if(getOrientation() == Orientation.HORIZONTAL) {
            return x >= this.x + this.button1.getWidth() && x < this.x + this.width - button1.getWidth() && y >= this.y && y < this.y + this.height;
        }
        else if(getOrientation() == Orientation.VERTICAL) {
            return y >= this.y + this.button1.getHeight() && y < this.y + this.height - button1.getHeight() && x >= this.x && x < this.x + this.width;
        }
        else return true;
    }

    @Override
    public void add(Image image, int part) {
        //super.add(image, part);
    }

    @Override
    public void add(Image image, float offset, int part) {
        //super.add(image, offset, part);
    }

    @Override
    public void add(TileComponent tileComponent, int part) {
        //super.add(tileComponent, part);
    }

    @Override
    public void add(TileComponent tileComponent, float offset, int part) {
        //super.add(tileComponent, offset, part);
    }

    @Override
    public void add(AbstractComponent component, int part) {
        //super.add(component, part);
    }

    @Override
    public void add(MouseOverArea mouseOverArea, Image image, Rectangle rectangle, int part) {
        //super.add(mouseOverArea, image, rectangle, part);
    }

    @Override
    public void add(MouseOverArea mouseOverArea, Image image, Rectangle rectangle, float offset, int part) {
        //super.add(mouseOverArea, image, rectangle, offset, part);
    }

    @Override
    public void add(Panel panel, int part) {
        //super.add(panel, part);
        this.panel = panel;
            //this.panels.add(panel);
        if(getOrientation() == Orientation.HORIZONTAL) {
            this.panel.setX(getX() + this.button1.getWidth());
            this.panel.setY(getY());
            this.panel.setHeight(getHeight());
            this.panel.setParent(this);
        }
        else if(getOrientation() == Orientation.VERTICAL) {
            this.panel.setY(getY() + this.button1.getHeight());
            this.panel.setX(getX());
            this.panel.setWidth(getWidth());
            this.panel.setParent(this);
        }
    }

    @Override
    public void add(Panel panel, float offset, int part) {
        //super.add(panel, offset, part);
        if(this.panel == null) {
            this.panel = panel;
            this.panel.setOffset(offset);
            //this.panels.add(panel);
            if(getOrientation() == Orientation.HORIZONTAL) {
                this.panel.setX(getX() + this.button1.getWidth());
                this.panel.setY(getY());
                this.panel.setHeight(getHeight());
                this.panel.setParent(this);
            }
            else if(getOrientation() == Orientation.VERTICAL) {
                this.panel.setY(getY() + this.button1.getHeight());
                this.panel.setX(getX());
                this.panel.setWidth(getWidth());
                this.panel.setParent(this);
            }
        }
    }



    @Override
    public void keyPressedSignalise(int key, char c) {
        this.panel.keyPressedSignalise(key, c);
    }

    @Override
    public void keyReleasedSignalise(int key, char c) {
        this.panel.keyReleasedSignalise(key, c);
    }

    @Override
    public void mouseDraggedSignalise(int oldx, int oldy, int newx, int newy) {
        this.panel.mouseDraggedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mouseClickedSignalise(int button, int x, int y, int clickCount) {
        this.panel.mouseClickedSignalise(button, x, y, clickCount);
    }

    @Override
    public void mouseReleasedSignalise(int button, int x, int y) {
        this.panel.mouseReleasedSignalise(button, x, y);
    }

    @Override
    public void mouseWheelMovedSignalise(int newValue) {
        this.panel.mouseWheelMovedSignalise(newValue);
    }

    @Override
    public void mouseMovedSignalise(int oldx, int oldy, int newx, int newy) {
        this.panel.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.button1.mouseMovedSignalise(oldx, oldy, newx, newy);
        this.button2.mouseMovedSignalise(oldx, oldy, newx, newy);
    }

    @Override
    public void mousePressedSignalise(int button, int x, int y) {
        this.panel.mousePressedSignalise(button, x, y);
        this.button1.mousePressedSignalise(button, x, y);
        this.button2.mousePressedSignalise(button, x, y);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(getOrientation() == Orientation.HORIZONTAL) {
            if(abstractComponent == this.button1) {
                this.scrollingLeft = true;
            }
            else if(abstractComponent == this.button2) {
                this.scrollingRight = true;
            }
        }
        else if(getOrientation() == Orientation.VERTICAL) {
            if(abstractComponent == this.button1) {
                this.scrollingDown = true;
            }
            else if(abstractComponent == this.button2) {
                this.scrollingUp = true;
            }
        }
    }
}
