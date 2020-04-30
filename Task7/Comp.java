
//Components
public class Comp{
    private int id;
    private double breakdownTime = 0;
	public String getId;
    
    public Comp(int id){
        this.id = id;
    }

    public void setBreakdown(double breakdown_time){
        this.breakdownTime = breakdown_time;
    }

    public double getBreakdown(){
        return this.breakdownTime;
    }

    public int getID(){
        return this.id;
    }
}