import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

public class T7Main {
    static DecimalFormat df = new DecimalFormat("#.####");

    public static void main(String[] args) throws IOException {
        int N = 5, min = 1, max = 5, noRuns = 1000; //number of components, max and min for lifespan
        Comp[] allComponents = new Comp[N];
        int brokenDown = 0, batchSize = 15, count = 0, run = 0;
        int batchMeasures = 0;
        double allBatches = 0, noBatches = 0;
        

        Random p = new Random();

        while (run < noRuns){

            for (int i = 0; i < N; i++){
                allComponents[i] = new Comp(i + 1);
                double random = p.nextDouble() * (max - min) + min;
                allComponents[i].setBreakdown((int)random);
            }

            int time = 0;
            
            while (brokenDown < 5){
                time++;
                for (Comp c : allComponents){
                    if (c.getBreakdown() == time){
                        // System.out.println("Component " + c.getID() + " broke down at " + time);
                        brokenDown++;

                        if (c.getID() == 1){
                            allComponents[1].setBreakdown(time); //change breakdown time of comp 2 and 5
                            allComponents[4].setBreakdown(time);
                        }
                        else if (c.getID() == 3){
                            allComponents[3].setBreakdown(time); //change breakdown time of comp 4
                        }
                    }
                }
            }

            batchMeasures += time; 
            count++; 
            
            if (count == batchSize){
                count = 0;
                allBatches += (batchMeasures/batchSize);
                batchMeasures = 0;
                noBatches++;
            }

            run++;
            brokenDown = 0;
        }

        System.out.println("mean life span for system is " + df.format(allBatches/noBatches));
    }
}