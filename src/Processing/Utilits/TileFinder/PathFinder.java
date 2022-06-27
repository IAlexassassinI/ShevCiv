package Processing.Utilits.TileFinder;

import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import Processing.Units.Unit;
import Processing.Utilits.Point;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.HashMap;

public class PathFinder implements Serializable {
    static final long serialVersionUID = 24L;
    final static public double WITHOUT_ROAD_MODIFIER = 1D;
    final static public double WITH_ROAD_MODIFIER = 0.5D;
    final static public double DIAGONAL_MODIFIER = 1.41D;
    final static public double BASIC_AP_COST = 1D;

    static private HashMap<Tile,Path> AllPath;
    static private GameMap CurrentMap;
    static private Unit CurrentUnit;
    public static HashMap<Tile,Path> findMovePath(double currentActionPoints, Tile startTile){
        Path FirstPath = new Path(currentActionPoints);
        AllPath = new HashMap<>();
        CurrentMap = startTile.map;
        CurrentUnit = startTile.unit;
        Pathfinder(FirstPath);
        return AllPath;
    }

    private static void Pathfinder(Path path){
        for(int i = 0; i < Point.ALL_SIDES.length-1; i++){
            Tile TileToOrient;
            if(path.tilePath.size() == 0){
                TileToOrient = CurrentUnit.onTile;
            } else{
                TileToOrient = path.tilePath.peekLast();
            }
            Tile TMP_Tile = CurrentMap.getTile(TileToOrient.coordinates.LookAt(Point.ALL_SIDES[i]));
            if(TMP_Tile != null){
                if(TMP_Tile.unit != null){
                    continue;
                }
                if(TMP_Tile.isFogOfWarFor(CurrentUnit.owner)){
                    continue;
                }
                if(WhereCanBe.FullCheck(TMP_Tile, CurrentUnit.typeOfUnit.whereCanMove)){
                    if(path.currentActionPoints > 0){
                        double modifiers;
                        if((i & 1) == 0){
                            modifiers = CurrentUnit.typeOfUnit.moveModifier*DIAGONAL_MODIFIER;
                        }
                        else{
                            modifiers = CurrentUnit.typeOfUnit.moveModifier;
                        }
                        double actionCost = BASIC_AP_COST;
                        //final modifier
                        if(TMP_Tile.isThereRoad()){
                            modifiers = modifiers * WITH_ROAD_MODIFIER;
                            //there dont counted flora mod and resource mod also can be applied WITH_ROAD_MODIFIER that now is 1
                            actionCost = actionCost + TMP_Tile.typeOfBuilding.additionalActionPointCost;
                            actionCost = actionCost + (TMP_Tile.typeOfLand.additionalActionPointCost*CurrentUnit.typeOfUnit.howMuchAffectedByLandAdditionalAPCost);
                        }
                        else{
                            actionCost = actionCost + TMP_Tile.typeOfBuilding.additionalActionPointCost;
                            actionCost = actionCost + (TMP_Tile.typeOfLand.additionalActionPointCost*CurrentUnit.typeOfUnit.howMuchAffectedByLandAdditionalAPCost);
                            actionCost = actionCost + (TMP_Tile.typeOfFlora.additionalActionPointCost*CurrentUnit.typeOfUnit.howMuchAffectedByFloraAdditionalAPCost);
                            actionCost = actionCost + (TMP_Tile.resource.additionalActionPointCost*CurrentUnit.typeOfUnit.howMuchAffectedByResourceAdditionalAPCost);

                            modifiers = modifiers * WITHOUT_ROAD_MODIFIER;
                        }
                        CaseTree(path, TMP_Tile, i, actionCost*modifiers);
                    }

                    /*
                    //Like in civ
                    if(path.currentActionPoints > 0){
                        CaseTree(path, TMP_Tile, i, actionCost*modifiers);
                    }
                    //More hard variant
                    if(path.currentActionPoints >= actionCost*modifiers){
                        CaseTree(path, TMP_Tile, i, actionCost*modifiers);
                    }
                     */
                }
            }
        }
    }

    private static void CaseTree(Path path, Tile CurrentTile, int direction, double ActionCost){
        double ActionPointsLeft = path.currentActionPoints - ActionCost;

        if(CurrentTile.isRiver(direction) && !CurrentTile.isBridge(direction)){
            ActionPointsLeft = 0;
        }

        if(AllPath.containsKey(CurrentTile)){
            if(AllPath.get(CurrentTile).currentActionPoints >= ActionPointsLeft){
                return;
            }
        }
        Path NewPath = new Path(path, CurrentTile, ActionPointsLeft);
        AllPath.put(CurrentTile, NewPath);
        Pathfinder(NewPath);
    }



}
