package Processing.Utilits.TileFinder;

import Processing.Player.Player;
import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import Processing.Units.Projectile;
import Processing.Units.Unit;
import Processing.Utilits.GeneralUtility;
import Processing.Utilits.Point;
import Processing.Utilits.Wrapers.ThreeTTT;
import Processing.Utilits.Wrapers.TwoTTT;
import Processing.Utilits.WhereCanBe;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class LightPlay implements Serializable {
    static final long serialVersionUID = 22L;
    private static GameMap CurrentMap;
    private static Unit CurrentUnit;
    private static Projectile CurrentProjectile;
    private static HashMap<Point,Tile> LightMap;
    private static HashMap<Point, Tile> UnitMap;
    private static HashMap<Point, Tile> CitiMap;

    public static TwoTTT<Tile[], HashMap<Point, Tile>> findShootingRange(Tile startTile){
        LightMap = new HashMap<>();
        UnitMap = new HashMap<>();
        CurrentMap = startTile.map;
        CurrentUnit = startTile.unit;
        CurrentProjectile = CurrentUnit.typeOfUnit.projectile;
        double shootingRange = CurrentUnit.typeOfUnit.rangeOfAttack * startTile.resource.battleModifier.additionalShootingRange * startTile.typeOfBuilding.battleModifier.additionalShootingRange * startTile.typeOfFlora.battleModifier.additionalShootingRange * startTile.typeOfLand.battleModifier.additionalShootingRange;
        shootingRange = shootingRange * startTile.unit.owner.battleModifier.additionalShootingRange;

        drawCircle(startTile.coordinates.x, startTile.coordinates.y, GeneralUtility.Round(shootingRange), true, true, false);
        return new TwoTTT<Tile[], HashMap<Point, Tile>>(LightMap.values().toArray(new Tile[0]), UnitMap);
    }

    public static ThreeTTT<Tile[], HashMap<Point, Tile>, HashMap<Point, Tile>> AI_Vision(Tile startTile){
        LightMap = new HashMap<>();
        UnitMap = new HashMap<>();
        CitiMap = new HashMap<>();
        CurrentMap = startTile.map;
        CurrentUnit = startTile.unit;
        if(CurrentUnit.typeOfUnit.isFlying){
            CurrentProjectile = Projectile.LightFlying; //TODO Перейти по всій програмі в модінг формат типу Projectile.AllTypeOfProjectile.get("NAME")
        }
        else{
            CurrentProjectile = Projectile.LightLand;
        }
        double visionRange = CurrentUnit.typeOfUnit.visionRange * startTile.resource.battleModifier.additionalVisionRange * startTile.typeOfBuilding.battleModifier.additionalVisionRange * startTile.typeOfFlora.battleModifier.additionalVisionRange * startTile.typeOfLand.battleModifier.additionalVisionRange;
        while((GeneralUtility.Round(visionRange) > 0)){
            if(GeneralUtility.Round(visionRange) == 1){
                //really costil
                drawCircle(startTile.coordinates.x, startTile.coordinates.y, GeneralUtility.Round(visionRange), true, false, true);
            }
            else{
                Circle(startTile.coordinates.x, startTile.coordinates.y, GeneralUtility.Round(visionRange), true, false, true);
            }
            visionRange--;
        }
        return new ThreeTTT<>(LightMap.values().toArray(new Tile[0]), UnitMap, CitiMap);
    }

    public static Tile[] findVisionRange(Tile startTile){
        LightMap = new HashMap<>();
        CurrentMap = startTile.map;
        CurrentUnit = startTile.unit;
        if(CurrentUnit.typeOfUnit.isFlying){
            CurrentProjectile = Projectile.LightFlying; //TODO Перейти по всій програмі в модінг формат типу Projectile.AllTypeOfProjectile.get("NAME")
        }
        else{
            CurrentProjectile = Projectile.LightLand;
        }
        double visionRange = CurrentUnit.typeOfUnit.visionRange * startTile.resource.battleModifier.additionalVisionRange * startTile.typeOfBuilding.battleModifier.additionalVisionRange * startTile.typeOfFlora.battleModifier.additionalVisionRange * startTile.typeOfLand.battleModifier.additionalVisionRange;
        //drawCircle(startTile.coordinates.x, startTile.coordinates.y, GeneralUtility.Round(visionRange), false, false);
        while((GeneralUtility.Round(visionRange) > 0)){
            if(GeneralUtility.Round(visionRange) == 1){
                //really costil
                drawCircle(startTile.coordinates.x, startTile.coordinates.y, GeneralUtility.Round(visionRange), false, false, false);
            }
            else{
                Circle(startTile.coordinates.x, startTile.coordinates.y, GeneralUtility.Round(visionRange), false, false, false);
            }
            visionRange--;
        }
        return LightMap.values().toArray(new Tile[0]);
    }



    static boolean makePutPoint(int x, int y, boolean withUnitsArray, boolean findingInVisionRange, boolean withCitiesArray){
        Tile TMP_Tile = CurrentMap.getTile(x,y);
        if(TMP_Tile != null){
            boolean terrainCheck = WhereCanBe.FullCheck(TMP_Tile, CurrentProjectile.whereCanBe);
            if(findingInVisionRange){
                if(!TMP_Tile.isVisibleFor(CurrentUnit.owner)){
                    if(terrainCheck){
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
            if(withUnitsArray){
                if(TMP_Tile.unit != null){
                    UnitMap.put(TMP_Tile.coordinates, TMP_Tile);
                }
                LightMap.put(TMP_Tile.coordinates, TMP_Tile);
            }
            else{
                LightMap.put(TMP_Tile.coordinates, TMP_Tile);
            }
            if(withCitiesArray){
                if(TMP_Tile.city != null){
                    CitiMap.put(TMP_Tile.coordinates, TMP_Tile);
                }
            }
            if(terrainCheck){
                return true;
            }
        }
        return false;
    }

    /*
    static void drawLine(int x0, int y0, int x1, int y1, boolean withUnitsArray, boolean findingInVisionRange) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x1 >= x0 ? 1 : -1;
        int sy = y1 >= y0 ? 1 : -1;

        int de;
        int d;
        int d1;
        int d2;
        int y = y0;
        int x = x0;
        int main_sx = 0;
        int sub_sx = 0;
        int main_sy = 0;
        int sub_sy = 0;

        if(dy <= dx){
            de = dx;
            d = (dy << 1) - dx;
            d1 = dy << 1;
            d2 = (dy - dx) << 1;
            x = x + sx;
            main_sx = sx;
            sub_sy = sy;
        }
        else{
            de = dy;
            d = (dx << 1) - dy;
            d1 = dx << 1;
            d2 = (dx - dy) << 1;
            y = y + sy;
            main_sy = sy;
            sub_sx = sx;
        }

        if(!makePutPoint(x0, y0, withUnitsArray, findingInVisionRange)){
            return;
        }
        for(int i = 1; i <= de; i++) {
            if(d > 0) {
                d = d + d2;
                x = x + sub_sx;
                y = y + sub_sy;
            }else{
                d = d + d1;
            }
            x = x + main_sx;
            y = y + main_sy;

        }
        if(!makePutPoint(x0, y0, withUnitsArray, findingInVisionRange)){
            return;
        }

    }

     */


    static void drawLine(int x0, int y0, int x1, int y1, boolean withUnitsArray, boolean findingInVisionRange, boolean withCitiesArray)
    {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = x1 >= x0 ? 1 : -1;
        int sy = y1 >= y0 ? 1 : -1;

        if (dy <= dx)
        {
            int d = (dy << 1) - dx;
            int d1 = dy << 1;
            int d2 = (dy - dx) << 1;
            if(!makePutPoint(x0, y0, withUnitsArray, findingInVisionRange, withCitiesArray)){
                return;
            }
            for(int x = x0 + sx, y = y0, i = 1; i <= dx; i++, x += sx)
            {
                if ( d >0)
                {
                    d += d2;
                    y += sy;
                }
                else
                    d += d1;
                if(!makePutPoint(x, y, withUnitsArray, findingInVisionRange, withCitiesArray)){
                    return;
                }
            }
        }
        else
        {
            int d = (dx << 1) - dy;
            int d1 = dx << 1;
            int d2 = (dx - dy) << 1;
            if(!makePutPoint(x0, y0, withUnitsArray, findingInVisionRange, withCitiesArray)){
                return;
            }
            for(int y = y0 + sy, x = x0, i = 1; i <= dy; i++, y += sy)
            {
                if ( d >0)
                {
                    d += d2;
                    x += sx;
                }
                else
                    d += d1;
                if(!makePutPoint(x, y, withUnitsArray, findingInVisionRange, withCitiesArray)){
                    return;
                }
            }
        }
    }

    static void plot_circle(int x, int y, int x_center, int  y_center, boolean withUnitsArray, boolean findingInVisionRange, boolean withCitiesArray)
    {
        drawLine(x_center,y_center,x_center+x,y_center+y, withUnitsArray, findingInVisionRange, withCitiesArray);
        drawLine(x_center,y_center,x_center-x,y_center+y, withUnitsArray, findingInVisionRange, withCitiesArray);
        drawLine(x_center,y_center,x_center+x,y_center-y, withUnitsArray, findingInVisionRange, withCitiesArray);
        drawLine(x_center,y_center,x_center-x,y_center-y, withUnitsArray, findingInVisionRange, withCitiesArray);
    }

    /* Вычерчивание окружности с использованием алгоритма Мичнера */
    static void Circle(int x_center, int y_center, int radius, boolean withUnitsArray, boolean findingInVisionRange , boolean withCitiesArray)
    {
        int x,y,delta;
        x = 0;
        y = radius;
        delta=3-2*radius;
        while(x<y) {
            plot_circle(x,y,x_center,y_center,withUnitsArray, findingInVisionRange, withCitiesArray);
            plot_circle(y,x,x_center,y_center,withUnitsArray, findingInVisionRange, withCitiesArray);
            if (delta<0)
                delta+=4*x+6;
            else {
                delta+=4*(x-y)+10;
                y--;
            }
            x++;
        }

        if(x==y) plot_circle(x,y,x_center,y_center,withUnitsArray, findingInVisionRange, withCitiesArray);
    }

    static void drawCircle(int x0, int y0, int radius, boolean withUnitsArray, boolean findingInVisionRange, boolean withCitiesArray) {
        int x = 0;
        int y = radius;
        int delta = 1 - 2 * radius;
        int error = 0;
        while(y >= 0) {
            drawLine(x0, y0, x0 + x, y0 + y, withUnitsArray, findingInVisionRange, withCitiesArray);
            drawLine(x0, y0,x0 + x, y0 - y, withUnitsArray, findingInVisionRange, withCitiesArray);
            drawLine(x0, y0,x0 - x, y0 + y, withUnitsArray, findingInVisionRange, withCitiesArray);
            drawLine(x0, y0,x0 - x, y0 - y, withUnitsArray, findingInVisionRange, withCitiesArray);
            error = 2 * (delta + y) - 1;
            if(delta < 0 && error <= 0) {
                ++x;
                delta += 2 * x + 1;
                continue;
            }
            error = 2 * (delta - x) - 1;
            if(delta > 0 && error > 0) {
                --y;
                delta += 1 - 2 * y;
                continue;
            }
            ++x;
            delta += 2 * (x - y);
            --y;
        }
    }

    static public void addToPlayerVision(Tile tile, Player player){
        Point coordinates = tile.coordinates;
        player.VisionMap[coordinates.y][coordinates.x]++;
        if(!player.OpenFOWMap[coordinates.y][coordinates.x]){
            player.OpenFOWMap[coordinates.y][coordinates.x] = true;
        }
    }

    static public void addToPlayerVision(Tile tiles[], Player player){
        for(int i = 0; i < tiles.length; i++){
            addToPlayerVision(tiles[i], player);
        }
    }

    static public void addToPlayerVision(LinkedList<Tile> tiles, Player player){
        Iterator<Tile> iterator = tiles.iterator();
        while(iterator.hasNext()){
            addToPlayerVision(iterator.next(), player);
        }
    }

    static public void addToPlayerVision(Unit unit){
        addToPlayerVision(findVisionRange(unit.onTile), unit.owner);
    }

    static public void removeFromPlayerVision(Tile tile, Player player){
        Point coordinates = tile.coordinates;
        player.VisionMap[coordinates.y][coordinates.x]--;
    }

    static public void removeFromPlayerVision(Tile tiles[], Player player){
        for(int i = 0; i < tiles.length; i++){
            removeFromPlayerVision(tiles[i], player);
        }
    }

    static public void removeFromPlayerVision(LinkedList<Tile> tiles, Player player){
        Iterator<Tile> iterator = tiles.iterator();
        while(iterator.hasNext()){
            removeFromPlayerVision(iterator.next(), player);
        }
    }

    static public void removeFromPlayerVision(Unit unit){
        removeFromPlayerVision(findVisionRange(unit.onTile), unit.owner);
    }

}
