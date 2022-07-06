package Processing.Units.Ability;

import Processing.TileMap.Tile;
import Processing.TileMap.TileUtils.RoadBridge;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Units.Unit;
import Processing.Utilits.Point;
import Processing.Utilits.Wrapers.CreatableObject;
import Processing.Utilits.Wrapers.TwoTTT;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ConstructSomethingOnTile extends SpecialAbility implements Serializable {
    static final long serialVersionUID = 15L;
    public static String nameOfAbility = "ConstructSomethingOnTile";
    public static String description = "ConstructSomethingOnTile";
    public static double Cooldown = 1;

    Unit currentUnit;
    public ConstructSomethingOnTile(Unit unit) {
        this.currentUnit = unit;
        currentCooldown = 0;
    }

    static RoadBridge RB = new RoadBridge();
    static TwoTTT<LinkedHashMap<String, TwoTTT<TypeOfBuilding, Boolean>>, RoadBridge> answer = new TwoTTT<>(null, RB);

    public TwoTTT<LinkedHashMap<String, TwoTTT<TypeOfBuilding, Boolean>>, RoadBridge> prepareList(){
        Iterator<TwoTTT<TypeOfBuilding, Boolean>> iter = currentUnit.owner.availableUpgradesOfTile.values().iterator();
        if(currentUnit.onTile.typeOfBuilding == TypeOfBuilding.AllTypeOfBuilding.get(TypeOfBuilding.City.elementName)){
            while(iter.hasNext()){
                iter.next().second = false;
            }
        }
        else{
            while(iter.hasNext()){
                TwoTTT<TypeOfBuilding, Boolean> element = iter.next();
                if((!element.first.onlyInBorder) || (currentUnit.onTile.owner != null && currentUnit.onTile.owner.owner == currentUnit.owner)){
                    if(currentUnit.onTile.typeOfBuilding.equals(element.first)){
                        element.second = false;
                        continue;
                    }
                    if(WhereCanBe.FullCheck(currentUnit.onTile , element.first.whereCanExist)){
                        element.second = true;
                    }
                    else{
                        element.second = false;
                    }
                }
                else{
                    element.second = false;
                }
            }
        }

        answer.first = currentUnit.owner.availableUpgradesOfTile;
        //Bridge Check
        for(int i = 0; i < 8; i++){
            if(!currentUnit.onTile.roadAndBridges.roadAndBridges[i] && currentUnit.onTile.river[i]){
                RB.roadAndBridges[i] = true;
            }
            else{
                RB.roadAndBridges[i] = false;
            }
        }
        //Road Check
        if(!currentUnit.onTile.roadAndBridges.roadAndBridges[Point.CENTER_NUM]){
            RB.roadAndBridges[8] = true;
        }
        else{
            RB.roadAndBridges[8] = false;
        }

        return answer;
    }

    public void designateStructureOnTile(Object toBuild){
        if(toBuild == null){
            return;
        }
        if(toBuild.getClass() == TypeOfBuilding.class){
            if(((TypeOfBuilding) toBuild) != TypeOfBuilding.none){
                currentUnit.onTile.buildingInProcess = new CreatableObject<TypeOfBuilding>((TypeOfBuilding)toBuild, ((TypeOfBuilding)toBuild).turnsToBuild);
                currentUnit.onTile.setTypeOfBuilding(TypeOfBuilding.BuildingInProcess);
            }
            else{
                currentUnit.onTile.buildingInProcess = null;
                currentUnit.onTile.setTypeOfBuilding(TypeOfBuilding.none);
            }
        }
        else {
            RoadBridge TMP_RB = (RoadBridge) toBuild;
            if(TMP_RB.roadAndBridges[8]){
                currentUnit.onTile.buildingInProcess = new CreatableObject<RoadBridge>(TMP_RB, RoadBridge.turnsToBuildRoad);
            }
            else{
                currentUnit.onTile.buildingInProcess = new CreatableObject<RoadBridge>(TMP_RB, RoadBridge.turnsToBuildBridge);
            }
        }
    }

    public void workOnTile(){
        if(isCanUse()){
            if (currentUnit.onTile.buildingInProcess != null) {
                currentUnit.onTile.buildingInProcess.addProgress(1 * currentUnit.typeOfUnit.workEfficiency);
                if (currentUnit.onTile.buildingInProcess.checkStatus()) {
                    apply(currentUnit.onTile);
                }
                setOnCooldown();
            }
        }
    }

    private void apply(Tile tile){
        if(tile.buildingInProcess.object.getClass() == TypeOfBuilding.class){
            tile.setTypeOfBuilding((TypeOfBuilding)tile.buildingInProcess.object);
        }
        else{
            for(int i = 0; i < 9; i++){
                if(((RoadBridge)tile.buildingInProcess.object).roadAndBridges[i]){
                    tile.roadAndBridges.roadAndBridges[i] = true;
                    break;
                }
            }
        }
        tile.buildingInProcess.object = null;
    }

    public void setOnCooldown(){
        this.currentCooldown = Cooldown;
    }



    public double getCooldown() {
        return currentCooldown;
    }

    public boolean isCanUse(){
        return currentCooldown == 0;
    }



}
