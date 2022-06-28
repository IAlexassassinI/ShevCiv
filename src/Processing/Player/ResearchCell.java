package Processing.Player;

import Processing.Buildings.Building;
import Processing.City.City;
import Processing.TileMap.TileUtils.TypeOfBuilding;
import Processing.Units.UnitPattern;
import Processing.Utilits.Wrapers.TwoTTT;

import java.util.Iterator;
import java.util.LinkedList;

public class ResearchCell {

    static final public int ENGINEERING_NUM = 0;
    static final public int SOCIETY_NUM = 1;
    static final public int ARCANUM_NUM = 2;

    int typeOfResearch;
    String nameOfResearch;
    String description;
    double researchPointCost;

    LinkedList<String> preRequisitesAND = new LinkedList<>();
    LinkedList<String> preRequisitesOR = new LinkedList<>();
    LinkedList<String> preRequisitesNOT = new LinkedList<>();

    LinkedList<Object> techGiven = new LinkedList<>();

    public void openToTechPlayer(Player player){
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

    public void researchToPlayer(Player player){
        switch(this.typeOfResearch){
            case ENGINEERING_NUM:

                break;
            case SOCIETY_NUM:

                break;
            case ARCANUM_NUM:

                break;
            default:
                break;
        }
    }

    public static void generateNextResearchChooseToPlayer(Player player, int typeOfResearchNum){

    }

}
