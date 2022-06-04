package Processing.Utilits;

import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import Processing.Units.Unit;

import java.util.HashMap;

public class PathFinder {

    final static public double WITHOUT_ROAD_MODIFIER = 1.25D;
    final static public double WITH_ROAD_MODIFIER = 1D;
    final static public double DIAGONAL_MODIFIER = 1.41D;
    final static public double BASIC_AP_COST = 1D;

    static private HashMap<Tile,Path> AllPath;
    static private GameMap CurrentMap;
    static private Unit CurrentUnit;
    public static HashMap<Tile,Path> findMovePath(double currentActionPoints, Tile startTile){
        Path FirstPath = new Path(currentActionPoints);
        AllPath = new HashMap<>();
        AllPath.put(startTile, FirstPath);
        CurrentMap = startTile.map;
        CurrentUnit = startTile.unit;
        Pathfinder(FirstPath);
        return AllPath;
    }

    private static void Pathfinder(Path path){
        for(int i = 1; i < Point.ALL_SIDES.length; i++){
            Tile TMP_Tile = CurrentMap.getTile(Point.ALL_SIDES[i]);
            if(TMP_Tile != null){
                if(TMP_Tile.unit != null){
                    continue;
                }
                if(WhereCanBe.FullCheck(TMP_Tile, CurrentUnit.typeOfUnit.whereCanMove)){
                    if(path.currentActionPoints > 0){
                        double modifiers;
                        if((i & 1) == 0){
                            modifiers = CurrentUnit.typeOfUnit.moveModifier;
                        }
                        else{
                            modifiers = CurrentUnit.typeOfUnit.moveModifier*DIAGONAL_MODIFIER;
                        }
                        double actionCost = BASIC_AP_COST;
                        //final modifier
                        if(TMP_Tile.isThereRoad){
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

                    //TODO TALK ABOUT IT
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

    private static void CaseTree(Path path, Tile CurrentTile, int num, double ActionCost){
        double ActionPointsLeft = path.currentActionPoints - ActionCost;
        switch(num){
            case Point.TOP_NUM:
                if(CurrentTile.isRiverBottom && !CurrentTile.isBridgeBottom){
                    ActionPointsLeft = 0;
                }
                break;
            case Point.TOP_RIGHT_NUM:
                if(CurrentTile.isRiverLeft && CurrentTile.isRiverBottom){
                    ActionPointsLeft = 0;
                }
                if(CurrentTile.isRiverLeft && path.tilePath.peekLast().isRiverRight){
                    ActionPointsLeft = 0;
                }
                if(CurrentTile.isRiverBottom && path.tilePath.peekLast().isRiverTop){
                    ActionPointsLeft = 0;
                }
                break;
            case Point.RIGHT_NUM:
                if(CurrentTile.isRiverLeft && !CurrentTile.isBridgeLeft){
                    ActionPointsLeft = 0;
                }
                break;
            case Point.BOTTOM_RIGHT_NUM:
                if(CurrentTile.isRiverLeft && CurrentTile.isRiverTop){
                    ActionPointsLeft = 0;
                }
                if(CurrentTile.isRiverLeft && path.tilePath.peekLast().isRiverRight){
                    ActionPointsLeft = 0;
                }
                if(CurrentTile.isRiverTop && path.tilePath.peekLast().isRiverBottom){
                    ActionPointsLeft = 0;
                }
                break;
            case Point.BOTTOM_NUM:
                if(CurrentTile.isRiverTop && !CurrentTile.isBridgeTop){
                    ActionPointsLeft = 0;
                }
                break;
            case Point.BOTTOM_LEFT_NUM:
                if(CurrentTile.isRiverRight && CurrentTile.isRiverTop){
                    ActionPointsLeft = 0;
                }
                if(CurrentTile.isRiverRight && path.tilePath.peekLast().isRiverLeft){
                    ActionPointsLeft = 0;
                }
                if(CurrentTile.isRiverTop && path.tilePath.peekLast().isRiverBottom){
                    ActionPointsLeft = 0;
                }
                break;
            case Point.LEFT_NUM:
                if(CurrentTile.isRiverRight && !CurrentTile.isBridgeRight){
                    ActionPointsLeft = 0;
                }
                break;
            case Point.TOP_LEFT_NUM:
                if(CurrentTile.isRiverRight && CurrentTile.isRiverBottom){
                    ActionPointsLeft = 0;
                }
                if(CurrentTile.isRiverRight && path.tilePath.peekLast().isRiverLeft){
                    ActionPointsLeft = 0;
                }
                if(CurrentTile.isRiverBottom && path.tilePath.peekLast().isRiverTop){
                    ActionPointsLeft = 0;
                }
                break;
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
