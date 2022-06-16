package Processing.Utilits;

import java.io.Serializable;

public class Point implements Serializable {
    static final long serialVersionUID = 4L;

    final public static Point CENTER = new Point(0,0);
    final public static Point TOP = new Point(0,-1);
    final public static Point TOP_RIGHT = new Point(1, -1);
    final public static Point RIGHT = new Point(1,0);
    final public static Point BOTTOM_RIGHT = new Point(1,1);
    final public static Point BOTTOM = new Point(0,1);
    final public static Point BOTTOM_LEFT = new Point(-1,1);
    final public static Point LEFT = new Point(-1,0);
    final public static Point TOP_LEFT = new Point(-1,-1);

    final public static int CENTER_NUM = 8;
    final public static int TOP_NUM = 0;
    final public static int TOP_RIGHT_NUM = 1;
    final public static int RIGHT_NUM = 2;
    final public static int BOTTOM_RIGHT_NUM = 3;
    final public static int BOTTOM_NUM = 4;
    final public static int BOTTOM_LEFT_NUM = 5;
    final public static int LEFT_NUM = 6;
    final public static int TOP_LEFT_NUM = 7;

    final public static Point[] ALL_SIDES = new Point[]{CENTER, TOP, TOP_RIGHT, RIGHT, BOTTOM_RIGHT, BOTTOM, BOTTOM_LEFT, LEFT, TOP_LEFT};

    public Point(){
        this.x = 0;
        this.y = 0;
    }

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Point MoveThis(int dx, int dy){
        this.x = this.x + dx;
        this.y = this.y + dy;
        return this;
    }

    public Point LookAt(int dx, int dy) {
        return new Point(this.x + dx, this.y + dy);
    }

    public Point LookAt(Point dPoint) {
        return new Point(this.x + dPoint.x, this.y + dPoint.y);
    }

    public int x;
    public int y;

}
