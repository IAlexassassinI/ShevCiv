package Processing.Player;

import Processing.Buildings.Building;
import Processing.City.City;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Units.UnitPattern;
import Processing.Utilits.Wrapers.TwoTTT;

import java.util.Iterator;
import java.util.LinkedList;

public class ResearchCell {

    static public int ENGINEERING_NUM = 0;
    static public int SOCIETY_NUM = 1;
    static public int ARCANUM_NUM = 2;

    String nameOfResearch;
    String description;
    double researchPointCost;

    LinkedList<String> preRequisitesAND = new LinkedList<>();
    LinkedList<String> preRequisitesOR = new LinkedList<>();
    LinkedList<String> preRequisitesNOT = new LinkedList<>();

    LinkedList<Object> techGiven = new LinkedList<>();

    public void openToPlayer(Player player){
        Iterator<Object> iterator = techGiven.iterator();
        while(iterator.hasNext()){
            Object researchedObject = iterator.next();
            if(researchedObject.getClass() == Building.class){
                player.buildings.put(((Building) researchedObject).name, (Building) researchedObject);
            }
            else if(researchedObject.getClass() == UnitPattern.class){
                player.unitPatterns.put(((UnitPattern) researchedObject).NameOfUnit, ((UnitPattern) researchedObject));
            }
            else if(researchedObject.getClass() == TypeOfBuilding.class){
                player.availableUpgradesOfTile.put(((TypeOfBuilding) researchedObject).elementName, new TwoTTT<>(((TypeOfBuilding) researchedObject), false));
            }
        }
        Iterator<City> cityIterator = player.playerCities.iterator();
        while(cityIterator.hasNext()){
            cityIterator.next().addBooleanToBuildings();
        }
    }

}
