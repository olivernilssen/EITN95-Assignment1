
public class T6Prescription {
    //parameters for which script we are serving
    //and what time it entered the prescription queue
    private int scriptnumber;
    private double startTime;
    
    public T6Prescription(int number, double startTime){
        this.scriptnumber = number;
        this.startTime = startTime; 
    }


    public double getStart(){
        return this.startTime;
    }

    public int getID(){
        return this.scriptnumber;
    }
}