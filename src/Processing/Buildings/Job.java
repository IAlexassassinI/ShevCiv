package Processing.Buildings;

import Processing.Utilits.Wealth;

import java.io.Serializable;
import java.util.LinkedHashMap;

public class Job implements Serializable {
    static final long serialVersionUID = 1L;

    public Wealth wealth;
    public String name;
    public String description;

    Job(String name, Wealth wealth, String description){
        this.wealth = wealth;
        this.name = name;
        this.description = description;
    }

    public Job initJob(){
        AllJobs.put(this.name, this);
        return this;
    }

    public static LinkedHashMap<String, Job> AllJobs = new LinkedHashMap<>();

    public static final Job MoneyMaker = new Job("MoneyMaker", new Wealth(), "Make money").initJob();

}
