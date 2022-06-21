package graphics.panels;

import graphics.components.tiledmap.SelectStyle;
import graphics.components.tiledmap.TileComponent;
import org.newdawn.slick.gui.AbstractComponent;
import org.newdawn.slick.gui.ComponentListener;

public class SelectPanel extends Panel implements ComponentListener {

    private AbstractComponent selectedComponent;

    public SelectPanel(Orientation orientation, float x, float y, float width, float height, int parts) {
        super(orientation, x, y, width, height, parts);
        this.selectedComponent = null;
    }

    @Override
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
    }

    public AbstractComponent getSelectedComponent() {
        return selectedComponent;
    }
}
