package Processing.Utilits;

import java.io.Serializable;

public class Wealth implements Serializable {
    static final long serialVersionUID = 5L;

    public Wealth(){
        this.production = 0;
    }
    public int production;

    public Wealth dWealth(Wealth dWealth){
        this.production = this.production + dWealth.production;
        return this;
    }

    public Wealth toZero(){
        this.production = 0;
        return this;
    }

}
