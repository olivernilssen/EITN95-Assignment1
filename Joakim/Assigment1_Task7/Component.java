import java.util.Random;

public class Component {
	public int component1;
	public int component2;
	public int component3;
	public int component4;
	public int component5;
    public int customerNr;
	Random slump = new Random();
    public double lifeSpan;
    
    public Component(int number){
    	
        this.component1 = component1;
        this.component2 = component2;
        this.component3 = component3;
        this.component4 = component4;
        this.component5 = component5;
        
        component1 = slump.nextInt(5);
        component2 = slump.nextInt(5);
        component3 = slump.nextInt(5);
        component4 = slump.nextInt(5);
        component5 = slump.nextInt(5);
        

    }
    public void lifeLength() {
    	
    	
    }
}