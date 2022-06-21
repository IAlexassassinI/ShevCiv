package graphics.components.camera;

import graphics.components.tiledmap.MapComponent;
import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.GUIContext;

public class Camera extends AbstractComponent {

    private float x;
    private float y;

    private float width;
    private float height;

    private MapComponent map;

    private int mouseX;
    private int mouseY;

    private boolean locked;

    public Camera(GUIContext container, float x, float y, float width, float height, MapComponent map) {
        super(container);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.map = map;
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        //graphics.setColor(Color.red);
        //graphics.fillRect(this.x-10,this.y-10,this.width+20,this.height+20);
        //graphics.setClip((int) this.x, (int) this.y, (int) this.width, (int) this.height);
        map.render(guiContext, graphics);
        //graphics.clearClip();
        //graphics.resetTransform();
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int newx, int newy) {
        if(!contains(oldx, oldy)) return;
        translateMap(newx - oldx, newy - oldy);
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int newx, int newy) {
        this.mouseX = newx;
        this.mouseY = newy;
    }

    @Override
    public void mouseWheelMoved(int change) {
        if(!contains(this.mouseX, this.mouseY)) return;
        scaleMap((1000f + change) / 1000f, this.mouseX, this.mouseY);
    }

    public void translateMap(float dx, float dy) {
        if(this.map.getX() + dx <= this.x && this.map.getX() + this.map.getWidth() + dx >= this.x + this.width) this.map.translate(dx, 0);
        if(this.map.getY() + dy <= this.y && this.map.getY() + this.map.getHeight() + dy >= this.y + this.height) this.map.translate(0, dy);
        /*if(this.mapX + dx + this.width < this.map.getWidth() && this.mapX + dx >= 0) {
            this.mapX += dx;
            translateTiles(-dx, 0);
        }
        else if(this.mapX + dx + this.width >= this.map.getWidth()) {
            translateTiles(-this.map.getWidth(), 0);
        }
        else {
            translateTiles(this.map.getWidth(), 0);
        }*/
        //System.out.println(this.mapX + this.map.getWidth());
        /*if(this.mapX + this.map.getWidth() < this.x) {
            translateTiles(-this.map.getWidth(), 0);
            translateTiles(-dx, 0);
            System.out.println("1");
        }
        else if(this.mapX > this.x + this.width) {
            translateTiles(this.map.getWidth(), 0);
            translateTiles(-dx, 0);

            System.out.println("2");
        }
        else {
            translateTiles(-dx, 0);
        }
        if(this.mapY + dy + this.height < this.map.getHeight() && this.mapY + dy >= 0) {
            this.mapY += dy;
            translateTiles(0, -dy);
        }*/
    }

    public void translate(float dx, float dy) {
        this.x += dx;
        this.y += dy;
        this.map.translate(dx, dy);
    }

    public void scaleMap(float k, float px, float py) {
        if(this.map.getWidth() * k < this.width) {
            k = this.width / this.map.getWidth();
            //System.out.println(1 + " " + this.map.getX() + " " + this.map.getY());
        }
        if(this.map.getHeight() * k < this.height) {
            k = this.height / this.map.getHeight();
            //System.out.println(2 + " " + this.map.getX() + " " + this.map.getY());
        }
        this.map.scale(k, px, py);
        if(this.map.getX() > this.x) {
            this.map.translate(this.x - this.map.getX(), 0);
            //System.out.println(3 + " " + this.map.getX() + " " + this.map.getY());
        }
        else if(this.map.getX() + this.map.getWidth() < this.x + this.width) {
            this.map.translate((this.x + this.width) - (this.map.getX() + this.map.getWidth()), 0);
            //System.out.println(4 + " " + this.map.getX() + " " + this.map.getY());
        }
        if(this.map.getY() > this.y) {
            this.map.translate(0, this.y - this.map.getY());
            //System.out.println(5 + " " + this.map.getX() + " " + this.map.getY());
        }
        else if(this.map.getY() + this.map.getHeight() < this.y + this.height) {
            this.map.translate(0, (this.y + this.height) - (this.map.getY() + this.map.getHeight()));
            //System.out.println(6 + " " + this.map.getX() + " " + this.map.getY());
        }
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
    }

    @Override
    public void setLocation(int i, int i1) {

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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /*public void scaleTiles(float scale) {
        TileComponent[][] tiles = this.map.getTiles();
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].scale(scale);
            }
        }
    }*/

    /*private void translateTiles(float dx, float dy) {
        this.mapX -= dx;
        this.mapY -= dy;

        TileComponent[][] tiles = this.map.getTiles();
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].translate(dx, dy);
            }
        }
    }*/

}
