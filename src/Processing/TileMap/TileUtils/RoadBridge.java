package Processing.TileMap.TileUtils;

import java.io.Serializable;

public class RoadBridge implements Serializable {
    static final long serialVersionUID = 7L;

    public boolean roadAndBridges[];

    public static double turnsToBuildRoad = 2;
    public static double turnsToBuildBridge = 3;

    public RoadBridge(){
        roadAndBridges = new boolean[9];
    }

}
