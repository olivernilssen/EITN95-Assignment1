    import java.io.IOException;
    import java.text.DecimalFormat;
    import java.util.Arrays;
    import java.util.Random;

    public class T7Main {
        static DecimalFormat df = new DecimalFormat("#.####");

        public static void main(String[] args) throws IOException {
            int N = 5, min = 1, max = 5, noRuns = 1000; // number of components, max and min for lifespan
            Comp[] allComponents = new Comp[N];
            double[] breakdownTimes = new double[N];
            int brokenDown = 0, batchSize = 15, count = 0, run = 0;
            double batchMeasures = 0;
            double allBatches = 0, noBatches = 0;

            Random p = new Random();
            SimpleFileWriter toFile = new SimpleFileWriter("Task7/lifespan.m", false);

            while (run < noRuns) {

                for (int i = 0; i < N; i++) {
                    allComponents[i] = new Comp(i + 1);
                    double random = p.nextDouble() * (max - min) + min;
                    breakdownTimes[i] = (int)random; //(int)random
                    allComponents[i].setBreakdown((int)random); //(int)random for discrete uniform distribution
                }

                Arrays.sort(breakdownTimes);
                
                double time = 0;
                int i = 0;
                
                while (brokenDown < N){
                    time = breakdownTimes[i];
                    for (Comp c : allComponents){
                        if (c.getBreakdown() == time){
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
                    i++;
                }

                toFile.println(String.valueOf(time));
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
            
            toFile.close();
            System.out.println("if the system is continous, we get the results: ");
            System.out.println("mean life span for system is " + df.format(allBatches/noBatches));
        }

    }