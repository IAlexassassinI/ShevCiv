package Processing.Utilits.Wrapers;

public class CreatableObject<TTT> {
    static final long serialVersionUID = 25L;
    public TTT object;
    public double goal;
    public double progress;
    public boolean status;

    public CreatableObject(TTT object, double goal, double progress){
        this.object = object;
        this.goal = goal;
        this.progress = progress;
        this.status = false;
    }

    public CreatableObject(TTT object, double goal){
        this(object, goal, 0);
    }

    public double addProgress(double dProgress){
        this.progress = this.progress + dProgress;
        if(this.progress >= goal){
            this.status = true;
            return this.progress - goal;
        }
        return 0;
    }

    public boolean checkStatus(){
        return status;
    }

    public double getProgress() {
        return progress;
    }

    public double getGoal() {
        return goal;
    }

    public TTT getObject(){
        return object;
    }

    public CreatableObject<TTT> getCopy(){
        return new CreatableObject<TTT>(this.object, this.goal, this.progress);
    }



}
