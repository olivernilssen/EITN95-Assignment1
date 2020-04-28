
public class T6Prescription {
    //parameters for which script we are serving
    //and what time it entered the prescription queue
    public int scriptnumber;
    public double startTime;
    
    public T6Prescription(int number, double startTime){
        this.scriptnumber = number;
        this.startTime = startTime; 
    }
}