package Processing.Utilits;

import java.io.Serializable;

public class Wealth implements Serializable {
    static final long serialVersionUID = 32L;

    public Wealth(){
        this.production = 0;
        this.money = 0;
        this.food = 0;
        this.engineeringScience = 0;
        this.societyScience = 0;
        this.arcanumScience = 0;
    }

    public Wealth(double production, double money, double food, double engineeringScience, double societyScience, double arcanumScience){
        this.production = production;
        this.money = money;
        this.food = food;
        this.engineeringScience = engineeringScience;
        this.societyScience = societyScience;
        this.arcanumScience = arcanumScience;
    }

    public double production;
    public double money;
    public double food;
    public double engineeringScience;
    public double societyScience;
    public double arcanumScience;

    public Wealth dWealth(Wealth dWealth){
        this.production += dWealth.production;
        this.money += dWealth.money;
        this.food += dWealth.food;
        this.engineeringScience += dWealth.engineeringScience;
        this.societyScience += dWealth.societyScience;
        this.arcanumScience += dWealth.arcanumScience;
        return this;
    }

    public Wealth dMinusWealth(Wealth dWealth){
        this.production -= dWealth.production;
        this.money -= dWealth.money;
        this.food -= dWealth.food;
        this.engineeringScience -= dWealth.engineeringScience;
        this.societyScience -= dWealth.societyScience;
        this.arcanumScience -= dWealth.arcanumScience;
        return this;
    }

    public Wealth dWealth(double production, double money, double food, double engineeringScience, double societyScience, double arcanumScience){
        this.production += production;
        this.money += money;
        this.food += food;
        this.engineeringScience += engineeringScience;
        this.societyScience += societyScience;
        this.arcanumScience += arcanumScience;
        return this;
    }

    public Wealth dMinusWealth(double production, double money, double food, double engineeringScience, double societyScience, double arcanumScience){
        this.production -= production;
        this.money -= money;
        this.food -= food;
        this.engineeringScience -= engineeringScience;
        this.societyScience -= societyScience;
        this.arcanumScience -= arcanumScience;
        return this;
    }

    public Wealth toZero(){
        this.production = 0;
        this.money = 0;
        this.food = 0;
        this.engineeringScience = 0;
        this.societyScience = 0;
        this.arcanumScience = 0;
        return this;
    }

}
