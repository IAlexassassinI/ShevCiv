package Processing.Utilits;

import java.io.Serializable;

public class Point implements Serializable {
    static final long serialVersionUID = 4L;

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

    public int x;
    public int y;

}
