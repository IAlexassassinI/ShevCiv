package graphics.panels;

import graphics.components.button.SelectButtonComponent;
import graphics.components.tiledmap.SelectStyle;
import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

public class SelectionPanel extends Panel implements ComponentListener {
    private SelectButtonComponent selectedComponent;
    private int optionCount;

    public SelectionPanel(Orientation orientation, float x, float y, float width, float height, int parts) {
        super(orientation, x, y, width, height, parts);
        this.selectedComponent = null;
    }

    public void add(SelectButtonComponent selectButton, int part) {
        this.add(new SelectPanel(selectButton, selectButton.getX(),selectButton.getY(),selectButton.getWidth(),selectButton.getHeight(), optionCount), part);
        if(this.optionCount == 0) {
            selectedComponent = selectButton;
            selectButton.setSelected(true);
        }
        selectButton.setId(this.optionCount);
        this.optionCount++;
        selectButton.addListener(this);
    }

    /*@Override
    public void add(TileComponent tileComponent, int part) {
        super.add(tileComponent, part);
        tileComponent.addListener(this);
        tileComponent.setSelectStyle(SelectStyle.WHITE_BORDER);
    }

    @Override
    public void add(TileComponent tileComponent, float offset, int part) {
        super.add(tileComponent, offset, part);
        tileComponent.addListener(this);
        tileComponent.setSelectStyle(SelectStyle.WHITE_BORDER);
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(this.selectedComponent == abstractComponent) selectedComponent = null;
        else if(this.selectedComponent == null) selectedComponent = abstractComponent;
        else {
            if(this.selectedComponent instanceof TileComponent) {
                ((TileComponent) this.selectedComponent).setSelected(false);
                this.selectedComponent = abstractComponent;
            }
        }
    }*/

    public SelectButtonComponent getSelectedComponent() {
        return selectedComponent;
    }

    @Override
    public void componentActivated(AbstractComponent abstractComponent) {
        if(abstractComponent instanceof SelectButtonComponent) {
            ((SelectButtonComponent)selectedComponent).setSelected(false);
            ((SelectButtonComponent)abstractComponent).setSelected(true);
            selectedComponent = (SelectButtonComponent) abstractComponent;
        }
    }

    public class SelectPanel extends Panel {

        SelectButtonComponent selectButton;
        int id;

        public SelectPanel(SelectButtonComponent selectButton, float x, float y, float width, float height, int parts) {
            super(x, y, width, height, parts);
            //this.id = id;
            this.selectButton = selectButton;
            this.selectButton.setX(x);
            this.selectButton.setY(y);
            this.selectButton.setWidth(width);
            this.selectButton.setHeight(height);
            //this.selectButton.addListener(this);
            //selectStyle = SelectStyle.WHITE_BORDER;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public void render(GameContainer container, Graphics g) throws SlickException {
            this.selectButton.render(container, g);
        }

        @Override
        protected void setX(float x) {
            super.setX(x);
            this.selectButton.setX(x);
        }

        @Override
        protected void setY(float y) {
            super.setY(y);
            this.selectButton.setY(y);
        }

        @Override
        protected void setWidth(float width) {
            super.setWidth(width);
            this.selectButton.setWidth(width);
        }

        @Override
        protected void setHeight(float height) {
            super.setHeight(height);
            this.selectButton.setHeight(height);
        }
    }
}
