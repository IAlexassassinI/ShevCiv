package Processing.Utilits.TileFinder;

import Processing.TileMap.GameMap;
import Processing.TileMap.Tile;
import Processing.Units.Projectile;
import Processing.Units.Unit;
import Processing.Utilits.GeneralUtility;
import Processing.Utilits.Point;
import Processing.Utilits.Wrapers.TwoTTT;
import Processing.Utilits.WhereCanBe;

import java.util.HashMap;

public class LightPlay {

    private static GameMap CurrentMap;
    private static Unit CurrentUnit;
    private static Projectile CurrentProjectile;
    private static HashMap<Point,Tile> LightMap;
    private static HashMap<Point, Tile> UnitMap;

    public static TwoTTT<Tile[], HashMap<Point, Tile>> findShootingRange(Tile startTile){
        LightMap = new HashMap<>();
        UnitMap = new HashMap<>();
        CurrentMap = startTile.map;
        CurrentUnit = startTile.unit;
        CurrentProjectile = CurrentUnit.typeOfUnit.projectile;
        double shootingRange = CurrentUnit.typeOfUnit.rangeOfAttack * startTile.resource.battleModifier.additionalShootingRange * startTile.typeOfBuilding.battleModifier.additionalShootingRange * startTile.typeOfFlora.battleModifier.additionalShootingRange * startTile.typeOfLand.battleModifier.additionalShootingRange;
        drawCircle(startTile.coordinates.x, startTile.coordinates.y, GeneralUtility.Round(shootingRange), true, true);
        return new TwoTTT<Tile[], HashMap<Point, Tile>>(LightMap.values().toArray(new Tile[0]), UnitMap);
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
        drawCircle(startTile.coordinates.x, startTile.coordinates.y, GeneralUtility.Round(visionRange), false, false);
        return LightMap.values().toArray(new Tile[0]);
    }



    static boolean makePutPoint(int x, int y, boolean withUnitsArray, boolean findingInVisionRange){
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
            if(terrainCheck){
                return true;
            }
        }
        return false;
    }

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

    static void drawCircle(int x0, int y0, int radius, boolean withUnitsArray, boolean findingInVisionRange) {
        int x = 0;
        int y = radius;
        int delta = 1 - 2 * radius;
        int error = 0;
        while(y >= 0) {
            drawLine(x0, y0, x0 + x, y0 + y, withUnitsArray, findingInVisionRange);
            drawLine(x0, y0,x0 + x, y0 - y, withUnitsArray, findingInVisionRange);
            drawLine(x0, y0,x0 - x, y0 + y, withUnitsArray, findingInVisionRange);
            drawLine(x0, y0,x0 - x, y0 - y, withUnitsArray, findingInVisionRange);
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

    static public void addToPlayerVision(Unit unit){
        Tile TMP_Tiles[] = findVisionRange(unit.onTile);
        for(int i = 0; i < TMP_Tiles.length; i++){
            Point coordinates = TMP_Tiles[i].coordinates;
            unit.owner.VisionMap[coordinates.y][coordinates.x]++;
            if(!unit.owner.OpenFOWMap[coordinates.y][coordinates.x]){
                unit.owner.OpenFOWMap[coordinates.y][coordinates.x] = true;
            }
        }
    }

    static public void removeFromPlayerVision(Unit unit){
        Tile TMP_Tiles[] = findVisionRange(unit.onTile);
        for(int i = 0; i < TMP_Tiles.length; i++){
            Point coordinates = TMP_Tiles[i].coordinates;
            unit.owner.VisionMap[coordinates.y][coordinates.x]--;
        }
    }

}
