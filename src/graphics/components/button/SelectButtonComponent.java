package graphics.components.button;

import graphics.components.tiledmap.SelectStyle;
import graphics.loads.Sounds;
import org.newdawn.slick.*;
import org.newdawn.slick.gui.GUIContext;

public class SelectButtonComponent extends ButtonComponent {

    private boolean selected = false;
    private SelectStyle selectStyle = SelectStyle.WHITE_BORDER;
    private int id;

    public SelectButtonComponent(GUIContext container, Image image, float x, float y, float width, float height) {
        super(container, image, x, y, width, height);
    }

    @Override
    public void render(GUIContext guiContext, Graphics graphics) throws SlickException {
        if(this.locked) {
            graphics.setColor(Color.black);
            graphics.fillRect(this.x, this.y, this.width, this.height);
            this.image.draw(this.x, this.y, this.width, this.height, this.mouseOverColor);
        }
        else if(this.selected) {
            if(this.selectStyle == SelectStyle.WHITE_BORDER) {
                graphics.setColor(Color.white);
                graphics.fillRoundRect((float) (this.x - this.width * 0.05), (float) (this.y - this.height * 0.05), (float) (this.width * 1.1), (float) (this.height * 1.1), (int) (this.width * 0.05));
                this.image.draw(this.x, this.y, this.width, this.height, this.currentColor);
            }
            else if(this.selectStyle == SelectStyle.BRIGHTER) {
                this.image.draw(this.x, this.y, this.width, this.height, this.currentColor);
                graphics.setColor(new Color(1,1,1,0.2f));
                graphics.fillRect(this.x, this.y, this.width, this.height);
            }
        }
        else if(this.mouseOver) {
            graphics.setColor(this.backgroundColor);
            graphics.fillRect(this.x, this.y, this.width, this.height);
            this.image.draw(this.x, this.y, this.width, this.height, this.currentColor);
        }
        else {
            this.image.draw(this.x, this.y, this.width, this.height, this.currentColor);
        }
    }

    public void mousePressedSignalise(int button, int x, int y) {
        if(contains(x, y) && button == Input.MOUSE_LEFT_BUTTON && !this.locked) {
            Sounds.clickSound.play();
            this.selected = !this.selected;
            this.currentColor = this.normalColor;
            this.backgroundColor = Color.white;
            notifyListeners();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SelectStyle getSelectStyle() {
        return selectStyle;
    }

    public void setSelectStyle(SelectStyle selectStyle) {
        this.selectStyle = selectStyle;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
