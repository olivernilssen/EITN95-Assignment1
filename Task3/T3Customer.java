public class T3Customer {
    private int customerNr;
    private double startTime;
    
    public T3Customer(int number, double startTime){
        this.customerNr = number;
        this.startTime = startTime;
    }

    public double getStartTime(){
        return this.startTime;
    }

    public int getCustomerNr(){
        return this.customerNr;
    }
    
}